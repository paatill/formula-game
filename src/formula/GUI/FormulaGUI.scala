package formula.GUI
import formulaPlay._
import scala.io.Source
import java.io.File
import scala.swing._
import scala.swing.event._
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}
import javax.swing.ImageIcon
import javax.swing.filechooser._




object FormulaGUI extends SimpleSwingApplication {
  
  
  
  
  
  
  val gamePanel = new BoxPanel(Orientation.Vertical) {
    
    //Set up player names
    val player1Name = getPlayerName('1')
    val player2Name = getPlayerName('2')
    
    //Calls a Dialog where the players will enter their names
    def getPlayerName(playerNumber: Char): String = {
      //This variable tracks whether a name of correct name is typed in the following Dialog
      //Must be true for the following loop to end
      var nameLengthRight = false
      var playerName = ""
      
      //Creates new dialogs asking for a player's name until they give one that is no longer than 9 characters
      while(!nameLengthRight) {
        val answer = Dialog.showInput[String](new Dialog, "Type your name here, player " + playerNumber + ". It may not be more than 9 characters long.", initial = "")
        answer match {
          case Some(name) if name.length <= 9 => playerName = name; nameLengthRight = true
          case _ => Dialog.showMessage(new Dialog, "The name you typed was too long!")
        }
      }
      playerName
    }
    
    //Filter that only accepts a text file
    val fileFilter = new FileFilter {
      def accept(pathname: File): Boolean = pathname.getName.endsWith(".txt")
      def getDescription: String = "Only .txt files"
    }
  
    //File is read and the information is saved to fileInfo as String
    val fileChooser = new FileChooser
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
    
    //
    val file = Source.fromFile(selectedFile)
    
    val pureFileInfo = file.toVector.mkString
    val fileInfo: String = pureFileInfo.filter( _ != '\n' )
    file.close
  
    //A new game is created
    val game = new Game(fileInfo, player1Name, player2Name)
    val array = game.track.map
  
  
  
    //Store currently chosen gear and direction changes
    var gearChange: Char = '='
    var directionChange: Int = 0
    
  
  
    //Label of car information
    //Includes information about currently chosen gear and direction change
    val carInfo = new Label("The gear change is: " + gearChange + "\nThe direction change is : " + directionChange + "\nCarInTurn: " + game.inTurnCar.driver.name)
  
    //Sets up a BoxPanel for the buttons the player can interact with
    //Includes buttons for choosing gearChange, choosing direction change, and confirming the changes
    //Also sets the application up to listen to those buttons
    val buttons = new BoxPanel(Orientation.Horizontal)
    val upGearButton = Button("Up the Gear") { updateCarInfo('+', directionChange) }
      buttons.contents += upGearButton
      this.listenTo(upGearButton)
    val keepGearButton = Button("Keep the same Gear") { updateCarInfo('=', directionChange) }
      buttons.contents += keepGearButton
      this.listenTo(keepGearButton)
    val downGearButton = Button("Down the Gear") { updateCarInfo('-', directionChange) }
      buttons.contents += downGearButton
      this.listenTo(downGearButton)
    val clockwiseDirectionButton = Button("Turn clockwise") { updateCarInfo(gearChange, 1) }
      buttons.contents += clockwiseDirectionButton
      this.listenTo(clockwiseDirectionButton)
    val keepDirectionButton = Button("Keep the current direction") { updateCarInfo(gearChange, 0) }
      buttons.contents += keepDirectionButton
      this.listenTo(keepDirectionButton)
    val counterClockwiseDirectionButton = Button("Turn counterClockwise") { updateCarInfo(gearChange, -1) }
      buttons.contents += counterClockwiseDirectionButton
      this.listenTo(counterClockwiseDirectionButton)
    val driveButton = Button("Confirm choices and drive") {
        //Advances the game one turn
        game.playTurn(gearChange, directionChange)
        //Draws the new game map into picture
        map(game.track.map, g)
        //Sets a new icon based on the updated picture into mapLabel
        mapLabel.icon = new ImageIcon(picture)
        //Sets the changes back to neutral for the next player
        updateCarInfo('=', 0)
      }
      buttons.contents += driveButton
      this.listenTo(driveButton)
  
  
    
  //Defines what happens when a particular reaction is triggered
    /*this.reactions += {
      case clickEvent: ButtonClicked if clickEvent.source == upGearButton => updateCarInfo('+', directionChange)
      case clickEvent: ButtonClicked if clickEvent.source == keepGearButton => updateCarInfo('=', directionChange)
      case clickEvent: ButtonClicked if clickEvent.source == downGearButton => updateCarInfo('-', directionChange)
      case clickEvent: ButtonClicked if clickEvent.source == clockwiseDirectionButton => updateCarInfo(gearChange, 1)
      case clickEvent: ButtonClicked if clickEvent.source == keepDirectionButton => updateCarInfo(gearChange, 0)
      case clickEvent: ButtonClicked if clickEvent.source == counterClockwiseDirectionButton => updateCarInfo(gearChange, -1)
      
        
      case clickEvent: ButtonClicked if clickEvent.source == driveButton => {
             
        //Advances the game one turn
        game.playTurn(gearChange, directionChange)
        //Draws the new game map into picture
        map(game.track.map, g)
        //Sets a new icon based on the updated picture into mapLabel
        mapLabel.icon = new ImageIcon(picture)
        
        //Sets the changes back to neutral for the next player
        updateCarInfo('=', 0)
      }
      
    }*/
    
    //Creates a BufferedImage the size of the game map
    val picture = new BufferedImage(16 * game.track.map(0).length, 16 * game.track.map.length, BufferedImage.TYPE_INT_ARGB)
    //The picture's graphics
    val g = picture.getGraphics.asInstanceOf[Graphics2D]
    //Set up the right graphics
    map(game.track.map, g)
    //Create the label where the image will be
    val mapLabel = new Label
    //Set the image as an icon on lable
    mapLabel.icon = new ImageIcon(picture)
    
  
    //A BoxPanel with all the game's content; the content of the Frame
    val allItems = {
    val a = new BoxPanel(Orientation.Vertical)
      a.contents += carInfo
      a.contents += mapLabel //theMap
      a.contents += buttons
      a
    }
    
    
    //Sets up the first title, contents and size of the Frame
    //title    = "AFRO POLICE CAR RACER"
    contents += allItems
    //size     = new Dimension(1400, 800)
    
    
    
    
    //Updates gearChange and directionChange to match the character and integer given as parameters
    //Also changes carInfo's text to match the new values
    def updateCarInfo(newGearChange: Char, newDirectionChange: Int) = {
      gearChange = newGearChange
      directionChange = newDirectionChange
      carInfo.text = "The gear change is: " + gearChange + "\nThe direction change is : " + directionChange
    }
    
    
    
    
    //Takes an array (map of the race track) and graphics as parameters
    //Changes then those graphics as the chracterMap of the array instruct
    def map(track: Array[Array[Char]], g: Graphics2D) = {
      
      
      //The loops that goes through all the characters in the array
      for {
        j <- 0 until track.length
        i <- 0 until track(0).length
      } {
        //The method matching the the character is chosen
        track(j)(i) match {
          case 'T' => wall(i, j)
          case 'F' => finishLine(i, j)
          case 'A' => carA(i, j)
          case 'B' => carB(i, j)
          case _   => road(i, j)
        }
        
        //Lines separating the different squares are drawn
        g.setColor(Color.black)
        g.fillRect(i * 16, j * 16, 16, 1)
        g.fillRect(i * 16, j * 16, 1, 16)
      }
      
      //Methods each drawing their specified square
      
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
      
    }
    
  }
  
  
  
