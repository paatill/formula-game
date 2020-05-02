package tests
import formulaPlay.RaceTrack
import org.scalatest._

class RaceTrackTest extends FlatSpec {
  "firstMap" should "be exactly as the file dictates it." in {
    val raceTrack = new RaceTrack("0304PPPPPKKPPPPP")
    assert(raceTrack.track === Array(Array('P', 'P', 'P', 'P'), Array('P', 'K', 'K', 'P'), Array('P', 'P', 'P', 'P')))
  }
  
  "It" should "be exactly as the file dictates it." in {
    val raceTrack = new RaceTrack("0305KPPPPPPKKPPPKPP")
    assert(raceTrack.track === Array(Array('K', 'P', 'P', 'P', 'P'), Array('P', 'P', 'K', 'K', 'P'), Array('P', 'P', 'K', 'P', 'P')))
  }
  
  
  "Car positions" should "be exactly as the file dictates it." in {
    val raceTrack = new RaceTrack("0305KPXPPPPKKPPPKPY")
    assert(raceTrack.car1Pos === (2, 0))
    assert(raceTrack.car2Pos === (4, 2))
  }
  
  "Returned map" should "have the right been to mark and the right is in mark" in {
    val raceTrack = new RaceTrack("0305KPXPPPPKKPPPKPY")
    
    assert(raceTrack.drawTrack((2, 2), (0, 2)) === Array(Array('K', 'P', 'a', 'P', 'P'), Array('P', 'P', 'K', 'K', 'P'), Array('B', 'P', 'A', 'P', 'b')))
    
    assert(raceTrack.car1Pos === (2, 2))
    assert(raceTrack.car2Pos === (0, 2))
  }
  
}