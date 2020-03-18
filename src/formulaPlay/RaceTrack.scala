

package formulaPlay

class RaceTrack(mapInfo: String, val car1: Car, val car2: Car) {
  
  //The original map read from the file. Also sets the starting positions for the cars
  val firstMap: Array[Array[String]] = {
    val hight = mapInfo.take(2).toInt
    val width = mapInfo.drop(2).take(2).toInt
    val track = Array.ofDim[String](hight, width)
    
    var unreadInfo = mapInfo.drop(4)
    
    for {
      row <- track.indices
      column <- track(0).indices
    } {
        track(row)(column) = unreadInfo.take(1)
        unreadInfo = unreadInfo.drop(1)
        if (track(row)(column) == "e") {
          if (car1.position == (-1, -1)) {
            car1.setPosition((row, column))
          } else car2.setPosition((row, column))
        }
    }
    track
  }
  //
  private var currentMap = firstMap
  
  //Creates current map meaning places the cars onto their current positions. Also leaves a a mark on their latest position
  def drawMap: Array[Array[String]] = {
    val newMap = currentMap
    newMap(car1.abandonedPosition._1)(car1.abandonedPosition._2) = "a"
    newMap(car2.abandonedPosition._1)(car2.abandonedPosition._2) = "b"
    newMap(car1.position._1)(car1.position._2) = "A"
    newMap(car2.position._1)(car2.position._2) = "B"
    currentMap = newMap
    newMap
  }
  
  
  
}


