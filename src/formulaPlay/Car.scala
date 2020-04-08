

package formulaPlay

class Car(val driver: Driver, firstPosition: (Int, Int)) {
  
  
  val gearManager = new GearManager
  
  
  private var currentPos = firstPosition
  
  
  
  //Simulates the car driving one round
  //In more detail:
  //Calls a new position from gearManager and gives gearChange, directionChange and current position as parameters
  //Sets the returned position as new current position
  //Returns Unit
  def drive(gearChange: Char, directionChange: Int, map: Array[Array[Char]]): Unit = {
    this.setPosition(gearManager.newPosition(gearChange, directionChange, this.position, map))
  }
  
  
  
  
  
  
  
  
  def position = currentPos
  def setPosition(coords: (Int, Int)) = {
    currentPos = coords
  }
}