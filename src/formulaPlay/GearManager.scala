

package formulaPlay
import scala.collection.mutable
import scala.math._

class GearManager {
  val one = new gearOne
  val two = new gearTwo
  val three = new gearThree
  val four = new gearFour
  val five = new gearFive
  
  var gear: Gear = one
  var gearNumber = 1
  var generalDirection: GeneralDirection = Left

  
  
  //Changes gearNumber and calls for changeGear if necessary
  def changeGearNumber(change: Char): Unit = {
    change match {
      case '+' if gearNumber < 5 => gearNumber += 1; changeGear
      case '-' if gearNumber > 1 => gearNumber -= 1; changeGear
      case _ =>
    }
  }
  
  //Changes the gear to match the gearNumber
  def changeGear: Unit = {
    val newGear = gearNumber match {
      case 1 => one
      case 2 => two
      case 3 => three
      case 4 => four
      case 5 => five
      case _ => gear
    }
    
    val a: Double = gear.direction(0)
    val b: Double = gear.direction(1)
    val c: Double = sqrt(pow(a, 2) + pow(b, 2))
    val alpha = asin(a / c)
      
    if (generalDirection == Left || generalDirection == Right) {
      val constant = gear.direction(1)
      newGear.direction(1) = constant
      val variable = constant / sqrt((c / a) - 1)
      newGear.direction(0) = variable.toInt
    } else {
      val constant = gear.direction(0)
      newGear.direction(0) = constant
      val variable = constant * sqrt((c / a) - 1)
      newGear.direction(1) = variable.toInt
    }
    gear = newGear
  }
  
  
  
  
  
  def newPosition(gearChange: Char, directionChange: Int, carPosition: (Int, Int)): (Int, Int) = {
    
    ???
  }
  
}