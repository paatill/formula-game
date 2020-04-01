package formulaPlay
import exceptions._

class Game(info: String) {
  
  
  val trackInfo = {          //includes only the information regarding the layout of the map from the file (layout hight, layout length, layout; not the tag MAP)
    this.infoTest(info)
    
    info.drop(13)
        
  }
  val car1 = new Car(new Driver(""))
  
  
  
  
  
  def infoTest(information: String) = {
    if (information.take(13) != "FORMULAMAPREC") throw FileException("Does not read 'FORMULAMAPREC' where it is supposed to.", information)
    val nameLength = information.drop(13).take(1).toInt
    val timeLength = information.drop(13 + 1 + nameLength).take(1).toInt
    val info2 = information.drop(13 + 1 + nameLength + 1 + timeLength)
    if (info2.take(3) != "MAP") throw FileException("Does not read 'MAP' where it is supposed to.", information)
    if (information.takeRight(3) != "END") throw FileException("Does not read 'END' where it is supposed to.", information)
    
  }
}