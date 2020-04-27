package formulaPlay
import exceptions._

class Game(info: String, driverInfo1: String, driverInfo2: String) {
  
  //Including only the information regarding the layout of the map from the file
  //(layout height, layout length, layout; not the tag MAP)
  val (trackInfo, lapInfo) = {
    this.infoTest(info)
    val cutInfo = info.drop(13)                                                //"FORMULAMAPREC" dropped
    val cutInfo2 = cutInfo.drop(cutInfo.take(2).toInt + 2)                     //RecordHolder name and its length dropped
    val cutInfo3 = cutInfo2.drop(cutInfo2.take(2).toInt + 2 + 3)               //RecordTime and its length and "MAP" dropped
    val toTake1 = cutInfo3.take(2).toInt                                       //Map height
    val toTake2 = cutInfo3.drop(2).take(2).toInt                               //Map length
    val infoTrack = cutInfo3.take(toTake1 * toTake2 + 4)                       //Map info (map heigth, length and their product taken)
    val infoLap = cutInfo3.drop(toTake1 * toTake2 + 4 + 3).dropRight(3).toInt  //One digit lap amount (map height, length and their product dropped, "LAP" dropped, "END" dropped
    (infoTrack, infoLap)
  }
  
  //Reading the record names from info
  val (recordHolderName, recordTime) = {
    val cutInfo = info.drop(13)                                                //"FORMULAMAPREC" dropped
    val toDrop = cutInfo.take(2).toInt                                         //RecordHolder name length
    val recordHolder = cutInfo.drop(2).take(toDrop)                            //RecordHolder name (name length dropped, name length amount taken
    val cutInfo2 = cutInfo.drop(toDrop + 2)                                    //RecordHolder name dropped
    val toTake = cutInfo2.take(2).toInt                                        //RecordTime length
    val recordTimes = cutInfo2.drop(2).take(toTake)                            //RecordTime (RecordTime length dropped, its amount taken)
    (recordHolder, recordTimes)
  }
  
  //Creating the race track
  val track = new RaceTrack(trackInfo)
  
  
  val finishLine = track.finishLine.toVector
  
  //Creating the cars
  val car1 = new Car(Driver(driverInfo1), track.car1Pos, 'A', finishLine)
  val car2 = new Car(Driver(driverInfo2), track.car2Pos, 'B', finishLine)
  
  //Marking which car is in turn and which isn't
  private var carInTurn = car1
  private var carNotInTurn = car2
  def inTurnCar = carInTurn
  def notInTurnCar = carNotInTurn
  

  
  //Tells whether the game has ended or not
  private var isOver = false
  def over = isOver
  
  //Tells who the victorious driver is
  private var victoriousDriver: Option[Driver] = None
  def victor = victoriousDriver
  
  //Plays a turn as in moves the car and returns an updated map
  //In more detail:
  //The car whose driver has just given input drives meaning gets its location updated
  //Track then alters its map so it corresponds to the car's new position
  //The car not in turn then makes a map which has all its possible choices for the next turn marked
  //Turn is then added by one and the carInTurn and CarNotInTurn are switched accordingly
  //The map with all the choices is then returned
  def playTurn(gearChange: Char, directionChange: Int): Array[Array[Char]] = {
    
    
    
    
    carInTurn.drive(gearChange, directionChange, track.map)
    
    
    
    
    track.drawMap(car1.position, car2.position)
    val returnMap = carNotInTurn.seekPossibilities(track.map)
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
    
    
    
    returnMap
  }
  
  
  
  
  
  //Tests whether the information is has the correct tags at the correct places and integers at record holder's name length and time length.
  //If the file is incorrectly formatted, throws an exception that stops the program.
  def infoTest(information: String): Unit = {
    try {
      if (information.take(13) != "FORMULAMAPREC") throw FileException("Does not read 'FORMULAMAPREC' where it is supposed to.", information)
    val nameLength = information.drop(13).take(2).toInt
    val timeLength = information.drop(13 + 2 + nameLength).take(2).toInt
    val info2 = information.drop(13 + 2 + nameLength + 2 + timeLength)
    if (info2.take(3) != "MAP") throw FileException("Does not read 'MAP' where it is supposed to.", information)
    if (information.takeRight(3) != "END") throw FileException("Does not read 'END' where it is supposed to.", information)
    } catch {
      case e: FileException => throw e
      case e: NumberFormatException => throw FileException("Does not have Int where record's nameLength and timeLength should be.", information)
    }
    
  }
}