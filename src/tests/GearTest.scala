package tests
import formulaPlay._
import org.scalatest._

class GearTest extends FlatSpec {
  
  val gearOne = new GearOne
  val gearTwo = new GearTwo
  val gearThree = new GearThree
  val gearFour = new GearFour
  val gearFive = new GearFive
  
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
    gearThree.changeDirection(1)
    assert(gearThree.direction === Array(-2, 3))
    
    gearThree.changeDirection(1)
    assert(gearThree.direction === Array(-3, 3))
    
    val shouldBeLeft = gearThree.changeDirection(1)
    assert(gearThree.direction === Array(-3, 2))
    
    gearThree.changeDirection(-1)
    assert(gearThree.direction === Array(-3, 3))
    
    gearThree.changeDirection(-1)
    assert(gearThree.direction === Array(-2, 3))
    
    gearThree.direction(0) = -3
    gearThree.direction(1) = -3
    gearThree.changeDirection(1)
    assert(gearThree.direction === Array(-2, -3))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = -3
    gearThree.changeDirection(1)
    assert(gearThree.direction === Array(3, -2))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = -3
    gearThree.changeDirection(-1)
    assert(gearThree.direction === Array(2, -3))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = 3
    gearThree.changeDirection(1)
    assert(gearThree.direction === Array(2, 3))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = 3
    gearThree.changeDirection(-1)
    assert(gearThree.direction === Array(3, 2))
    
    gearThree.direction(0) = 3
    gearThree.direction(1) = 3
    gearThree.changeDirection(0)
    assert(gearThree.direction === Array(3, 3))
    
  }
  
  
  
}