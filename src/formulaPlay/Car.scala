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
  def drive(gearChange: Char, directionChange: Int, track: Array[Array[Char]]): Unit = {
    val newPositionOption = (gearManager.newPosition(gearChange, directionChange, this.position, track, true, false))
    val positionToSet = newPositionOption match {
      case Some(tuple) => tuple
      case None => position
    }
    this.setPosition(positionToSet)
  }
  
  //Car goes through all its possible future destinations and returns a track with them marked
  def seekPossibilities(track: Array[Array[Char]], tuples: (Char, Int)*): Array[Array[Char]] = {
    val possibilityTrack= Array.ofDim[Char](track.length, track(0).length)
    
    //Fills the possibilityTrack with track's values meaning makes it a copy of it
    for {
      j <- track.indices
      i <- track(0).indices
    } {
      possibilityTrack(j)(i) = track(j)(i)
    }
    
    val gearDirectionChanges = Vector(('+', 1), ('+', 0), ('+', -1), ('-', 1), ('-', 0), ('-', -1), ('=', 1), ('=', 0), ('=', -1))
    
    //Calls fill for each of the 9 scenarios
    if (tuples.size == 0) {
      gearDirectionChanges.foreach(tuple => fill(tuple._1, tuple._2))
    } else {
      tuples.foreach(tuple => fill(tuple._1, tuple._2))
    }
    
    
    
    
    //Adds the specified gearChange and directionChange destination to possibilityTrack
    def fill(gearChange: Char, directionChange: Int) = {
      val positionOption = gearManager.newPosition(gearChange, directionChange, this.position, track, false, tuples.size != 0)
      positionOption match {
        case Some((xPos, yPos)) => possibilityTrack(yPos)(xPos) = gearChange
        case None => 
      }
    }
    
    //Makes sure the car's own position does not dissapear even if the car does not move in one of the scenarios
    possibilityTrack(position._2)(position._1) = avatar
    
    
    possibilityTrack
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