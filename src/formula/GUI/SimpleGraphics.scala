package formula.GUI
import formulaPlay._
import scala.io.Source
import java.io._
import scala.swing._
import scala.swing.event._
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.swing.ImageIcon

object SimpleGraphics extends SimpleSwingApplication {

  def top = new MainFrame {
    title    = "Road"
    contents = {
      
      val array = Array(
        Array('T', 'T', 'T', 'T', 'T', 'T', 'T', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'A', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'B', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'T', 'T', 'T', 'T', 'T', 'T', 'T'))
      
      val a = new MapPanel(array)
      
      a.preferredSize_=(new Dimension(31 * array(0).length, 31 * array.length))
      
      a
    }
    size     = new Dimension(400, 400)
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
      g.fillRect(i * 31, j * 31, 31, 31)
    }
    
    def carA(i: Int, j: Int) = {
      g.setColor(Color.blue)
      g.fillRect(i * 31, j * 31, 31, 31)
    }
    
    def carB(i: Int, j: Int) = {
      g.setColor(Color.red)
      g.fillRect(i * 31, j * 31, 31, 31)
    }
    
    def finishLine(i: Int, j: Int) = {
      g.setColor(Color.white)
      g.fillRect(i * 31, j * 31, 31, 31)
    }
    
    def road(i: Int, j: Int) = {
      g.setColor(Color.green)
      g.fillRect(i * 31, j * 31, 31, 31)
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
      g.fillRect(i * 31, j * 31, 31, 1)
      g.fillRect(i * 31, j * 31, 1, 31)
    }
    
    
    g
  
  }
}