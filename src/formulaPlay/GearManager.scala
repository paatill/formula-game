package formulaPlay
import scala.collection.mutable
import exceptions._
import scala.math._

class GearManager(finishLine: Vector[(Int, Int)]) {
  val one = new GearOne
  val two = new GearTwo
  val three = new GearThree
  val four = new GearFour
  val five = new GearFive
  
  var gear: Gear = one
  var gearNumber = 1
  
  //Tells whether the car has crossed the finish line
  var crossedFinishLine = false
  
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
  
  
  
  //Changes gear and direction
  //Then moves the car forward one tile at a time
  //Stops if tries to enter a tile where there is another car or a wall
  //Returns the new position once the car has stopped
  def newPosition(gearChange: Char, directionChange: Int, carPosition: (Int, Int), map: Array[Array[Char]], isTrueChange: Boolean): (Int, Int) = {
    val previousGearNumber = gear.speed
    val previousDirection = (gear.direction(0), gear.direction(1))
    
    this.changeGearNumber(gearChange)
    this.changeGear
    gear.changeDirection(directionChange)

    
    //Determines which general direction the car drives
    val xStep = if (gear.direction(0) < 0) -1 else if (gear.direction(0) > 0) 1 else 0
    val yStep = if (gear.direction(1) < 0) -1 else if (gear.direction(1) > 0) 1 else 0
   
    
    val intendedPosition = (carPosition._1 + gear.direction(0), carPosition._2 + gear.direction(1))
    
    //Checks which distance is bigger
    val xDistance = if (intendedPosition._1 - carPosition._1 != 0) abs(intendedPosition._1 - carPosition._1) else 1
    val yDistance = if (intendedPosition._2 - carPosition._2 != 0) abs(intendedPosition._2 - carPosition._2) else 1
    val xDistanceBiggerOrEqual = xDistance >= yDistance
    val distancesEqual = xDistance == yDistance
    
    //The position of the car at each juncture
    //Also eventually stores the final return value
    var finalPosition: (Int, Int) = carPosition
    
    
    //Something to stop the loop from going on forever
    var continueLoop = true
    
    
    while (continueLoop) {
      
      //Counts how much of the journey through each direction has been traveled
      val xToGo = abs(intendedPosition._1 - finalPosition._1)
      val xToGoPercent = xToGo.toDouble / xDistance
      val yToGo = abs(intendedPosition._2 - finalPosition._2)
      val yToGoPercent = yToGo.toDouble / yDistance
      
      //Determines which step is taken in the following way:
      //First the direction with bigger relative way to go is chosen
      //Percentages being equal, the distance with the bigger absolute value is chosen
      //Absolute values being equal, direction x is chosen
      val xPercentEqualyPercent = xToGoPercent == yToGoPercent
      val xStepIn = (xDistanceBiggerOrEqual && xPercentEqualyPercent) || (xToGoPercent > yToGoPercent)
      val yStepIn = !xStepIn
      val xStepPut = if (xStepIn || distancesEqual) xStep else 0
      val yStepPut = if (yStepIn || distancesEqual) yStep else 0
      
      
      //Matches the final outcome
      //In more precise terms:
      //Moves the car one tile forward unless it is about to enter some special tile
      //In that case the car follows the orders of the special case
      //For an example, if the car tries to enter a place where another car is currently situated, it stops
      map(finalPosition._2 + yStepPut)(finalPosition._1 + xStepPut) match {
        case 'A' => continueLoop = false
        case 'B' => continueLoop = false
        case 'T' => continueLoop = false
        case _   => finalPosition = (finalPosition._1 + xStepPut, finalPosition._2 + yStepPut)
      }
      
      //Quits the loop (and car movement) if the full movement has been traveled
      if (finalPosition == intendedPosition) {
        continueLoop = false
      }
      
      //Checks if the car is crossing the finish line
      //If true, changes crossedFinishLine to true
      for (tile <- finishLine) {
        if (finalPosition == tile) {
          crossedFinishLine = true
        }
      }
    }
    
    
    //Changes the gearManager back to its original state if no actual movement was intended
    if (!isTrueChange) {
      gearNumber = previousGearNumber
      this.changeGear
      gear.direction(0) = previousDirection._1
      gear.direction(1) = previousDirection._2
      crossedFinishLine = false
    }
    
    finalPosition
  }
  
}