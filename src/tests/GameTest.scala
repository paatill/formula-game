package tests
import formulaPlay._
import org.scalatest._

class GameTest extends FlatSpec {
  "trackInfo" should "have only the information for RaceTrack" in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0302PPPPPPEND")
    assert(game.trackInfo === "0302PPPPPP")
  }
  
  "Driver names" should "be what is read from the file." in {
    val game = new Game("FORMULAMAPREC3JIM5MATTIMAP0302PPPPPPEND")
    assert(game.car1.driver.name === "JIM")
    assert(game.car2.driver.name === "MATTI")
  }
  
  
  
  
}