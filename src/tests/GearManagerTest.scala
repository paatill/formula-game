package tests
import formulaPlay._
import org.scalatest._

class GearManagerTest extends FlatSpec {
  val gearManager = new GearManager
  
  "gearNumber" should "be correct after changeGearNumber" in {
    assert(gearManager.gearNumber === 1)
    
    gearManager.changeGearNumber('+')
    assert(gearManager.gearNumber === 2)
    
    gearManager.changeGearNumber('-')
    assert(gearManager.gearNumber === 1)
    
    gearManager.gearNumber = 5
    gearManager.changeGearNumber('+')
    assert(gearManager.gearNumber === 5)
    
    gearManager.gearNumber = 1
    gearManager.changeGearNumber('-')
    assert(gearManager.gearNumber === 1)
  }
  
  "gear" should "be correct after gearChange" in {
    assert(gearManager.gear.speed == (new GearOne).speed)
    
    gearManager.changeGearNumber('+')
    gearManager.changeGear
    assert(gearManager.gear.speed == (new GearTwo).speed)
    
    gearManager.gearNumber = 5
    gearManager.changeGear
    assert(gearManager.gear.speed == (new GearFive).speed)
  }
  
  "direction" should "be changed correctly when switching gears" in {
    gearManager.gearNumber = 3
    gearManager.changeGear
    assert(gearManager.gear.speed === (new GearThree).speed)
    gearManager.gear.direction(0) = 3
    gearManager.gear.direction(1) = 2
    gearManager.gearNumber = 5
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === 5)
    assert(gearManager.gear.direction(1) === 3)
    
    gearManager.gearNumber = 3
    gearManager.changeGear
    gearManager.gear.direction(0) = 3
    gearManager.gear.direction(1) = 1
    gearManager.gearNumber = 5
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === 5)
    assert(gearManager.gear.direction(1) === 2)
    
    gearManager.gearNumber = 3
    gearManager.changeGear
    gearManager.gear.direction(0) = -3
    gearManager.gear.direction(1) = 1
    gearManager.gearNumber = 5
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === -5)
    assert(gearManager.gear.direction(1) === 2)
    
    gearManager.generalDirection = Up
    
    gearManager.gearNumber = 3
    gearManager.changeGear
    gearManager.gear.direction(0) = 3
    gearManager.gear.direction(1) = 2
    gearManager.gearNumber = 4
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === 3)
    assert(gearManager.gear.direction(1) === 4)
    
    
    gearManager.generalDirection = Down
    
    gearManager.gearNumber = 3
    gearManager.changeGear
    gearManager.gear.direction(0) = -3
    gearManager.gear.direction(1) = -2
    gearManager.gearNumber = 4
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === -3)
    assert(gearManager.gear.direction(1) === -4)
    
    gearManager.gearNumber = 1
    gearManager.changeGear
    assert(gearManager.gear.direction(0) === -1)
    assert(gearManager.gear.direction(1) === -1)
    
  }
  
  
}