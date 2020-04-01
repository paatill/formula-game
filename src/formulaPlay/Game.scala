package formulaPlay
import exceptions._

class Game(info: String) {
  
  //Including only the information regarding the layout of the map from the file
  //(layout height, layout length, layout; not the tag MAP)
  val trackInfo = {
    this.infoTest(info)
    
    val cutInfo = info.drop(13)
    val cutInfo2 = cutInfo.drop(cutInfo.take(1).toInt + 1)
    cutInfo2.drop(cutInfo2.take(1).toInt + 1 + 3).dropRight(3)
  }
  
  
  //Reading the driver names from info
  val (name1, name2) = {
    val cutInfo = info.drop(13)
    val toDrop = cutInfo.take(1).toInt
    val driver1Name = cutInfo.drop(1).take(toDrop)
    val cutInfo2 = cutInfo.drop(toDrop + 1)
    val driver2Name = cutInfo2.drop(1).take(cutInfo2.take(1).toInt)
    (driver1Name, driver2Name)
  }
  
  //Creating the cars
  val car1 = new Car(Driver(name1))
  val car2 = new Car(Driver(name2))
  
  val track = new RaceTrack(trackInfo)
  
  
  //Plays a turn as in moves the cars and returns an updated map
  def playTurn(gearChange1: String, gearChange2: String, directionChange1: String, directionChange2: String): Array[Array[Char]] = {
    
    
    
    
    
    
    track.drawMap(???, ???)
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