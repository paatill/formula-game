package formula.GUI
import formulaPlay._

object GameGUI extends App {
  
  this.run()
  
  private def run() = {
    println("The race is about to begin!")
    val player1Name = readLine("\nType your name, player 1.\n")
    println("Your chosen name is " + player1Name)
    val player2Name = readLine("\nType your name, player 2.\n")
    println("Your chosen name is " + player2Name)
    val game = new Game("FORMULAMAPREC3JIM15MAP4040"
        + "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT"
        + "        F                               "
        + "        F                               "
        + "        F                               "
        + "        F     Y                         "
        + "        F                               "
        + "FFFFFFFFF                               "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                                        "
        + "                               X        "
        + "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT"
        + "END", player1Name, player2Name)
    println("\n")
    for (line <- game.track.firstMap) {
        var string = ""
        line.foreach( string += _ )
        println(string)
    }
    println("\n")
    
    while (!game.isOver) {
      println("It is your turn player " + (if (game.turn % 2 == 0) player1Name else player2Name))
      val gearChange = readLine("\nYour Gear is " + game.carInTurn.gearManager.gear.speed + ".\nType + to increase, - to decrease or = to keep the same gear.\n")(0)
      val direction0 = game.carInTurn.gearManager.gear.direction(0)
      val direction1 = game.carInTurn.gearManager.gear.direction(1)
      val directionChange = readLine("Your direction is (" + direction0 + ", " + direction1 + ").\nType 1 for clockwise, -1 for counterclockwise or 0 to keep it same.\n").toInt
      println("\n\nTrack record by " + game.recordHolderName + " is " + game.recordTime + " rounds.\n" + "Current situation:\nRound " + (game.turn / 2) + "\n")
      for (line <- game.playTurn(gearChange, directionChange)) {
        var string = ""
        line.foreach( string += _ )
        println(string)
      }
      println("\n")
    }
    println("The game has ended!")
  }
  
}