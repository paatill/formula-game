package formulaPlay
import exceptions._

class Game(info: String, driverInfo1: String, driverInfo2: String) {
  
  
  val FORMULATRACKRECdigits = 15
  val TRACKdigits = 5
  val recordNameLengthDigits = 2
  val recordLengthDigits = 1
  val lapDigits = 1
  val ENDdigits = 3
  val LAPdigits = 3
  
  //Including only the information regarding the layout of the track from the file
  //(layout height, layout length, layout; not the tag TRACK)
  val (trackInfo, lapInfo, recordHolderName, recordTime) = {
    this.infoTest(info)
    val afterREC = info.drop(FORMULATRACKRECdigits)
    val recordNameLength =      afterREC.take(recordNameLengthDigits).toInt
    val afterRecordNameLength = afterREC.drop(recordNameLengthDigits)
    val recordHolder =          afterREC.take(recordNameLength)
    val afterRecordName =       afterRecordNameLength.drop(recordNameLength)
    val recordLength =          afterRecordName.take(recordLengthDigits).toInt
    val afterRecordLength =     afterRecordName.drop(recordLengthDigits)
    val recordTimes =           afterRecordLength.take(recordLength)
    val afterRecord =           afterRecordName.drop(recordLength + recordLengthDigits)
    val afterTRACK =            afterRecord.drop(TRACKdigits)
    val infoTrack =             afterTRACK.dropRight(ENDdigits + lapDigits + LAPdigits)
    val afterTrack =            afterTRACK.takeRight(ENDdigits + lapDigits + LAPdigits)
    val ENDdropped =            afterTrack.dropRight(ENDdigits)
    val infoLap =               ENDdropped.drop(LAPdigits).toInt
    (infoTrack, infoLap, recordHolder, recordTimes)
  }
  
  
  //Creating the race track
  val raceTrack = new RaceTrack(trackInfo)
  val track = raceTrack.track
  
  
  val finishLine = raceTrack.finishLine.toVector
  
  //Creating the cars
  val car1 = new Car(Driver(driverInfo1), raceTrack.car1Pos, 'A', finishLine)
  val car2 = new Car(Driver(driverInfo2), raceTrack.car2Pos, 'B', finishLine)
  
  //Marking which car is in turn and which isn't
  private var carInTurn = car1
  private var carNotInTurn = car2
  def inTurnCar = carInTurn
  def notInTurnCar = carNotInTurn
  def inTurnCarGear = carInTurn.gearManager.gear.speed
  def notInTurnCarGear = carNotInTurn.gearManager.gear.speed
  def carLapTimes(car: Car) = car.gearManager.lapTimes
  

  
  //Tells whether the game has ended or not
  private var isOver = false
  def over = isOver
  
  //Tells who the victorious driver is
  private var victoriousDriver: Option[Driver] = None
  def victor = victoriousDriver
  
  def possibilitiesTrack = carInTurn.seekPossibilities(track)
  def possibilityTrack(tuple: (Char, Int)) = carInTurn.seekPossibilities(track, tuple)
  
  
  //Plays a turn as in moves the car and returns an updated track
  //In more detail:
  //The car whose driver has just given input drives meaning gets its location updated
  //Track then alters its track so it corresponds to the car's new position
  //The car not in turn then makes a track which has all its possible choices for the next turn marked
  //Turn is then added by one and the carInTurn and CarNotInTurn are switched accordingly
  //The track with all the choices is then returned
  def playTurn(gearChange: Char, directionChange: Int): Array[Array[Char]] = {
    carInTurn.drive(gearChange, directionChange, track)

    raceTrack.drawTrack(car1.position, car2.position)
    val returnTrack = carNotInTurn.seekPossibilities(track)
    val carInTurnFinished = carInTurn.gearManager.laps >= lapInfo
    val carNotInTurnFinished = carNotInTurn.gearManager.laps >= lapInfo
    
    //Quits the game if a player has crossed the finish line and both players have played an equal number of turns
    if ((carInTurnFinished || carNotInTurnFinished) && TurnCounter.turn % 2 == 1) {
      this.isOver = true
      victoriousDriver = if (carInTurnFinished) Some(carInTurn.driver) else Some(carNotInTurn.driver)
    }
    TurnCounter.advanceTurn
    carInTurn = if (TurnCounter.turn % 2 == 0) car1 else car2
    carNotInTurn = if (TurnCounter.turn % 2 == 0) car2 else car1
    returnTrack
  }
  
  
  
  
  
  //Tests whether the information is has the correct tags at the correct places and integers at record holder's name length and time length.
  //If the file is incorrectly formatted, throws an exception that stops the program.
  def infoTest(information: String): Unit = {
    try {
      if (information.take(15) != "FORMULATRACKREC") throw FileException("Does not read 'FORMULATRACKREC' where it is supposed to.", information)
    val nameLength = information.drop(15).take(2).toInt
    val timeLength = information.drop(15 + 2 + nameLength).take(1).toInt
    val info2 = information.drop(15 + 2 + nameLength + 1 + timeLength)
    if (info2.take(5) != "TRACK") throw FileException("Does not read 'TRACK' where it is supposed to.", information)
    if (information.takeRight(3) != "END") throw FileException("Does not read 'END' where it is supposed to.", information)
    } catch {
      case e: FileException => throw e
      case e: NumberFormatException => throw FileException("Does not have Int where record's nameLength and timeLength should be.", information)
    }
    
  }
}