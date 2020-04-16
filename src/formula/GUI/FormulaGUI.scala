package formula.GUI
import formulaPlay._
import scala.io.Source
import java.io._
import scala.swing._
import scala.swing.event._
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}




object FormulaGUI extends SimpleSwingApplication {
  
  
  
  
  val player1Name = "Jack"
  val player2Name = "Jim"
  val filename = "/Users/rekowenell/git/formula-peli/maps/RaceTrackTest01.txt"
  val file = Source.fromFile(filename)
  val pureFileInfo = file.toVector.mkString
  val fileInfo: String = pureFileInfo.filter( _ != '\n' )
  file.close
  
  val game = new Game(fileInfo, player1Name, player2Name)
  val array = game.track.map
  
  
  
  
  var gearChange: Char = '='
  var directionChange: Int = 0
  
  val carInfo = new Label("The gear change is: " + gearChange + "\nThe direction change is : " + directionChange)
  
  
  
  val buttons = new BoxPanel(Orientation.Horizontal)
  val upGearButton = new Button("Up the Gear")
  val keepGearButton = new Button("Keep the same Gear")
  val downGearButton = new Button("Down the Gear")
  buttons.contents += upGearButton
  buttons.contents += keepGearButton
  buttons.contents += downGearButton
  
  
  
  this.listenTo(upGearButton)
  this.reactions += {
    case clickEvent: ButtonClicked if clickEvent.source == upGearButton => updateCarInfo('+', directionChange)
  }
  
  val theMap = new MapPanel(array)
      
  
  def top = new MainFrame {
    title    = "Road"
    contents = {
      val a = new BoxPanel(Orientation.Vertical)
      a.contents += carInfo
      a.contents += theMap
      a.contents += buttons
      a
    }
    size     = new Dimension(16 * array(0).length, 16 * (array.length + 2) - 10)
  }
  
  def updateCarInfo(newGearChange: Char, newDirectionChange: Int) = {
    gearChange = newGearChange
    directionChange = newDirectionChange
    carInfo.text = "The gear change is: " + gearChange + "\nThe direction change is : " + directionChange
  }
  
  

  class MapPanel(track: Array[Array[Char]]) extends Panel {
    override def paintComponent(g: Graphics2D) = {
      map(track, g)
    }  
  }
  
  def map(track: Array[Array[Char]], g: Graphics2D): Graphics2D = {
  //  val picture = new BufferedImage(31 * track(0).length, 31 * track.length, BufferedImage.TYPE_INT_ARGB)
  //  val g = picture.getGraphics.asInstanceOf[Graphics2D]
    
    def wall(i: Int, j: Int) = {
      g.setColor(Color.gray)
      g.fillRect(i * 16, j * 16, 16, 16)
    }
    
    def carA(i: Int, j: Int) = {
      g.setColor(Color.blue)
      g.fillRect(i * 16, j * 16, 16, 16)
    }
    
    def carB(i: Int, j: Int) = {
      g.setColor(Color.red)
      g.fillRect(i * 16, j * 16, 16, 16)
    }
    
    def finishLine(i: Int, j: Int) = {
      g.setColor(Color.white)
      g.fillRect(i * 16, j * 16, 16, 16)
    }
    
    def road(i: Int, j: Int) = {
      g.setColor(Color.green)
      g.fillRect(i * 16, j * 16, 16, 16)
    }
    
    for {
      j <- 0 until track.length
      i <- 0 until track(0).length
    } {
      track(j)(i) match {
        case 'T' => wall(i, j)
        case 'F' => finishLine(i, j)
        case 'A' => carA(i, j)
        case 'B' => carB(i, j)
        case _   => road(i, j)
      }
      
      g.setColor(Color.black)
      g.fillRect(i * 16, j * 16, 16, 1)
      g.fillRect(i * 16, j * 16, 1, 16)
    }
    
    
    g
  
  }
  
}