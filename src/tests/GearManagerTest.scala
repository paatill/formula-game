package tests
import formulaPlay._
import org.scalatest._

class GearManagerTest extends FlatSpec {
  val gearManager = new GearManager(Vector())
  
  "gearNumber" should "be correct after changeGearNumber" in {
    assert(gearManager.gear.speed === 1)
    
    gearManager.changeGearNumber('+')
    assert(gearManager.gear.speed === 2)
    
    gearManager.changeGearNumber('-')
    assert(gearManager.gear.speed === 1)
    
    gearManager.changeGearNumber('+')
    assert(gearManager.gear.speed === 5)
    
    gearManager.changeGearNumber('-')
    assert(gearManager.gear.speed === 1)
  }
  
  "gear" should "be correct after gearChange" in {
    assert(gearManager.gear.speed == (new GearOne).speed)
    
    gearManager.changeGearNumber('+')
    gearManager.changeGear
    assert(gearManager.gear.speed == (new GearTwo).speed)
    
    gearManager.changeGear
    assert(gearManager.gear.speed == (new GearFive).speed)
  }
  
  "direction" should "be changed correctly when switching gears" in {
    gearManager.changeGear
    assert(gearManager.gear.speed === (new GearThree).speed)
    gearManager.gear.direction(0) = 3
    gearManager.gear.direction(1) = 2
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === 5)
    assert(gearManager.gear.direction(1) === 3)
    
    gearManager.changeGear
    gearManager.gear.direction(0) = 3
    gearManager.gear.direction(1) = 1
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === 5)
    assert(gearManager.gear.direction(1) === 2)
    
    gearManager.changeGear
    gearManager.gear.direction(0) = -3
    gearManager.gear.direction(1) = 1
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === -5)
    assert(gearManager.gear.direction(1) === 2)
    
    
    gearManager.changeGear
    gearManager.gear.direction(0) = 2
    gearManager.gear.direction(1) = 3
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === 3)
    assert(gearManager.gear.direction(1) === 4)
    
    
    
    gearManager.changeGear
    gearManager.gear.direction(0) = -2
    gearManager.gear.direction(1) = -3
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === -3)
    assert(gearManager.gear.direction(1) === -4)
    
    gearManager.changeGear
    gearManager.gear.direction(0) = 0
    gearManager.gear.direction(1) = -4
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === 0)
    assert(gearManager.gear.direction(1) === -1)
    
  }
  
  "newPosition" should "return the right position" in {
    val map = Array(
        Array('T', 'T', 'T', 'T', 'T', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'A', 'O', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'B', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'T', 'T', 'T', 'T', 'T'))
    
    
    gearManager.changeGear
    gearManager.gear.direction(0) = 1
    gearManager.gear.direction(1) = 0
    assert(gearManager.newPosition('=', 0, (3, 2), map, true, false) === (4, 2))
    
    gearManager.changeGear
    gearManager.gear.direction(0) = 0
    gearManager.gear.direction(1) = 5
    assert(gearManager.newPosition('=', 0, (3, 2), map, true, false) === (3, 3))
    
    gearManager.changeGear
    gearManager.gear.direction(0) = 1
    gearManager.gear.direction(1) = 1
    assert(gearManager.newPosition('=', 0, (3, 2), map, true, false) === (4, 3))
    
    gearManager.changeGear
    gearManager.gear.direction(0) = -2
    gearManager.gear.direction(1) = -1
    assert(gearManager.newPosition('=', 0, (3, 2), map, true, false) === (1, 1))
    
    gearManager.changeGear
    gearManager.gear.direction(0) = -3
    gearManager.gear.direction(1) = 2
    assert(gearManager.newPosition('-', -1, (3, 2), map, true, false) === (1, 4))
    
    
  }
  
  
  
}