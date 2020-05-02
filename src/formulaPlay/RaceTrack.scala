

package formulaPlay
import scala.collection.mutable._

class RaceTrack(trackInfo: String) {
  
  
  //Keeps track of the latest car positions
  //Main purpose is for marking the position a car has been in the track
  //Set during the evaluation of firstTrack
  var car1Pos = (-1, -1)
  var car2Pos = (-1, -1)
  
  val finishLine = Buffer[(Int, Int)]()
  
  //The original track read from the file
  //Also sets the starting positions for the cars
  val track: Array[Array[Char]] = {
    val height = trackInfo.take(2).toInt
    val width = trackInfo.drop(2).take(2).toInt
    val track = Array.ofDim[Char](height, width)
    
    var placements = trackInfo.drop(4)
    
    for {
      j <- track.indices
      i <- track(0).indices
    } {
      
      placements(0) match {
        case 'X' => car1Pos = (i, j); track(j)(i) = 'A'
        case 'Y' => car2Pos = (i, j); track(j)(i) = 'B'
        case 'F' => finishLine += ((i, j)); track(j)(i) = 'F'
        case _   => track(j)(i) = placements(0)
      }
      
      track(j)(i) = placements(0)
      
      if (placements(0) == 'X') {
        car1Pos = (i, j)
        track(j)(i) = 'A'
      }
      if (placements(0) == 'Y') {
        car2Pos = (i, j)
        track(j)(i) = 'B'
      }
      
      if (placements(0) == 'F') {
        
      }
      
      placements = placements.drop(1)
      
    }
    
    track
  }
  
  val cloneTrack = track.clone
  val emptyTrack = cloneTrack.map(_.clone)
  emptyTrack.foreach(_.map(x => if (x == 'A' || x == 'B') ' ' else x))
  
  
  
  
  //Creates current track which means placing the cars onto their current positions
  //Also leaves a a mark on their latest position
  def drawTrack(car1Position: (Int, Int), car2Position: (Int, Int)): Unit = {
    
    //Marks latest positions
    track(car1Pos._2)(car1Pos._1) = 'a'
    track(car2Pos._2)(car2Pos._1) = 'b'
    
    //Changes positions
    car1Pos = car1Position
    car2Pos = car2Position
    
    //Marks new positions
    track(car1Pos._2)(car1Pos._1) = 'A'
    track(car2Pos._2)(car2Pos._1) = 'B'
    
  }
  
  
  
}