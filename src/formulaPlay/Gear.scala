

package formulaPlay
import scala.math._
import exceptions._

abstract class Gear {
  //Speed equals how many squares the car travels in a turn
  //So speed is 1 at gear one, 2 at gear two and so on
  val speed: Int
  /* Direction of a car is represented as a square with side length of speed * 2 + 1 where numbers from minuHalf to plusHalf are represented.
   * The following example where X represents a direction and A the car is for gear two:
   *
   *   -2, -1, 0, 1, 2
   *  2 X   X  X  X  X 2
   *  1 X            X 1
   *  0 X      A     X 0
   * -1 X            X-1
   * -2 X   X  X  X  X-2
   *   -2, -1, 0, 1, 2
   */
  val sideLength: Int // = speed * 2 + 1
  val plusHalf: Int// = sideLength / 2
  val minusHalf: Int // = sideLength / 2 * -1
  //Index 0 represents the x-axis and index 1 the y-axis of the direction
  val direction: Array[Int] //= Array(minusHalf, 0)
  
  //Takes as parameter an integer that is either -1, 0 or 1
  //Otherwise throws an exception
  def changeDirection(changeByGiven: Int): Unit = {
    
    //Flips the changeByGiven's positivity/negativity if currentDirection is on the upper or left row
    //This is because 1 represents clockwise change while -1 represents counterclockwise change.
    //In the down or right row the value can just be straight added, but in the upper or left row it works the opposite way.
    val changeBy = if (direction(1) == plusHalf || direction(0) == minusHalf) changeByGiven * -1 else changeByGiven
    
    //Determines which part of the direction-vector is to be changed
    //Chooses the index number to be used
    val xOrY = if (direction(1) == plusHalf || direction(1) == minusHalf) 0 else 1
    
    
    val x = direction(0)
    val y = direction(1)
    
    (direction, changeByGiven) match {
      //If no change is ordered
      case (direction, changeByGiven) if changeByGiven == 0 =>
      //Takes care of every situation except when |direction(0)| == |direction(1)|
      case (direction, changeByGiven) if minusHalf + 1 to plusHalf - 1 contains direction(xOrY) => direction(xOrY) = direction(xOrY) + changeBy
      //The following cases cover each of the special corner situations, where |direction(0)| == |direction(1)|
      //First the precise position on direction is altered and then the general direction is set correct
      case (direction, changeByGiven) if (x, y) == (plusHalf, minusHalf) => direction(max(0, changeByGiven)) = (plusHalf - 1) * changeByGiven * -1
      case (direction, changeByGiven) if (x, y) == (minusHalf, plusHalf) => direction(max(0, changeByGiven)) = (plusHalf - 1) * changeByGiven
      case (direction, changeByGiven) if (x, y) == (plusHalf, plusHalf) => direction(min(0, changeByGiven) * -1) = plusHalf - 1
      case (direction, changeByGiven) if (x, y) == (minusHalf, minusHalf) => direction(min(0, changeByGiven) * -1) = (plusHalf - 1) * -1
      case (direction, changeByGiven) => throw new CaseNotMatchedException("Direction change has failed for no suitable case was found.")
    }
    
    
  }
  
  
}

class GearOne extends Gear {
  val speed = 1
  val sideLength = 3
  val plusHalf = 1
  val minusHalf = -1
  val direction = Array(-1, 0)
  println(direction(0) + " " + direction(1))
}

class GearTwo extends Gear {
  val speed = 2
  val sideLength = 5
  val plusHalf = 2
  val minusHalf = -2
  val direction = Array(-2, 0)
}

class GearThree extends Gear {
  val speed = 3
  val sideLength = 7
  val plusHalf = 3
  val minusHalf = -3
  val direction = Array(-3, 0)
}

class GearFour extends Gear {
  val speed = 4
  val sideLength = 9
  val plusHalf = 4
  val minusHalf = -4
  val direction = Array(-4, 0)
}

class GearFive extends Gear {
  val speed = 5
  val sideLength = 11
  val plusHalf = 5
  val minusHalf = -5
  val direction = Array(-5, 0)
}