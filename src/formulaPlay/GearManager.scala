package formulaPlay
import scala.collection.mutable._
import exceptions._
import scala.math._

class GearManager(finishLine: Vector[(Int, Int)]) {
  private val one = new GearOne
  private val two = new GearTwo
  private val three = new GearThree
  private val four = new GearFour
  private val five = new GearFive
  
  private var currentGear: Gear = one
  private var gearNumber = 1
  
  //Tells how many times the car has crossed the finish line
  private var lapCount = 0
  
  private val lapTimes = Buffer[Int]()
  
  def storedLapTimes = lapTimes.toVector
  
  def gear = currentGear
  
  def laps = lapCount
  
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
    
    currentGear = newGear
  }
  
  
  
  //gearChange and directionChange are the user given choices of raising or lowering the gear by one or keeping it the same
  //carPosition is the car's current position and track is the current track of the game
  //isTrueChange is true if this is the car's acutal movement and false if this is merely seeking for possible destination
  //isSingleOption is true if the method call is looking for a single possibile destination
  //First changes the gear and direction accordingly
  //Then moves the car forward one tile at a time
  //Stops if tries to enter a tile where there is another car or a wall if either isTrueChange or isSingleOption are true
  //Returns the new position once the car has stopped
  //Also saves laps and lap times
  def newPosition(gearChange: Char, directionChange: Int, carPosition: (Int, Int), track: Array[Array[Char]], isTrueChange: Boolean, isSingleOption: Boolean): Option[(Int, Int)] = {
    val previousGearNumber = gear.speed
    val previousDirection = (gear.direction(0), gear.direction(1))
    
    //Changing gear and direction
    this.changeGearNumber(gearChange)
    this.changeGear
    gear.changeDirection(directionChange)
    
    
    //Determines which general direction the car drives and gives it a single step accordingly
    val xStep = if (gear.direction(0) < 0) -1 else if (gear.direction(0) > 0) 1 else 0
    val yStep = if (gear.direction(1) < 0) -1 else if (gear.direction(1) > 0) 1 else 0
   
    //The position the car ends up in if nothing blocks its way
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
    
    //Whether the finish line has been crossed by this particular movement
    var addLap = false
    
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
      //Moves the car one tile forward unless it is about to enter some special tile or drive out of bounds
      //In that case the car follows the orders of the special case
      //For an example, if the car tries to enter a place where another car is currently situated, it stops
      if (isTrueChange || isSingleOption) {
      
        track(finalPosition._2 + yStepPut)(finalPosition._1 + xStepPut) match {
          case 'A' => continueLoop = false
          case 'B' => continueLoop = false
          case 'T' => continueLoop = false
          case _ => finalPosition = (finalPosition._1 + xStepPut, finalPosition._2 + yStepPut)
        }
      } else {
        if (!isOutOfBounds) {
          finalPosition = (finalPosition._1 + xStepPut, finalPosition._2 + yStepPut)
        } else {
          continueLoop = false
        }
      }
      
      
      def isOutOfBounds: Boolean = {
        finalPosition._1 + xStepPut > track(0).length - 1 || finalPosition._1 + xStepPut < 0 || finalPosition._2 + yStepPut > track.length - 1 || finalPosition._2 + yStepPut < 0
      }
      
      //Quits the loop (and car movement) if the full movement has been traveled
      if (finalPosition == intendedPosition) {
        continueLoop = false
      }
      
      //Checks if the car is crossing the finish line
      //If true, changes addLap to true
      if (isTrueChange) {
        for (tile <- finishLine) {
          if (finalPosition == tile) {
            addLap = true
          }
        }
      }
    }
    
    //Saves the lap and lap time
    if (addLap) {
      lapCount += 1
      val round = TurnCounter.round
      val lastLapTime = lapTimes.lift(lapTimes.length - 1) match {
        case Some(lapTime) => lapTime
        case None          => 0
      }
      lapTimes += (round - lastLapTime)
      
    }
    
    //Changes the gearManager back to its original state if no actual movement was intended
    if (!isTrueChange) {
      gearNumber = previousGearNumber
      this.changeGear
      gear.direction(0) = previousDirection._1
      gear.direction(1) = previousDirection._2
    }
    
    //If the car's position has changed, the position wrapped in Some is returned
    //Otherwise None is returned
    if (track(finalPosition._2)(finalPosition._1) != 'A' && track(finalPosition._2)(finalPosition._1) != 'B') {
      Some(finalPosition)
    } else {
      None
    }
    
  }
  
}