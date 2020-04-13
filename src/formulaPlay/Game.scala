package formulaPlay
import exceptions._

class Game(info: String, driverName1: String, driverName2: String) {
  
  //Including only the information regarding the layout of the map from the file
  //(layout height, layout length, layout; not the tag MAP)
  val trackInfo = {
    this.infoTest(info)
    val cutInfo = info.drop(13)
    val cutInfo2 = cutInfo.drop(cutInfo.take(1).toInt + 1)
    cutInfo2.drop(cutInfo2.take(1).toInt + 1 + 3).dropRight(3)
  }
  
  //Reading the record names from info
  val (recordHolderName, recordTime) = {
    val cutInfo = info.drop(13)
    val toDrop = cutInfo.take(1).toInt
    val recordHolder = cutInfo.drop(1).take(toDrop)
    val cutInfo2 = cutInfo.drop(toDrop + 1)
    val recordTimes = cutInfo2.drop(1).take(cutInfo2.take(1).toInt)
    (recordHolder, recordTimes)
  }
  
  //Creating the race track
  val track = new RaceTrack(trackInfo)
  
  val finishLine = track.finishLine.toVector
  
  //Creating the cars
  val car1 = new Car(Driver(driverName1), track.car1Pos, 'A', finishLine)
  val car2 = new Car(Driver(driverName2), track.car2Pos, 'B', finishLine)
  
  //Marking which car is in turn and which isn't
  var carInTurn = car1
  var carNotInTurn = car2
  
  //Counts which turn it is
  //Even number means its player 1's turn, an uneven number means its player 2's turn
  var turn = 0
  
  //Tells whether the game has ended or not
  var isOver = false
  
  
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
    
    //Quits the game if a player has crossed the finish line and both players have played an equal number of turns
    if ((carInTurn.gearManager.crossedFinishLine || carNotInTurn.gearManager.crossedFinishLine) && turn % 2 == 1) {
      this.isOver = true
    }
    
    
    turn +=1
    carInTurn = if (turn % 2 == 0) car1 else car2
    carNotInTurn = if (turn % 2 == 0) car2 else car1
    
    returnMap
  }
  
  
  
  
  
  //Tests whether the information is has the correct tags at the correct places and integers at record holder's name length and time length.
  //If the file is incorrectly formatted, throws an exception that stops the program.
  def infoTest(information: String): Unit = {
    try {
      if (information.take(13) != "FORMULAMAPREC") throw FileException("Does not read 'FORMULAMAPREC' where it is supposed to.", information)
    val nameLength = information.drop(13).take(1).toInt
    val timeLength = information.drop(13 + 1 + nameLength).take(1).toInt
    val info2 = information.drop(13 + 1 + nameLength + 1 + timeLength)
    if (info2.take(3) != "MAP") throw FileException("Does not read 'MAP' where it is supposed to.", information)
    if (information.takeRight(3) != "END") throw FileException("Does not read 'END' where it is supposed to.", information)
    } catch {
      case e: FileException => throw e
      case e: NumberFormatException => throw FileException("Does not have Int where record's nameLength and timeLength should be.", information)
    }
    
  }
}