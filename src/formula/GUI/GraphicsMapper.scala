package formula.GUI
import formulaPlay._
import scala.io.Source
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter
import scala.swing._
import scala.swing.event._
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.swing.ImageIcon
import javax.swing.filechooser._
import java.awt.Font


class GraphicsMapper(squareSide: Int, game: Game) {
   //Takes an array (map of the race track) and graphics as parameters
   //Changes then those graphics as the chracterMap of the array instruct
    def apply(track: Array[Array[Char]], graphics: Graphics2D): Unit = {
      val trackLength = track(0).length
      val trackWidth = track.length
      
      //The loops that goes through all the characters in the array
      for {
        j <- 0 until track.length
        i <- 0 until track(0).length
      } {
        setChosenPartOfTrack(track, i, j)
      }
      
      
      def setChosenPartOfTrack(trackUsed: Array[Array[Char]], i: Int, j: Int): Unit = {
        //The method matching the the character is chosen
        trackUsed(j)(i) match {
          case 'T' => wall(i, j)
          case 'F' => finishLine(i, j)
          case 'A' => car(i, j, game.car1, Color.blue)
          case 'B' => car(i, j, game.car2, Color.red)
          case 'a' => carAWasHere(i, j)
          case 'b' => carBWasHere(i, j)
          case '-' /*if (futureDestinationHelpPrinted == Dialog.Result.Yes)*/ => downGear(i, j)
          case '+' /*if (futureDestinationHelpPrinted == Dialog.Result.Yes)*/ => upGear(i, j)
          case '=' /*if (futureDestinationHelpPrinted == Dialog.Result.Yes)*/ => keepGear(i, j)
          case _   => road(i, j) 
        }
        
        //Lines separating the different squares are drawn
       graphics.setColor(Color.black)
       graphics.fillRect(i * squareSide, j * squareSide, squareSide, 1)
       graphics.fillRect(i * squareSide, j * squareSide, 1, squareSide)
      }
      
      
      def searchMapProper(trackUsed: Array[Array[Char]], i: Int, j: Int) = {
        trackUsed(j)(i) match {
          case 'T' => wall(i, j)
          case 'F' => finishLine(i, j)
          case 'A' => road(i, j)
          case 'B' => road(i, j)
          case _   => road(i, j)
        }
      }
      
      
      //Methods each drawing their specified square
      
      def wall(i: Int, j: Int) = {
       graphics.setColor(Color.gray)
       graphics.fillRect(i * squareSide, j * squareSide, squareSide, squareSide)
      }
      
      def car(i: Int, j: Int, car: Car, color: Color) = {
        val direction = Direction(car)
        val carGraphics = CarGraphics(direction)
        
        searchMapProper(game.track, i, j)
        for {
          x <- carGraphics(0).indices
          y <- carGraphics.indices
        } {
          carGraphics(y)(x) match {
            case 'D' =>graphics.setColor(Color.black);graphics.fillRect(i * squareSide + x, j * squareSide + y, 1, 1)
            case 'B' =>graphics.setColor(color);graphics.fillRect(i * squareSide + x, j * squareSide + y, 1, 1)
            case _ => Unit
          }
          
        }
      }
      
      def carAWasHere(i: Int, j: Int) = {
        searchMapProper(game.track, i, j)
       graphics.setColor(Color.cyan)
       graphics.fillArc(i * squareSide + 4, j * squareSide + 4, 8, 8, 0, 360)
      }
      
      def carBWasHere(i: Int, j: Int) = {
        searchMapProper(game.track, i, j)
       graphics.setColor(Color.magenta)
       graphics.fillArc(i * squareSide + 4, j * squareSide + 4, 8, 8, 0, 360)
      }
      
      def finishLine(i: Int, j: Int) = {
        val baseILength = i * squareSide
        val baseJLength = j * squareSide
        for {
          x <- 0 until 5
          y <- 0 until 5
        } {
          if (x % 2 == y % 2) {
           graphics.setColor(new Color(155, 135, 12))
           graphics.fillRect(baseILength + (x * 3), baseJLength + (y * 3), 3, 3)
          } else {
           graphics.setColor(Color.white)
           graphics.fillRect(baseILength + (x * 3), baseJLength + (y * 3), 3, 3)
          }
        } 
      }
      
      def road(i: Int, j: Int) = {
       graphics.setColor(Color.green)
       graphics.fillRect(i * squareSide, j * squareSide, squareSide, squareSide)
        /*val baseILength = i * squareSide
        val baseJLength = j * squareSide
        for {
          x <- 0 until 16
          y <- 0 until 16
        } {
          if (x % 2 == y % 2) {
           graphics.setColor(Color.LIGHT_GRAY)
           graphics.fillRect(baseILength + x, baseJLength + y, 1, 1)
          } else {
           graphics.setColor(Color.DARK_GRAY)
           graphics.fillRect(baseILength + x, baseJLength + y, 1, 1)
          }
        }*/
      }
      
      def upGear(i: Int, j: Int) = {
        searchMapProper(game.track, i, j)
       graphics.setColor(if (game.inTurnCar == game.car1) Color.blue else Color.red)
       graphics.fillRect(i * squareSide + 2, j * squareSide + 7, squareSide - 4, 2)
       graphics.fillRect(i * squareSide + 7, j * squareSide + 2, 2, squareSide - 4)
      }
      
      def keepGear(i: Int, j: Int) = {
        searchMapProper(game.track, i, j)
       graphics.setColor(if (game.inTurnCar == game.car1) Color.blue else Color.red)
       graphics.fillRect(i * squareSide + 2, j * squareSide + 5, squareSide - 4, 2)
       graphics.fillRect(i * squareSide + 2, j * squareSide + 9, squareSide - 4, 2)
      }
      
      def downGear(i: Int, j: Int) = {
        searchMapProper(game.track, i, j)
       graphics.setColor(if (game.inTurnCar == game.car1) Color.blue else Color.red)
       graphics.fillRect(i * squareSide + 2, j * squareSide + 7, squareSide - 4, 2)
      }
      
      
      
      
      
      
      
    }
}