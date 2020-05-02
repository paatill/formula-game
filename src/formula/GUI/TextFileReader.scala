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

object TextFileReader {
  
  //Filter that only accepts a text file
  val fileFilter = new FileFilter {
    def accept(pathname: File): Boolean = pathname.getName.endsWith(".txt")
    def getDescription: String = "Only .txt files"
  }
  
  def apply(directory: String) = {
    //File is read and the information is saved to fileInfo as String
    val fileChooser = new FileChooser(new File(System.getProperty("user.dir") + directory))
    fileChooser.fileFilter_=(fileFilter)
    
    //A Dialog asks players to choose a file for the map
    //If they do not choose a map, another Dialog will pop up, demanding them to make the choice
    val selectedFile = {
      var fileIsChosen = false
      while (!fileIsChosen) {
        if (fileChooser.showOpenDialog(new Dialog) == FileChooser.Result.Approve) fileIsChosen = true
      }
      fileChooser.selectedFile
    }
    
    val filePath = selectedFile.getAbsolutePath
    
    //Information from the file
    val file = Source.fromFile(selectedFile)
    
    //Information from the file in string
    val pureFileInfo = file.toVector.mkString
    //Information from the file without line breaks
    val fileInfo: String = pureFileInfo.filter( _ != '\n' )
    file.close
    (fileInfo, pureFileInfo, filePath)
  }
}