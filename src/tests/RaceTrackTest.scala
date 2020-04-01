package tests
import formulaPlay.RaceTrack
import org.scalatest._

class RaceTrackTest extends FlatSpec {
  "firstMap" should "be exactly as the file dictates it." in {
    val track = new RaceTrack("0304PPPPPKKPPPPP")
    assert(track.firstMap === Array(Array('P', 'P', 'P', 'P'), Array('P', 'K', 'K', 'P'), Array('P', 'P', 'P', 'P')))
  }
  
  "It" should "be exactly as the file dictates it." in {
    val track = new RaceTrack("0305KPPPPPPKKPPPKPP")
    assert(track.firstMap === Array(Array('K', 'P', 'P', 'P', 'P'), Array('P', 'P', 'K', 'K', 'P'), Array('P', 'P', 'K', 'P', 'P')))
  }
  
}