  def top = new MainFrame {
    title    = "AFRO POLICE CAR RACER"
    contents = gamePanel
    size     = new Dimension(1400, 800)
      /*
    
    
    
    val player1Name = "car1"
    val player2Name = "car2"
  
    //File is read and th information is saved to fileInfo as String
    val filename = "/Users/rekowenell/git/formula-peli/maps/RaceTrackTest01.txt"
    val file = Source.fromFile(filename)
    val pureFileInfo = file.toVector.mkString
    val fileInfo: String = pureFileInfo.filter( _ != '\n' )
    file.close
  
    //A new game is created
    val game = new Game(fileInfo, player1Name, player2Name)
    val array = game.track.map
  
  
  
    //Store currently chosen gear and direction changes
    var gearChange: Char = '='
    var directionChange: Int = 0
  
  
  
    //Label of car information
    //Includes information about currently chosen gear and direction change
    val carInfo = new Label("The gear change is: " + gearChange + "\nThe direction change is : " + directionChange + "\nCarInTurn: " + game.carInTurn.driver.name)
  
    //Sets up a BoxPanel for the buttons the player can interact with
    //Includes buttons for choosing gearChange, choosing direction change, and confirming the changes
    //Also sets the application up to listen to those buttons
    val buttons = new BoxPanel(Orientation.Horizontal)
    val upGearButton = Button("Up the Gear") { updateCarInfo('+', directionChange) }
      buttons.contents += upGearButton
      this.listenTo(upGearButton)
    val keepGearButton = Button("Keep the same Gear") { updateCarInfo('=', directionChange) }
      buttons.contents += keepGearButton
      this.listenTo(keepGearButton)
    val downGearButton = Button("Down the Gear") { updateCarInfo('-', directionChange) }
      buttons.contents += downGearButton
      this.listenTo(downGearButton)
    val clockwiseDirectionButton = Button("Turn clockwise") { updateCarInfo(gearChange, 1) }
      buttons.contents += clockwiseDirectionButton
      this.listenTo(clockwiseDirectionButton)
    val keepDirectionButton = Button("Keep the current direction") { updateCarInfo(gearChange, 0) }
      buttons.contents += keepDirectionButton
      this.listenTo(keepDirectionButton)
    val counterClockwiseDirectionButton = Button("Turn counterClockwise") { updateCarInfo(gearChange, -1) }
      buttons.contents += counterClockwiseDirectionButton
      this.listenTo(counterClockwiseDirectionButton)
    val driveButton = Button("Confirm choices and drive") {
        //Advances the game one turn
        game.playTurn(gearChange, directionChange)
        //Draws the new game map into picture
        map(game.track.map, g)
        //Sets a new icon based on the updated picture into mapLabel
        mapLabel.icon = new ImageIcon(picture)
        //Sets the changes back to neutral for the next player
        updateCarInfo('=', 0)
      }
      buttons.contents += driveButton
      this.listenTo(driveButton)
  
  
    
  //Defines what happens when a particular reaction is triggered
    /*this.reactions += {
      case clickEvent: ButtonClicked if clickEvent.source == upGearButton => updateCarInfo('+', directionChange)
      case clickEvent: ButtonClicked if clickEvent.source == keepGearButton => updateCarInfo('=', directionChange)
      case clickEvent: ButtonClicked if clickEvent.source == downGearButton => updateCarInfo('-', directionChange)
      case clickEvent: ButtonClicked if clickEvent.source == clockwiseDirectionButton => updateCarInfo(gearChange, 1)
      case clickEvent: ButtonClicked if clickEvent.source == keepDirectionButton => updateCarInfo(gearChange, 0)
      case clickEvent: ButtonClicked if clickEvent.source == counterClockwiseDirectionButton => updateCarInfo(gearChange, -1)
      
        
      case clickEvent: ButtonClicked if clickEvent.source == driveButton => {
             
        //Advances the game one turn
        game.playTurn(gearChange, directionChange)
        //Draws the new game map into picture
        map(game.track.map, g)
        //Sets a new icon based on the updated picture into mapLabel
        mapLabel.icon = new ImageIcon(picture)
        
        //Sets the changes back to neutral for the next player
        updateCarInfo('=', 0)
      }
      
    }*/
    
    //Creates a BufferedImage the size of the game map
    val picture = new BufferedImage(16 * game.track.map(0).length, 16 * game.track.map.length, BufferedImage.TYPE_INT_ARGB)
    //The picture's graphics
    val g = picture.getGraphics.asInstanceOf[Graphics2D]
    //Set up the right graphics
    map(game.track.map, g)
    //Create the label where the image will be
    val mapLabel = new Label
    //Set the image as an icon on lable
    mapLabel.icon = new ImageIcon(picture)
    
  
    //A BoxPanel with all the game's content; the content of the Frame
    val allItems = {
    val a = new BoxPanel(Orientation.Vertical)
      a.contents += carInfo
      a.contents += mapLabel //theMap
      a.contents += buttons
      a
    }
    
    
    //Sets up the first title, contents and size of the Frame
    title    = "AFRO POLICE CAR RACER"
    contents = allItems
    size     = new Dimension(1400, 800)
    
    
    
    
    //Updates gearChange and directionChange to match the character and integer given as parameters
    //Also changes carInfo's text to match the new values
    def updateCarInfo(newGearChange: Char, newDirectionChange: Int) = {
      gearChange = newGearChange
      directionChange = newDirectionChange
      carInfo.text = "The gear change is: " + gearChange + "\nThe direction change is : " + directionChange
    }
    
    
    
    
    //Takes an array (map of the race track) and graphics as parameters
    //Changes then those graphics as the chracterMap of the array instruct
    def map(track: Array[Array[Char]], g: Graphics2D) = {
      
      
      //The loops that goes through all the characters in the array
      for {
        j <- 0 until track.length
        i <- 0 until track(0).length
      } {
        //The method matching the the character is chosen
        track(j)(i) match {
          case 'T' => wall(i, j)
          case 'F' => finishLine(i, j)
          case 'A' => carA(i, j)
          case 'B' => carB(i, j)
          case _   => road(i, j)
        }
        
        //Lines separating the different squares are drawn
        g.setColor(Color.black)
        g.fillRect(i * 16, j * 16, 16, 1)
        g.fillRect(i * 16, j * 16, 1, 16)
      }
      
      //Methods each drawing their specified square
      
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
      
    }
    */
  }
  

}