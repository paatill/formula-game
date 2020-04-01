

package formulaPlay

class RaceTrack(mapInfo: String) {
  
  //The original map read from the file. Also sets the starting positions for the cars
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
      placements = placements.drop(1)
    }
    
    track
  }
  //The map currently in place
  private var currentMap = firstMap
  
  //Creates current map which means placing the cars onto their current positions. Also leaves a a mark on their latest position
  def drawMap: Array[Array[Char]] = {
    val newMap = currentMap
    ???
  }
  
  
  
}


