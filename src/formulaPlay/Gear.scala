

package formulaPlay
import scala.math._
import exceptions._

abstract class Gear {
  val speed: Int
  val sideLength: Int
  val plusHalf: Int
  val minusHalf: Int
  val direction: Array[Int]
  
  
  def changeDirection(changeByT: Int, generalDirection: GeneralDirection): GeneralDirection = {
    var finalGeneralDirection = generalDirection
    
    //Flips the changeByT if necessary
    val changeBy = if (generalDirection == Up || generalDirection == Left) changeByT * -1 else changeByT
    
    //Determines which part of the direction-vector is to be changed
    val xOrY = if(generalDirection == Up || generalDirection == Down) 0 else 1
    
    
    (direction, changeByT) match {
      //Takes care of every situation except when |direction(0)| == |direction(1)|
      case (d, e) if minusHalf + 1 to plusHalf - 1 contains d(xOrY) => d(xOrY) = d(xOrY) + changeBy
      
      //The following cases cover each of the special corner situations, where |direction(0)| == |direction(1)|
      //First the precise position on direction is altered and then the general direction is set correct
      case (d, e) if (d(0), d(1)) == (plusHalf, minusHalf) => direction(max(0, e)) = (plusHalf - 1) * e * -1; finalGeneralDirection = if (e == 1) Right else Down
      case (d, e) if (d(0), d(1)) == (minusHalf, plusHalf) => direction(max(0, e)) = (plusHalf - 1) * e; finalGeneralDirection = if (e == 1) Left else Up
      case (d, e) if (d(0), d(1)) == (plusHalf, plusHalf) => direction(min(0, e) * -1) = plusHalf - 1; finalGeneralDirection = if (e == 1) Up else Right
      case (d, e) if (d(0), d(1)) == (minusHalf, minusHalf) => direction(min(0, e) * -1) = (plusHalf - 1) * -1; finalGeneralDirection = if (e == 1) Down else Left
      case (d, e) => throw new CaseNotMatchedException("Direction change has failed for no suitable case was found.")
    }
    
    finalGeneralDirection
    
  }
  
  
}

class GearOne extends Gear {
  val speed = 1
  val sideLength = 3
  val direction = Array(-1, 0)
  val plusHalf = 1
  val minusHalf = -1
}

class GearTwo extends Gear {
  val speed = 2
  val sideLength = 5
  val direction = Array(-2, 0)
  val plusHalf = 2
  val minusHalf = -2
}

class GearThree extends Gear {
  val speed = 3
  val sideLength = 7
  val direction = Array(-3, 0)
  val plusHalf = 3
  val minusHalf = -3
}

class GearFour extends Gear {
  val speed = 4
  val sideLength = 9
  val direction = Array(-4, 0)
  val plusHalf = 4
  val minusHalf = -4
}

class GearFive extends Gear {
  val speed = 5
  val sideLength = 11
  val direction = Array(-5, 0)
  val plusHalf = 5
  val minusHalf = -5
}