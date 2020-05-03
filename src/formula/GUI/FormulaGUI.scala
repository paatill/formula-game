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




object FormulaGUI extends SimpleSwingApplication {
  
  def top() = frame
  
  println("TAPPAJAHAI")
  val player1Info = getPlayerName('1')
  val player2Info = getPlayerName('2')
  
  //Game track information
  val (fileInfo, pureFileInfo, filePath) = TextFileReader("/racetracks")
  
  
  //Access to the internal logic of the game
  val game = new Game(fileInfo, player1Info, player2Info)
  val gameTrack = game.track
  //Presents the first player with possible outcomes
  val beginningTrack = game.possibilitiesTrack
  //A square represents a single character in the track's Array[Array[Char]]
  val squareSide = 16
  val map = new GraphicsMapper(squareSide, game)
  
  
  //Store currently chosen gear and direction changes
  private var gearChange: Char = '='
  private var directionChange: Int = 0
  
  val generalInfo =         new Label("Here is some information about the situation:")
  val gearChangeInfo =      new Label("Gear change is " + gearChange)
  val directionChangeInfo = new Label("Direction change is " + directionChange)
  val driverInTurnInfo =    new Label("Driver in turn is " + game.inTurnCar.driver.name)
  val roundInfo =           new Label("Round is " + TurnCounter.round)
  val gearInfo =            new Label("Gear is " + game.inTurnCarGear)
  
  
  //All the textual information the player gets
  val gameInfo = new BoxPanel(Orientation.Vertical)
    gameInfo.contents += generalInfo
    gameInfo.contents += gearChangeInfo
    gameInfo.contents += directionChangeInfo
    gameInfo.contents += driverInTurnInfo
    gameInfo.contents += roundInfo
    gameInfo.contents += gearInfo
    gameInfo.contents.foreach(this.setGameInfoFont(_))
  
  
  
  
  //Sets up a GridPanel for buttons used to control the car
  //Includes buttons for altering gearChange and directionChange
  val upGearButton =                    Button("Raise Gear")            { carControlButtonPushed('+', directionChange) }
  val keepGearButton =                  Button("Keep Gear")             { carControlButtonPushed('=', directionChange) }
  val downGearButton =                  Button("Down Gear")             { carControlButtonPushed('-', directionChange) }
  val counterClockwiseDirectionButton = Button("Turn counterclockwise") { carControlButtonPushed(gearChange, -1) }
  val keepDirectionButton =             Button("Keep direction")        { carControlButtonPushed(gearChange, 0) }
  val clockwiseDirectionButton =        Button("Turn clockwise")        { carControlButtonPushed(gearChange, 1) }
    
  val carControlButtons = new GridPanel(2, 3)
    carControlButtons.contents += upGearButton
    carControlButtons.contents += keepGearButton
    carControlButtons.contents += downGearButton
    carControlButtons.contents += counterClockwiseDirectionButton
    carControlButtons.contents += keepDirectionButton
    carControlButtons.contents += clockwiseDirectionButton
  
  //Advances the game by one turn when pressed
  val driveButton = Button("Confirm choices and drive") {
    map(game.playTurn(gearChange, directionChange), graphics)     //Advances the game one turn and draws the new game map into picture
    mapLabelUpdate()
    updateGameInfo('=', 0)                                 //Sets gearChange and directionChange back to neutral for the next player
    situationInfoUpdate()
    if (game.over) {
      executeGameEnding
    }
  }
  
  
  
  val recordText = game.recordHolderName + "---" + game.recordTime
  val recordButton = Button("Show record time") { Dialog.showMessage(allItems, recordText, title = "Track Record") }
  
  //When pressed, switches in a map showing all the possible destinations the player may drive this turn
  //Those possibilities, however, ignore all barriers on their way
  val showAllOptionsButton = Button("Show all options") {
    map(game.inTurnCar.seekPossibilities(gameTrack), graphics)
    mapLabelUpdate()
  }
  
  //All buttons used for considerably altering what is shown to players
  val gameControlButtons = new BoxPanel(Orientation.Vertical)
  gameControlButtons.contents += recordButton
  gameControlButtons.contents += driveButton
  gameControlButtons.contents += showAllOptionsButton
  
  //The buttons have an unalterable, pre-set size
  val gameControlButtonSize = new Dimension(200, 100)
  gameControlButtons.contents.foreach(setComponentSize(_, gameControlButtonSize))
  
  
  
