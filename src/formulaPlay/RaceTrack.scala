

package formulaPlay

class RaceTrack(mapInfo: String) {
  
  
  //Keeps track of the latest car positions
  //Main purpose is for marking the position a car has been in the track
  //Set during the evaluation of firstMap
  var car1Pos = (-1, -1)
  var car2Pos = (-1, -1)
  
  
  //The original map read from the file
  //Also sets the starting positions for the cars
  val firstMap: Array[Array[Char]] = {
    val hight = mapInfo.take(2).toInt
    val width = mapInfo.drop(2).take(2).toInt
    val track = Array.ofDim[Char](hight, width)
    
    var placements = mapInfo.drop(4)
    
    for {
      j <- track.indices
      i <- track(0).indices
    } {
      track(j)(i) = placements(0)
      
      if (placements(0) == 'X') {
        car1Pos = (i, j)
      }
      if (placements(0) == 'Y') {
        car2Pos = (i, j)
      }
      
      placements = placements.drop(1)
      
    }
    
    track
  }
  
  //The map being showed the players at the moment
  var currentMap = firstMap
  
  def map = currentMap
  
  
  //Creates current map which means placing the cars onto their current positions
  //Also leaves a a mark on their latest position
  def drawMap(car1Position: (Int, Int), car2Position: (Int, Int)): Array[Array[Char]] = {
    val newMap = currentMap
    
    //Marks latest positions
    newMap(car1Pos._2)(car1Pos._1) = 'a'
    newMap(car2Pos._2)(car2Pos._1) = 'b'
    
    //Changes positions
    car1Pos = car1Position
    car2Pos = car2Position
    
    //Marks new positions
    newMap(car1Pos._2)(car1Pos._1) = 'A'
    newMap(car2Pos._2)(car2Pos._1) = 'B'
    
    currentMap = newMap
    
    currentMap
  }
  
  
  
}