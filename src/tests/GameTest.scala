package tests
import formulaPlay._
import org.scalatest._

class GameTest extends FlatSpec {
  "trackInfo" should "have only the information for RaceTrack" in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0302XPPPPYEND")
    assert(game.trackInfo === "0302XPPPPY")
  }
  
  "Driver names" should "be what is read from the file." in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0302XPPPPPYEND")
    assert(game.car1.driver.name === "JIM")
    assert(game.car2.driver.name === "MATTI")
  }
  "playTurn" should "return the correct map." in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0305XPPPPPPKKPPPKPPYEND")
    assert(game.playTurn("", "", "", "") === Array(Array('X', 'P', 'P', 'P', 'P'), Array('P', 'P', 'K', 'K', 'P'), Array('P', 'P', 'K', 'P', 'Y')))
  }
  
  "car1.position" should "be the given one." in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0305KPXPPPPKKPPPKPYEND")
    assert(game.car1.position === (2, 2))
    assert(game.car2.position === (0, 2))
  }
  
  
}