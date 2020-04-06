package tests
import formulaPlay._
import org.scalatest._

class GearTest extends FlatSpec {
  
  val gearOne = new gearOne
  val gearTwo = new gearTwo
  val gearThree = new gearThree
  val gearFour = new gearFour
  val gearFive = new gearFive
  
  "plusHalf and minusHalf" should "be correct in any gear" in {
    assert(gearOne.plusHalf === 1)
    assert(gearOne.minusHalf === -1)
    assert(gearTwo.plusHalf === 2)
    assert(gearTwo.minusHalf === -2)
    assert(gearThree.plusHalf === 3)
    assert(gearThree.minusHalf === -3)
    assert(gearFour.plusHalf === 4)
    assert(gearFour.minusHalf === -4)
    assert(gearFive.plusHalf === 5)
    assert(gearFive.minusHalf === -5)
  }
  
  "direction" should "be changed properly when |direction(0)| != |direction(1)| in gear 3" in {
    
    gearThree.direction(0) = -1
    gearThree.direction(1) = 3
    gearThree.changeDirection(1, Up)
    assert(gearThree.direction === Array(-2, 3))
    
    gearThree.changeDirection(1, Up)
    assert(gearThree.direction === Array(-3, 3))
    
    val shouldBeLeft = gearThree.changeDirection(1, Up)
    assert(gearThree.direction === Array(-3, 2))
    assert(shouldBeLeft === Left)
    
    gearThree.changeDirection(-1, Left)
    assert(gearThree.direction === Array(-3, 3))
    
    gearThree.changeDirection(-1, Left)
    assert(gearThree.direction === Array(-2, 3))
    
    gearThree.direction(0) = -3
    gearThree.direction(1) = -3
    gearThree.changeDirection(1, Left)
    assert(gearThree.direction === Array(-2, -3))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = -3
    gearThree.changeDirection(1, Down)
    assert(gearThree.direction === Array(3, -2))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = -3
    gearThree.changeDirection(-1, Down)
    assert(gearThree.direction === Array(2, -3))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = 3
    gearThree.changeDirection(1, Right)
    assert(gearThree.direction === Array(2, 3))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = 3
    gearThree.changeDirection(-1, Up)
    assert(gearThree.direction === Array(3, 2))
    
  }
  
  
  
}