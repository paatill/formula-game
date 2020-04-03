

package formulaPlay

class Car(val driver: Driver, firstPosition: (Int, Int)) {
  
  
  val gearManager = new GearManager
  
  
  private var currentPos = firstPosition
  
  
  
  
  
  
  
  
  
  
  
  
  
  def position = currentPos
  def setPosition(coords: (Int, Int)) = {
    currentPos = coords
  }
}