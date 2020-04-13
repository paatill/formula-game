package tests
import formulaPlay._
import org.scalatest._

class GameTest extends FlatSpec {
  "trackInfo" should "have only the information for RaceTrack" in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0302XPPPPYEND", "JIM", "MATTI")
    assert(game.trackInfo === "0302XPPPPY")
  }
  
  "Driver names" should "be what is read from the file." in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0302XPPPPPYEND", "JIM", "MATTI")
    assert(game.car1.driver.name === "JIM")
    assert(game.car2.driver.name === "MATTI")
  }
  "playTurn" should "return the correct map and should have correct car positions" in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP1008TTTTTTTTTOOOOOOTTOOXOOOTTOOOOOOTTOOYOOOTTOOOOOOTTOOOOOOTTOOOOOOTTOOOOOOTTTTTTTTTTEND", "JIM", "MATTI")
    assert(game.car1.position === (3, 2))
    assert(game.car2.position === (3, 4))
    assert(game.track.map === Array(
        Array('T', 'T', 'T', 'T', 'T', 'T', 'T', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'X', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'Y', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'T', 'T', 'T', 'T', 'T', 'T', 'T')))
    
    game.playTurn('=', 0)
    assert(game.track.map === Array(
        Array('T', 'T', 'T', 'T', 'T', 'T', 'T', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'A', 'a', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'B', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'T', 'T', 'T', 'T', 'T', 'T', 'T')))
  }
  
  "car1.position" should "be the given one." in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0305KPXPPPPKKPPPKPYEND", "JIM", "MATTI")
    assert(game.car1.position === (2, 0))
    assert(game.car2.position === (4, 2))
  }
  
  
  
  
  
  
}