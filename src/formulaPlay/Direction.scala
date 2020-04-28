package formulaPlay

object Direction {
  def apply(car: Car): String = {
    val gear = car.gearManager.gear
    val directionX = gear.direction(0)
    val directionY = gear.direction(1)
    
    val proportionalX = directionX.toDouble / gear.plusHalf
    val proportionalY = directionY.toDouble / gear.plusHalf
    
    (proportionalX, proportionalY) match {
      case (x, y) if x > 0.5 && y > 0.5     => "DOWNRIGHT"
      case (x, y) if x > -0.5 && y > 0.5    => "DOWNMIDDLE"
      case (x, y) if x <= -0.5 && y > 0.5   => "DOWNLEFT"
      case (x, y) if x > 0.0 && y > -0.5    => "MIDDLELEFT"
      case (x, y) if x <= 0.0 && y > -0.5  => "MIDDLERIGHT"
      case (x, y) if x > 0.5 && y <= -0.5   => "UPRIGHT"
      case (x, y) if x > -0.5 && y <= -0.5  => "UPMIDDLE"
      case (x, y) if x <= -0.5 && y <= -0.5 => "UPLEFT"
    }
    
  }
}