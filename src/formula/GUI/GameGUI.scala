package formula.GUI
import formulaPlay._
import scala.io.Source
import java.io._

object GameGUI extends App {
  
  this.run()
  
  private def run() = {
    
    val filename = "/Users/rekowenell/git/formula-peli/maps/RaceTrackTest01.txt"
    val file = Source.fromFile(filename)
    val pureFileInfo = file.toVector.mkString
    val fileInfo: String = pureFileInfo.filter( _ != '\n' )
    file.close
    println(pureFileInfo)
    println("The race is about to begin!")
    val player1Name = readLine("\nType your name, player 1.\n")
    println("Your chosen name is " + player1Name)
    val player2Name = readLine("\nType your name, player 2.\n")
    println("Your chosen name is " + player2Name)
    val game = new Game(fileInfo, player1Name, player2Name)
    println("\n")
    for (line <- game.track.firstMap) {
        var string = ""
        line.foreach( string += _ )
        println(string)
    }
    println("\n")
    
    while (!game.over) {
      println("It is your turn player " + (if (TurnCounter.turn % 2 == 0) player1Name else player2Name))
      val gearChange = readLine("\nYour Gear is " + game.inTurnCar.gearManager.gear.speed + ".\nType + to increase, - to decrease or = to keep the same gear.\n")(0)
      val direction0 = game.inTurnCar.gearManager.gear.direction(0)
      val direction1 = game.inTurnCar.gearManager.gear.direction(1)
      val directionChange = readLine("Your direction is (" + direction0 + ", " + direction1 + ").\nType 1 for clockwise, -1 for counterclockwise or 0 to keep it same.\n").toInt
      println("\n\nTrack record by " + game.recordHolderName + " is " + game.recordTime + " rounds.\n" + "Current situation:\nRound " + (TurnCounter.turn / 2) + "\n")
      for (line <- game.playTurn(gearChange, directionChange)) {
        var string = ""
        line.foreach( string += _ )
        println(string)
      }
      for (line <- game.track.map) {
        var string = ""
        line.foreach( string += _ )
        println(string)
      }
      println("\n")
    }
    println("The game has ended!")
    if ((TurnCounter.turn / 2) < game.recordTime.toInt) {
      val recordDriverName: String = game.victor match {
        case Some(driver) => driver.name.toUpperCase
        case None         => "MYSTERY DRIVER"
      }
      
      val record = (TurnCounter.turn / 2)
      val newRecordLength = record.toString.length
      val afterREC = pureFileInfo.drop(14)
      val recordNameLength = afterREC.take(1).toInt
      val afterRecordName = afterREC.drop(recordNameLength + 1)
      val recordLength = afterRecordName.take(1).toInt
      val afterRecord = afterRecordName.drop(recordLength + 1)
      val newInfo = "FORMULAMAP\nREC" + recordDriverName.length + recordDriverName + newRecordLength + record + afterRecord
      val newFile = new File(filename)
      val bw = new BufferedWriter(new FileWriter(newFile))
      bw.write(newInfo)
      bw.close
    }
  }
  
}