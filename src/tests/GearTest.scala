package tests
import formulaPlay._
import org.scalatest._

class GearTest extends FlatSpec {
  
  "plusHalf and minusHalf" should "be correct when gear is one or 4" in {
    val gearOne = new gearOne
    val gearFour = new gearFour
    assert(gearOne.plusHalf === 30)
    assert(gearOne.minusHalf === -1)
    assert(gearFour.plusHalf === 4)
    assert(gearFour.minusHalf === -4)
  }
  
  "direction" should "be changed properly when |direction(0)| != |direction(1)| in gear 3" in {
    val three = new gearThree
    
    three.direction(0) = -1
    three.direction(1) = 3
    three.changeDirection(1, Up)
    assert(three.direction === Array(-2, 3))
  }
  
  
  
}