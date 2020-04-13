package formulaPlay
import scala.collection.mutable._

class Car(val driver: Driver, firstPosition: (Int, Int), avatar: Char, finishLine: Vector[(Int, Int)]) {
  
  
  val gearManager = new GearManager(finishLine)
  
  
  private var currentPos = firstPosition
  
  
  
  //Simulates the car driving one round
  //In more detail:
  //Calls a new position from gearManager and gives gearChange, directionChange and current position as parameters
  //Sets the returned position as new current position
  //Returns Unit
  def drive(gearChange: Char, directionChange: Int, map: Array[Array[Char]]): Unit = {
    this.setPosition(gearManager.newPosition(gearChange, directionChange, this.position, map, true))
  }
  
  //Car goes through all its possible future destinations and returns a map with them marked
  def seekPossibilities(map: Array[Array[Char]]): Array[Array[Char]] = {
    val possibilityMap = Array.ofDim[Char](map.length, map(0).length)
    
    //Fills the possibilityMap with map's values meaning makes it a copy of it
    for {
      j <- map.indices
      i <- map(0).indices
    } {
      possibilityMap(j)(i) = map(j)(i)
    }
    
    //Calls fill for each of the 9 scenarios
    fill('+', 1)
    fill('+', 0)
    fill('+', -1)
    fill('-', 1)
    fill('-', 0)
    fill('-', -1)
    fill('=', 1)
    fill('=', 0)
    fill('=', -1)
    
    
    //Adds the specified gearChange and directionChange destination to possibilityMap
    def fill(gearChange: Char, directionChange: Int) = {
      val (xPos, yPos) = gearManager.newPosition(gearChange, directionChange, this.position, map, false)
      possibilityMap(yPos)(xPos) = gearChange
    }
    
    //Makes sure the car's own position does not dissapear even if the car does not move in one of the scenarios
    possibilityMap(position._2)(position._1) = avatar
    
    
    possibilityMap
  }
  
  
  
  
  
  def position = currentPos
  def setPosition(coords: (Int, Int)) = {
    currentPos = coords
  }
  
  def arrayPrint(array: Array[Array[Char]]) = {
    for (line <- array) {
      var string = ""
        line.foreach( string += _ )
        println(string)
      }
  }
}