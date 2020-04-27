package tests
import formulaPlay.RaceTrack
import org.scalatest._

class RaceTrackTest extends FlatSpec {
  "firstMap" should "be exactly as the file dictates it." in {
    val track = new RaceTrack("0304PPPPPKKPPPPP")
    assert(track.map === Array(Array('P', 'P', 'P', 'P'), Array('P', 'K', 'K', 'P'), Array('P', 'P', 'P', 'P')))
  }
  
  "It" should "be exactly as the file dictates it." in {
    val track = new RaceTrack("0305KPPPPPPKKPPPKPP")
    assert(track.map === Array(Array('K', 'P', 'P', 'P', 'P'), Array('P', 'P', 'K', 'K', 'P'), Array('P', 'P', 'K', 'P', 'P')))
  }
  
  
  "Car positions" should "be exactly as the file dictates it." in {
    val track = new RaceTrack("0305KPXPPPPKKPPPKPY")
    assert(track.car1Pos === (2, 0))
    assert(track.car2Pos === (4, 2))
  }
  
  "Returned map" should "have the right been to mark and the right is in mark" in {
    val track = new RaceTrack("0305KPXPPPPKKPPPKPY")
    
    assert(track.drawMap((2, 2), (0, 2)) === Array(Array('K', 'P', 'a', 'P', 'P'), Array('P', 'P', 'K', 'K', 'P'), Array('B', 'P', 'A', 'P', 'b')))
    
    assert(track.car1Pos === (2, 2))
    assert(track.car2Pos === (0, 2))
  }
  
}