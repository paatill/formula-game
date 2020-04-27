

package formulaPlay
import scala.collection.mutable._

class RaceTrack(mapInfo: String) {
  
  
  //Keeps track of the latest car positions
  //Main purpose is for marking the position a car has been in the track
  //Set during the evaluation of firstMap
  var car1Pos = (-1, -1)
  var car2Pos = (-1, -1)
  
  val finishLine = Buffer[(Int, Int)]()
  
  //The original map read from the file
  //Also sets the starting positions for the cars
  val map: Array[Array[Char]] = {
    val hight = mapInfo.take(2).toInt
    val width = mapInfo.drop(2).take(2).toInt
    val track = Array.ofDim[Char](hight, width)
    
    var placements = mapInfo.drop(4)
    
    for {
      j <- track.indices
      i <- track(0).indices
    } {
      
      placements(0) match {
        case 'X' => car1Pos = (i, j); track(j)(i) = 'A'
        case 'Y' => car1Pos = (i, j); track(j)(i) = 'B'
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
  
  val cloneMap = map.clone
  val emptyMap = cloneMap.map(_.clone)
  emptyMap.foreach(_.map(x => if (x == 'A' || x == 'B') ' ' else x))
  
  
  
  
  //Creates current map which means placing the cars onto their current positions
  //Also leaves a a mark on their latest position
  def drawMap(car1Position: (Int, Int), car2Position: (Int, Int)): Unit = {
    
    //Marks latest positions
    map(car1Pos._2)(car1Pos._1) = 'a'
    map(car2Pos._2)(car2Pos._1) = 'b'
    
    //Changes positions
    car1Pos = car1Position
    car2Pos = car2Position
    
    //Marks new positions
    map(car1Pos._2)(car1Pos._1) = 'A'
    map(car2Pos._2)(car2Pos._1) = 'B'
    
  }
  
  
  
}