

package formulaPlay
import scala.collection.mutable

class GearManager {
  var currentGear: Gear = ???
  var generalDirection: GeneralDirection = ???
  var direction: Int = ???
  
  def switchGear: Unit = ???
  
  def changeDirection: Unit = ???
  
  def calculateDestination(position: (Int, Int)): (Int, Int) = {
    val oldX = position._1
    val oldY = position._2
    
    generalDirection match {
      case Right => {
        val x = oldX + currentGear.speed
        val y = oldY + direction
      }
      case Left => {
        val x = oldX - currentGear.speed
        val y = oldY + direction
      }
      case Up => {
        val x = oldX + direction
        val y = oldY + currentGear.speed
      }
      case Down => {
        val x = oldX + direction
        val y = oldY - currentGear.speed
      }
    }
    
    ???
  }
  
}