package tests
import formulaPlay._
import org.scalatest._

class GameTest extends FlatSpec {
  "trackInfo" should "have only the information for RaceTrack" in {
    val game = new Game("FORMULATRACKREC03JIM11TRACK0302XPPPPYLAP1END", "JIM", "MATTI")
    assert(game.trackInfo === "0302XPPPPY")
  }
  
  "Driver names" should "be what is read from the file." in {
    val game = new Game("FORMULATRACKREC03JIM11TRACK0302XPPPPYLAP1END", "JIM", "MATTI")
    assert(game.inTurnCar.driver.name === "JIM")
    assert(game.notInTurnCar.driver.name === "MATTI")
  }
  "playTurn" should "return the correct map and should have correct car positions" in {
    val game = new Game("FORMULATRACKREC03JIM11TRACK1008TTTTTTTTTOOOOOOTTOOXOOOTTOOOOOOTTOOYOOOTTOOOOOOTTOOOOOOTTOOOOOOTTOOOOOOTTTTTTTTTTLAP1END", "JIM", "MATTI")
    assert(game.inTurnCar.position === (3, 2))
    assert(game.notInTurnCar.position === (3, 4))
    assert(game.track() === Array(
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
    assert(game.track() === Array(
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
    val game = new Game("FORMULATRACKREC3JIM11TRACK0305KPXPPPPKKPPPKPYLAP1END", "JIM", "MATTI")
    assert(game.inTurnCar.position === (2, 0))
    assert(game.notInTurnCar.position === (4, 2))
  }
  
  
  
  
  
  
}