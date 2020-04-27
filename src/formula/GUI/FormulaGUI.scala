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




object FormulaGUI extends SimpleSwingApplication {
  
  
  
  
  
  
  val gamePanel = new BoxPanel(Orientation.Vertical) {
    
    val squareSide = 16
    
    //Set up player names
    val player1Info = getPlayerName('1')
    val player2Info = getPlayerName('2')
    
    //Calls a Dialog where the players will enter their names
    def getPlayerName(playerNumber: Char): String = {
      //This variable tracks whether a name of correct name is typed in the following Dialog
      //Must be true for the following loop to end
      var nameLengthRight = false
      var playerName = ""
      
      //Creates new dialogs asking for a player's name until they give one that is no longer than 9 characters
      while(!nameLengthRight) {
        val howToChooseDriver = Dialog.showConfirmation(new Dialog, "Do you want to choose an existing driver?", optionType =Dialog.Options.YesNo)
        
        if (howToChooseDriver == Dialog.Result.Yes) {
          val driverFileFilter = new FileFilter {
          def accept(pathname: File): Boolean = pathname.getName.endsWith(".txt")
          def getDescription: String = "Only .txt files"
          }
          val driverFileChooser = new FileChooser(new File(System.getProperty("user.dir") + "/drivers"))
          driverFileChooser.fileFilter_=(driverFileFilter)
          val driverSelectedFile = {
            var driverFileIsChosen = false
            while (!driverFileIsChosen) {
              val aDialog = driverFileChooser.showOpenDialog(new Dialog)
              if (aDialog == FileChooser.Result.Approve) driverFileIsChosen = true
            }
            driverFileChooser.selectedFile
          }
          
          val driverFile = Source.fromFile(driverSelectedFile)
          val driverFileInfo = driverFile.mkString
          driverFile.close
          val driverNameLength = driverFileInfo.take(2).toInt
          playerName = driverFileInfo
          nameLengthRight = true
          
          
        } else {
          val answer = Dialog.showInput[String](new Dialog, "Type your name here, player " + playerNumber + ". It may not be more than 24 characters long.", initial = "")
          answer match {
            case Some(name) if name.length <= 24 => playerName = ((if (name.length < 10) "0" else "") + name.filter(_ != ' ').length + name.filter(_ != ' ') + "00LAPTIMES"); nameLengthRight = true
            case _ => Dialog.showMessage(new Dialog, "The name you typed was too long!")
          }
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
    val fileChooser = new FileChooser(new File(System.getProperty("user.dir") + "/maps"))
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
  
    //A new game is created
    val game = new Game(fileInfo, player1Info, player2Info)
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
    val buttonGroup = new ButtonGroup
    val upGearButton = Button("Up the Gear") { updateCarInfo('+', directionChange) }
      buttonGroup.buttons += upGearButton
    val keepGearButton = Button("Keep the same Gear") { updateCarInfo('=', directionChange) }
      buttonGroup.buttons += keepGearButton
    val downGearButton = Button("Down the Gear") { updateCarInfo('-', directionChange) }
      buttonGroup.buttons += downGearButton
    val clockwiseDirectionButton = Button("Turn clockwise") { updateCarInfo(gearChange, 1) }
      buttonGroup.buttons += clockwiseDirectionButton
    val keepDirectionButton = Button("Keep the current direction") { updateCarInfo(gearChange, 0) }
      buttonGroup.buttons += keepDirectionButton
    val counterClockwiseDirectionButton = Button("Turn counterClockwise") { updateCarInfo(gearChange, -1) }
      buttonGroup.buttons += counterClockwiseDirectionButton
      
    val driveButton = Button("Confirm choices and drive") {
        map(game.playTurn(gearChange, directionChange), g)     //Advances the game one turn and draws the new game map into picture
        mapLabel.icon = new ImageIcon(picture)                 //Sets a new icon based on the updated picture into mapLabel
        updateCarInfo('=', 0)                                  //Sets the changes back to neutral for the next player
        
        if (game.over) {
          executeGameEnding
        }
      }
      buttonGroup.buttons += driveButton
      this.listenTo(driveButton)
      
      val buttons = new BoxPanel(Orientation.Horizontal)
      buttons.contents ++= buttonGroup.buttons
      
    
  
    
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
    val picture = new BufferedImage(squareSide * game.track.map(0).length, squareSide * game.track.map.length, BufferedImage.TYPE_INT_ARGB)
      
    val g = picture.getGraphics.asInstanceOf[Graphics2D]     //The picture's graphics
    map(game.track.map, g)                                   //Set up the right graphics
    val mapLabel = new Label                                 //Create the label where the image will be
    mapLabel.icon = new ImageIcon(picture)                   //Set the image as an icon on lable
    
  
    //BoxPanel with all the game's content; the content of the Frame
    val allItems = {
    val a = new BoxPanel(Orientation.Vertical)
      a.contents += carInfo
      a.contents += mapLabel
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
          case 'A' => carA(i, j)
          case 'B' => carB(i, j)
          case 'a' => carAWasHere(i, j)
          case 'b' => carBWasHere(i, j)
          case '-' => downGear(i, j)
          case '+' => upGear(i, j)
          case '=' => keepGear(i, j)
          case _   => road(i, j) 
        }
        
        //Lines separating the different squares are drawn
        g.setColor(Color.black)
        g.fillRect(i * squareSide, j * squareSide, squareSide, 1)
        g.fillRect(i * squareSide, j * squareSide, 1, squareSide)
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
        g.setColor(Color.gray)
        g.fillRect(i * squareSide, j * squareSide, squareSide, squareSide)
      }
      
      def carA(i: Int, j: Int) = {
        //g.setColor(Color.green)
        //g.fillRect(i * squareSide, j * squareSide, squareSide, squareSide)
        val upMiddle = Vector(
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'B', 'n', 'n', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'n', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'n', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'n', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'n', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'B', 'B', 'B', 'n', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'B', 'B', 'B', 'n', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n')
        )
        val temporaryDownMiddle = upMiddle.toBuffer
        temporaryDownMiddle += upMiddle(0)
        val downMiddle = temporaryDownMiddle.reverse
        downMiddle.drop(1)
        
        
        val middleRight = upMiddle.toArray.map(_.toArray)
        for {
          j <- middleRight.indices
          i <- middleRight(0).indices
        } {
          middleRight(j)(i) = upMiddle(i)(j)
        }
        
        val middleLeft = middleRight.clone().map(_.reverse)
        for {
          j <- middleLeft.indices
          i <- middleLeft(0).indices
        } {
          println("j,i :" + middleLeft(j)(i))
          middleLeft(j)((i + 1) % 16) = middleLeft(j)(i)
          
          println("j,i + 1 % 16 :" + middleLeft(j)((i + 1) % 16))
        }
        
        for (line <- middleLeft) {
        var string = ""
        line.foreach( string += _ )
        println(string)
    }
        
        searchMapProper(game.track.emptyMap, i, j)
        for {
          x <- middleLeft(0).indices
          y <- middleLeft.indices
        } {
          middleLeft(y)(x) match {
            case 'D' => g.setColor(Color.black); g.fillRect(i * squareSide + x, j * squareSide + y, 1, 1)
            case 'B' => g.setColor(Color.blue); g.fillRect(i * squareSide + x, j * squareSide + y, 1, 1)
            case _ => Unit
          }
          
        }
      }
      
      def carAWasHere(i: Int, j: Int) = {
        searchMapProper(game.track.emptyMap, i, j)
        g.setColor(Color.cyan)
        g.fillArc(i * squareSide + 4, j * squareSide + 4, 8, 8, 0, 360)
      }
      
      def carB(i: Int, j: Int) = {
        g.setColor(Color.red)
        g.fillRect(i * squareSide, j * squareSide, squareSide, squareSide)
      }
      
      def carBWasHere(i: Int, j: Int) = {
        searchMapProper(game.track.emptyMap, i, j)
        g.setColor(Color.magenta)
        g.fillArc(i * squareSide + 4, j * squareSide + 4, 8, 8, 0, 360)
      }
      
      def finishLine(i: Int, j: Int) = {
        val baseILength = i * squareSide
        val baseJLength = j * squareSide
        for {
          x <- 0 until 5
          y <- 0 until 5
        } {
          if (x % 2 == y % 2) {
            g.setColor(new Color(155, 135, 12))
            g.fillRect(baseILength + (x * 3), baseJLength + (y * 3), 3, 3)
          } else {
            g.setColor(Color.white)
            g.fillRect(baseILength + (x * 3), baseJLength + (y * 3), 3, 3)
          }
        }
      }
      
      def road(i: Int, j: Int) = {
        g.setColor(Color.green)
        g.fillRect(i * squareSide, j * squareSide, squareSide, squareSide)
        /*val baseILength = i * squareSide
        val baseJLength = j * squareSide
        for {
          x <- 0 until 16
          y <- 0 until 16
        } {
          if (x % 2 == y % 2) {
            g.setColor(Color.LIGHT_GRAY)
            g.fillRect(baseILength + x, baseJLength + y, 1, 1)
          } else {
            g.setColor(Color.DARK_GRAY)
            g.fillRect(baseILength + x, baseJLength + y, 1, 1)
          }
        }*/
      }
      
      def upGear(i: Int, j: Int) = {
        searchMapProper(game.track.map, i, j)
        g.setColor(if (game.inTurnCar == game.car1) Color.blue else Color.red)
        g.fillRect(i * squareSide + 2, j * squareSide + 7, squareSide - 4, 2)
        g.fillRect(i * squareSide + 7, j * squareSide + 2, 2, squareSide - 4)
      }
      
      def keepGear(i: Int, j: Int) = {
        searchMapProper(game.track.map, i, j)
        g.setColor(if (game.inTurnCar == game.car1) Color.blue else Color.red)
        g.fillRect(i * squareSide + 2, j * squareSide + 5, squareSide - 4, 2)
        g.fillRect(i * squareSide + 2, j * squareSide + 9, squareSide - 4, 2)
      }
      
      def downGear(i: Int, j: Int) = {
        searchMapProper(game.track.map, i, j)
        g.setColor(if (game.inTurnCar == game.car1) Color.blue else Color.red)
        g.fillRect(i * squareSide + 2, j * squareSide + 7, squareSide - 4, 2)
      }
      
      
      
      
      
      
      
    }
    
    
    
    def executeGameEnding {
      buttonGroup.buttons.foreach(setEmptyAction(_))
      if ((TurnCounter.turn / 2) < game.recordTime.toInt) {
        setNewRecordInFile()
      }
      val saveDrivers = Dialog.showConfirmation(new Dialog, "Do you wish to save the information about the drivers?", optionType=Dialog.Options.YesNo)
      if (saveDrivers == Dialog.Result.Yes) {
        saveDriver(game.car1)
        saveDriver(game.car2)
      }
      
    }
    
    def saveDriver(car: Car) = {
      //Driver of the car
      val driver = car.driver
      
      //Driver's information
      var previousInfo = driver.info
      //The new information to be saved
      var newInfo = ""
      
      //A method for storing from previousInfo into newInfo and deleting the stored info from previousInfo
      def storeDrop(amount: Int) = {
        newInfo = newInfo + previousInfo.take(amount)
        previousInfo = previousInfo.drop(amount)
      }
      
      //Adds a single line
      def addLine() = {
        newInfo = newInfo + "\n"
      }
      
      //Saves and storeDrops the first two characters who determine the length of the name
      val nameLength = previousInfo.take(2).toInt
      storeDrop(2)
      addLine()
      storeDrop(nameLength)
      val howManyTracks = previousInfo.take(2).toInt
      addLine()
      
      storeDrop(2)
      var isNewTrack = true
      addLine()
      
      storeDrop(8)
      addLine()
      
      
      while (previousInfo != "") {
        val trackNameLength = previousInfo.take(2).toInt
        val trackName = previousInfo.take(trackNameLength)
        storeDrop(trackNameLength)
        val trackLaps = previousInfo.take(2).toInt
        storeDrop(2)
        
        if (trackName == filePath.reverse.takeWhile(_ != '/').reverse.dropRight(4)) {
          for (lap <- car.gearManager.lapTimes) {
            val lapTime: String = lap.toString.length match {
              case 0 => "000"
              case 1 => "00" + lap
              case 2 => "0" + lap
              case 3 => lap.toString
              case _ => "999"
            }
            previousInfo.drop(3)
            newInfo = newInfo + lapTime
            addLine()
          }
          isNewTrack = false
        } else {
          for (a <- 1 to trackLaps) {
            storeDrop(3)
            addLine()
          }
        }
      }
      
      if (isNewTrack) {
        val trackName = filePath.reverse.takeWhile(_ != '/').reverse.dropRight(4)
        val trackNameLength = trackName.length
        newInfo += trackNameLength
        addLine()
        newInfo += trackName
        addLine()
        newInfo += game.lapInfo
        for (lap <- car.gearManager.lapTimes) {
          val lapTime = lap.toString.length match {
            case 0 => "000"
            case 1 => "00" + lap
            case 2 => "0" + lap
            case 3 => lap
            case _ => "999"
          }
          addLine()
          newInfo += lapTime

        }
      }
      
      
      val newFile = new File(System.getProperty("user.dir") + "/drivers/" + driver.name + ".txt")
      val bufferedWriter = new BufferedWriter(new FileWriter(newFile))
      bufferedWriter.write(newInfo)
      bufferedWriter.close
    }
    
    
    def setEmptyAction(button: AbstractButton) = {
        button.action_=(new Action(button.text) { def apply = Unit })
      }
    
    def setNewRecordInFile() = {
      val recordDriverName: String = game.victor match {
        case Some(driver) => driver.name.toUpperCase
        case None         => "MYSTERY DRIVER"
      }
      val record = (TurnCounter.round)
      val newRecordLength = record.toString.length
      val afterREC = pureFileInfo.drop(14)
      val recordNameLength = afterREC.take(1).toInt
      val afterRecordName = afterREC.drop(recordNameLength + 1)
      val recordLength = afterRecordName.take(1).toInt
      val afterRecord = afterRecordName.drop(recordLength + 1)
      val newInfo = "FORMULAMAP\nREC" + recordDriverName.length + recordDriverName + newRecordLength + record + afterRecord
      val newFile = new File(filePath)
      val bufferedWriter = new BufferedWriter(new FileWriter(newFile))
      bufferedWriter.write(newInfo)
      bufferedWriter.close
  }
    
    
    
    
  }
  
  
  
  
  def top() = new MainFrame {
    title    = "CAR RACER"
    contents = gamePanel
    size     = new Dimension(1400, 800)
     
  }
  

}