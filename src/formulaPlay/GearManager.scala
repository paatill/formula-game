package formulaPlay
import scala.collection.mutable
import exceptions._
import scala.math._

class GearManager {
  val one = new GearOne
  val two = new GearTwo
  val three = new GearThree
  val four = new GearFour
  val five = new GearFive
  
  var gear: Gear = one
  var gearNumber = 1
  var generalDirection: GeneralDirection = Left

  
  
  //Changes gearNumber and calls for changeGear if necessary
  def changeGearNumber(change: Char): Unit = {
    change match {
      case '+' if gearNumber < 5 => gearNumber += 1; changeGear
      case '-' if gearNumber > 1 => gearNumber -= 1; changeGear
      case _ =>
    }
  }
  
  //Changes the gear to match the gearNumber
  def changeGear: Unit = {
    val newGear = gearNumber match {
      case 1 => one
      case 2 => two
      case 3 => three
      case 4 => four
      case 5 => five
      case _ => throw new CaseNotMatchedException("gearNumber is not between 1 and 5")
    }
    
    
    val x: Double = gear.direction(0)
    val y: Double = gear.direction(1)
    val c: Double = sqrt(pow(y, 2) + pow(x, 2))
    println("X: " + x)
    println("Y: " + y)
    
    if (generalDirection == Left || generalDirection == Right) {
      val b = if (x > 0) newGear.plusHalf else newGear.minusHalf
      val a = if (y != 0) y / gear.plusHalf * newGear.plusHalf else 0
      newGear.direction(0) = b
      newGear.direction(1) = rint(a).toInt
    } else {
      val a = if (y > 0) newGear.plusHalf else newGear.minusHalf
      val b = if (x != 0) y / gear.plusHalf * newGear.plusHalf else 0
      newGear.direction(0) = rint(b).toInt
      newGear.direction(1) = a
    }
    
    
    
    /*
    if (generalDirection == Left || generalDirection == Right) {
      val sinAlpha = y / c
      println("SinAlpha: " + sinAlpha)
      val b = if (x > 0) newGear.plusHalf else newGear.minusHalf
      println("Unknown A-route\nB equals " + b)
      val sign = if (y < 0) -1 else 1
      println("Sign is " + sign)
      val divisor = sqrt(1 - pow(sinAlpha, 2))
      println("Divisor meaning sqrt(1 - sinAlpha^2) is " + divisor)
      val a = sign * (b * sinAlpha / divisor)
      println("A meaning sign * (b * sinAlpha / divisor) is " + a)
      
      newGear.direction(0) = b
      newGear.direction(1) = rint(a).toInt
      
    } else {
      val cosAlpha = x / c
      println("CosAlpha: " + cosAlpha)
      val a = if (y > 0) newGear.plusHalf else newGear.minusHalf
      println("Unknown B-route\nA equals " + a)
      val sign = if (x < 0) -1 else 1
      println("Sign is " + sign)
      val multiplier = sqrt(1 - pow(cosAlpha, 2))
      println("Multiplier meaning sqrt(1 - cosAlpha^2) is " + multiplier)
      val b = sign * (a * cosAlpha / multiplier)
      println("B meaning sign * (a * cosAlpha / multiplier) is " + b)
      
      newGear.direction(0) = rint(b).toInt
      newGear.direction(1) = a
    }
    */
    
    gear = newGear
  }
  
  
  
  
  
  def newPosition(gearChange: Char, directionChange: Int, carPosition: (Int, Int)): (Int, Int) = {
    
    ???
  }
  
}