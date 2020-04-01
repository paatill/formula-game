

package formulaPlay

class Car(val driver: Driver, firstPosition: (Int, Int)) {
  
  private var currentPos = firstPosition
  
  
  
  
  
  
  
  
  
  
  
  
  
  def position = currentPos
  def setPosition(coords: (Int, Int)) = {
    currentPos = coords
  }
}