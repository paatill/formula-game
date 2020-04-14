package formulaPlay

object TurnCounter {
  private var currentTurn = 0
  private var currentRound = 0
  
  def advanceTurn = {
    currentTurn += 1
    currentRound = currentTurn / 2
  }
  
  def turn = currentTurn
  def round = currentRound
  
}