package formulaPlay
import scala.collection.mutable
import exceptions._
import scala.math._

class GearManager {
  val one = new GearOne
  val two = new GearTwo
  val three = new GearThree
  val four = new GearFour
  val five = new GearFive
  
  var gear: Gear = one
  var gearNumber = 1

  
  
  //Changes gearNumber and calls for changeGear if necessary
  def changeGearNumber(change: Char): Unit = {
    change match {
      case '+' if gearNumber < 5 => gearNumber += 1; changeGear
      case '-' if gearNumber > 1 => gearNumber -= 1; changeGear
      case _ =>
    }
  }
  
  //Changes gear to match the gearNumber
  //Also sets the right direction for the new Gear
  def changeGear: Unit = {
    val newGear = gearNumber match {
      case 1 => one
      case 2 => two
      case 3 => three
      case 4 => four
      case 5 => five
      case _ => throw new CaseNotMatchedException("gearNumber is not between 1 and 5")
    }
    
    
    val x: Double = gear.direction(0)
    val y: Double = gear.direction(1)
    val c: Double = sqrt(pow(y, 2) + pow(x, 2))
    
    //Sets a and b up the right way
    //Sets the correct direction in the upcoming Gear
    //Finally switches the Gear
    if (x == gear.plusHalf || x == gear.minusHalf) {
      val b = if (x > 0) newGear.plusHalf else newGear.minusHalf
      val a = if (y != 0) y / gear.plusHalf * newGear.plusHalf else 0
      newGear.direction(0) = b
      newGear.direction(1) = rint(a).toInt
    } else {
      val a = if (y > 0) newGear.plusHalf else newGear.minusHalf
      val b = if (x != 0) x / gear.plusHalf * newGear.plusHalf else 0
      newGear.direction(0) = rint(b).toInt
      newGear.direction(1) = a
    }
    
    gear = newGear
  }
  
  
  
  
  def newPosition(gearChange: Char, directionChange: Int, carPosition: (Int, Int), map: Array[Array[Char]]): (Int, Int) = {
    this.changeGearNumber(gearChange)
    this.changeGear
    gear.changeDirection(directionChange)
    
    val xStep = if (gear.direction(0) < 0) -1 else 1
    val yStep = if (gear.direction(1) < 0) -1 else 1
   
    
    val intendedPosition = (carPosition._1 + gear.direction(0), carPosition._2 + gear.direction(1))
    
    val xDistance = if (intendedPosition._1 - carPosition._1 != 0) abs(intendedPosition._1 - carPosition._1) else 1
    val yDistance = if (intendedPosition._2 - carPosition._2 != 0) abs(intendedPosition._2 - carPosition._2) else 1
    val xDistanceBiggerOrEqual = xDistance >= yDistance
    
    var finalPosition: (Int, Int) = carPosition
    var continueLoop = true
    
    
    while (continueLoop) {
      
            
      val xToGo = abs(intendedPosition._1 - finalPosition._1)
      val xToGoPercent = xToGo.toDouble / xDistance
      val yToGo = abs(intendedPosition._2 - finalPosition._2)
      val yToGoPercent = yToGo.toDouble / yDistance
      
      val xPercentEqualyPercent = xToGoPercent == yToGoPercent
      val xStepIn = (xDistanceBiggerOrEqual && xPercentEqualyPercent) || (xToGoPercent > yToGoPercent)
      val yStepIn = !xStepIn
      val xStepPut = if (xStepIn) xStep else 0
      val yStepPut = if (yStepIn) yStep else 0
      
        
        
      map(finalPosition._2 + yStepPut)(finalPosition._1 + xStepPut) match {
        case 'A' => continueLoop = false
        case 'B' => continueLoop = false
        case 'T' => continueLoop = false
        case _   => finalPosition = (finalPosition._1 + xStepPut, finalPosition._2 + yStepPut)
      }
      
      
      if (finalPosition == intendedPosition) {
        continueLoop = false
      }
      
    }

    finalPosition
  }
  
}