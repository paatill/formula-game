

package formulaPlay

class Car(val driver: Driver) {
  
  private var currentPos = (-1, -1)
  private var previousPos = (-1, -1)
  
  def position = currentPos
  def setPosition(coords: (Int, Int)) = currentPos = coords
  def abandonedPosition = previousPos
}