  //Creates a BufferedImage according to the size of the game track
  val mapWidth = squareSide * beginningTrack(0).length
  val mapHeight = squareSide * beginningTrack.length
  val Picture = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB)
  
  val graphics = Picture.getGraphics.asInstanceOf[Graphics2D]     //The picture's graphics
  map(beginningTrack, graphics)                                   //Set the graphics to correspond with track
  val mapLabel = new Label                                        //Create the label where the image will be
  mapLabelUpdate()                                                //Set picture as an icon on lable
  
  
  //BoxPanel with most of the game's content
  val allButControlButtons = {
  val a = new BoxPanel(Orientation.Horizontal)
    a.contents += Swing.VStrut(10)
    a.contents += Swing.Glue
    a.contents += gameInfo
    a.contents += Swing.VStrut(10)
    a.contents += Swing.Glue
    a.contents += mapLabel
    a.contents += Swing.VStrut(10)
    a.contents += Swing.Glue
    a.contents += gameControlButtons
    a.contents += Swing.VStrut(10)
    a.contents += Swing.Glue
    a
  }

  //BoxPanel with all of the game's content
  val allItems = new BoxPanel(Orientation.Vertical)
  allItems.contents += allButControlButtons
  allItems.contents += Swing.VStrut(10)
  allItems.contents += Swing.Glue
  allItems.contents += carControlButtons
  
  //The Frame shown to user
  val frame = new MainFrame {
    title    = "CAR RACER"
    contents = allItems
  }
  //Frame is set to the smallest possible size where all the elements are still shown
  //This is then set as the frame's smallest possible size
  //Finally, the frame is set to became as large as the screen allows
  frame.pack()
  val frameWidth = frame.size.getWidth.toInt
  val frameHeight = frame.size.getHeight.toInt
  val frameMinimumDimension = new Dimension(frameWidth, frameHeight)
  frame.minimumSize = frameMinimumDimension
  frame.maximize()
  
  
  
  
  
  
  //The following are helper methods used above
  
  //Updates gearInfo and roundInfo
  def situationInfoUpdate() = {
    gearInfo.text = "Gear is " + game.inTurnCarGear
    roundInfo.text = "Round is " + TurnCounter.round
  }
  
  //Sets a new icon based on the updated Picture into mapLabel
  def mapLabelUpdate() = {
    mapLabel.icon = new ImageIcon(Picture)
  }
  
  //Updates gearChange and directionChange, then draws a corresponding map for the player
  def carControlButtonPushed(newGearChange: Char, newDirectionChange: Int) = {
    updateGameInfo(newGearChange, newDirectionChange)
    updateMap
  }
  
  //Updates gearChange and directionChange to match the character and integer given as parameters
  //Also changes gameInfo's text to match the new values
  def updateGameInfo(newGearChange: Char, newDirectionChange: Int) = {
    gearChange = newGearChange
    directionChange = newDirectionChange
    gearChangeInfo.text = "Gear change is " + (gearChange match {
      case '-' => "down"
      case '+' => "up"
      case _   => "none"
    })
    directionChangeInfo.text =  "Direction change is " + (directionChange match {
      case -1 => "counterclockwise"
      case 1  => "clockwise"
      case 0  => "none"
    })
    driverInTurnInfo.text = "Driver in turn is " + game.inTurnCar.driver.name
    
  }
  
  //Updates the map featuring the single possible destination based on the car's current gearChange and directionChange
  //Called only when one of the carControlButtons is pushed
  def updateMap = {
    map(game.possibilityTrack(gearChange, directionChange), graphics)
    mapLabelUpdate()
  }
  
  def setComponentSize(component: Component, dimension: Dimension) = {
  component.minimumSize = dimension
  component.maximumSize = dimension
  component.preferredSize = dimension
  }
  
  def setGameInfoFont(component: Component) = {
    val fontSize = 16
    component.font_=(component.font.deriveFont(fontSize))
  }
  
  
  //Calls a Dialog where the player will enter their name
  def getPlayerName(playerNumber: Char): String = {
    //This variable tracks whether a name of correct name is typed in the following Dialog
    //Must be true for the following loop to end
    var nameLengthRight = false
    var playerInfo = ""
    //Creates new dialogs asking for a player's name until one that is no longer than 13 characters is given
    while(!nameLengthRight) {
      val howToChooseDriver = Dialog.showConfirmation(new Dialog, "Do you want to choose an existing driver?", optionType =Dialog.Options.YesNo)
      
      //The application gets shut down if the user presses the red cross
      if (howToChooseDriver == Dialog.Result.Closed) {
        this.quit()
      }
      //If the user wants to use an existing driver, they get to choose a .txt file that should include the information
      //Otherwise they must type a name for their new driver
      if (howToChooseDriver == Dialog.Result.Yes) {
        val (driverFileInfo, driverPureFileInfo, driverFilePath) = TextFileReader("/drivers")
        playerInfo = driverFileInfo
        nameLengthRight = true
      } else {
        val answer = Dialog.showInput[String](new Dialog, "Type your name here, player " + playerNumber + ". It may not be more than 13 characters long.", initial = "")
        
        //Again, the application gets shut down if the user presses the red cross
        if (answer == Dialog.Result.Closed) {
          this.quit()
        }
        //The name will be buffered with some other information, so that the driver's information will be easier to save at the end of the game
        answer match {
          case Some(name) if name.length <= 13 => {
            //As the name length is told in two digits, it may be necessary to add an additional zero
            val nameLengthSpotter = if (name.length < 10) "0" else ""
            val noBlanksName = name.filter(_ != ' ')
            val noBlanksNameLength = noBlanksName.length
            
            playerInfo = (nameLengthSpotter + noBlanksNameLength + noBlanksName + "01LAPTIMES")
            nameLengthRight = true
          }
          case _ => Dialog.showMessage(new Dialog, "The name you typed was too long!") //If the name is too long the loop will continue
        }
      }
    }
    playerInfo
  }
  
  
  
  
  
  //The following methods are all about what happens when the game ends
  
  
  //The method calling all the rest
  //First makes driving further impossible
  //Then sets a new record into the RaceTrack file if necessary
  //Then asks the user whether they wish to save their drivers
  //If yes, the drivers of both cars are saved
  def executeGameEnding {
    setEmptyAction(driveButton)
    if ((TurnCounter.turn / 2) < game.recordTime.toInt) {
      setNewRecordInFile()
    }
    val saveDrivers = Dialog.showConfirmation(allItems, game.victor.getOrElse("MYSTERY DRIVER") + " has won the race! Do you wish to save the information about the drivers?", optionType=Dialog.Options.YesNo)
    if (saveDrivers == Dialog.Result.Yes) {
      saveDriver(game.inTurnCar)
      saveDriver(game.notInTurnCar)
    }
  }
  
  
  
  
  def saveDriver(car: Car) = {
    val driver = car.driver
    val raceTrackName = filePath.reverse.takeWhile(_ != '/').reverse.dropRight(4)
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
    storeDrop(nameLength)
    val howManyTracks = previousInfo.take(2).toInt
    addLine()
    storeDrop(2)
    
    //Tells whether the driver is driving the track for the first time or not
    var isNewTrack = true
    addLine()
    //"LAPTIMES" gets a storeDrop
    storeDrop(8)
    addLine()
    //A loop using storeDrop on one track at a time
    //Ends when previousInfo is all read
    while (previousInfo != "") {
      val trackNameLength = previousInfo.take(2).toInt
      val trackName = previousInfo.take(trackNameLength)
      storeDrop(2 + trackNameLength)
      val trackLaps = previousInfo.take(1).toInt
      storeDrop(1)
      //The entire information about the track is only created if this is the first time the driver drives this particular track
      //Otherwise only this game's lap times get added
      if (trackName == raceTrackName) {
        for (lap <- game.carLapTimes(car)) {
          val lapTime = lapDigitAdder(lap)
          previousInfo.drop(3)
          newInfo = newInfo + lapTime
          addLine()
        }
        //Track is not new if the driver has driven it before
        isNewTrack = false
      } else {
        for (a <- 1 to trackLaps) {
          storeDrop(3)
          addLine()
        }
      }
    }
    //If track is new, it needs to be added to the drivers list from scratch
    if (isNewTrack) {
      val trackName = raceTrackName
      val trackNameLength = trackName.length
      newInfo += trackNameLength
      addLine()
      newInfo += trackName
      addLine()
      newInfo += game.lapInfo
      for (lap <- game.carLapTimes(car)) {
        val lapTime = lapDigitAdder(lap)
        addLine()
        newInfo += lapTime
      }
    }
    //The updated file is written and saved over the old one
    val newFile = new File(System.getProperty("user.dir") + "/drivers/" + driver.name + ".txt")
    val bufferedWriter = new BufferedWriter(new FileWriter(newFile))
    bufferedWriter.write(newInfo)
    bufferedWriter.close
  }
  
  
  
  //A helper method for adding the correct number of zeros to the lap time
  def lapDigitAdder(lap: Int): String = {
    val lapDigits = lap.toString.length
    lapDigits match {
      case 0 => "000"
      case 1 => "00" + lap
      case 2 => "0" + lap
      case 3 => lap.toString
      case _ => "999"
    }
  }
  
  
  def setEmptyAction(button: AbstractButton) = {
      button.action = new Action(button.text) { def apply = Unit }
    }
  
  
  //If a driven has beaten the current record of the track, the record needs to be updated
  def setNewRecordInFile() = {
    val recordDriverName: String = game.victor match {
      case Some(driver) => driver.name
      case None         => "MYSTERY DRIVER"
    }
    //Digits in these required values
    val lineBreak = 1
    val FORMULATRACKRECdigits = 15
    val recordNameLengthDigits = 2
    val recordLengthDigits = 1
    
    //The old record and its driver name are taken out and the new ones are put in their place
    //Rest of the file stays the same
    val record =           (TurnCounter.round)
    val newRecordLength =  record.toString.length
    val afterREC =         pureFileInfo.drop(lineBreak + FORMULATRACKRECdigits )
    val recordNameLength = afterREC.take(recordNameLengthDigits).toInt
    val afterRecordName =  afterREC.drop(recordNameLength + recordNameLengthDigits)
    val recordLength =     afterRecordName.take(recordLengthDigits).toInt
    val afterRecord =      afterRecordName.drop(recordLength + recordLengthDigits)
    val newInfo =          "FORMULATRACK\nREC" + recordDriverName.length + recordDriverName + newRecordLength + record + afterRecord
    
    //Writes an altered file over the original file
    val newFile = new File(filePath)
    val bufferedWriter = new BufferedWriter(new FileWriter(newFile))
    bufferedWriter.write(newInfo)
    bufferedWriter.close
  }
  
  
  
  
     
  
  

}