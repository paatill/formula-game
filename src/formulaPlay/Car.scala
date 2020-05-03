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
  //Parameter "track" is a race track and parameter "tuples" are all the (gear change value, direction change value) tuples a possible destination is searched for
  //In practise, parameter "tuple"'s multiple parameters simply allow the input of one or zero parameters
  //The game only needs to show a single one possible destination, or all of them (in which case any obstacles are not taken into consideration)
  //Therefore if a tuple is given, a track with only that possibility is returned
  //If no tuples are given, all the simplified possibilities are given
  def seekPossibilities(track: Array[Array[Char]], tuples: (Char, Int)*): Array[Array[Char]] = {
    val possibilityTrack = Array.ofDim[Char](track.length, track(0).length)
    //Fills the possibilityTrack with track's values; in other words makes a copy of it
    for {
      j <- track.indices
      i <- track(0).indices
    } {
      possibilityTrack(j)(i) = track(j)(i)
    }
    val gearDirectionChanges = Vector(('+', 1), ('+', 0), ('+', -1), ('-', 1), ('-', 0), ('-', -1), ('=', 1), ('=', 0), ('=', -1))
    
    //Calls fill for each of the 9 scenarios if no tuples were given as a parameter
    //Otherwise only the given scenarios are called fill for
    if (tuples.size == 0) {
      gearDirectionChanges.foreach(tuple => fill(tuple._1, tuple._2))
    } else {
      tuples.foreach(tuple => fill(tuple._1, tuple._2))
    }
    
    //A helper method that adds the specified gearChange and directionChange destination to possibilityTrack
    //If there are no tuples, obstacles on the track will not be taken into consideration
    def fill(gearChange: Char, directionChange: Int) = {
      val positionOption = gearManager.newPosition(gearChange, directionChange, this.position, track, false, tuples.size != 0)
      positionOption match {
        case Some((xPos, yPos)) => possibilityTrack(yPos)(xPos) = gearChange
        case None => 
      }
    }
    
    //Makes sure the car's own position does not dissapear even if the car does not move in one of the possibility scenarios
    possibilityTrack(position._2)(position._1) = avatar
    
    
    possibilityTrack
  }
  
  
  
  
  //Access to car position
  def position = currentPos
  //Method for changing car position
  def setPosition(coords: (Int, Int)) = {
    currentPos = coords
  }
}