package tests
import formulaPlay._
import org.scalatest._

class CarTest extends FlatSpec {
  
  def arrayPrint(array: Array[Array[Char]]) = {
    for (line <- array) {
      var string = ""
        line.foreach( string += _ )
        println(string)
      }
  }
  
  "Position" should "be correct after driving" in {
    val map = Array(
        Array('T', 'T', 'T', 'T', 'T', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'A', 'O', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'B', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'T', 'T', 'T', 'T', 'T'))
    
    val car = new Car(Driver("MATTI"), (3, 2), 'A', Vector())
    
    car.drive('=', 0, map)
    assert(car.position === (2, 2))
    
    car.drive('=', 1, map)
    assert(car.position === (1, 1))
    
  }
  
  
  "seekPossibilities" should "return correct possibilities" in {
    val map = Array(
        Array('T', 'T', 'T', 'T', 'T', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', 'O', 'O', 'O', 'A', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'T', 'T', 'T', 'T', 'T'))
    
    val car = new Car(Driver("MATTI"), (4, 3), 'A', Vector())
    
    val possibilities = car.seekPossibilities(map)
    arrayPrint(possibilities)
    
    println("\ndirection(0): " + car.gearManager.gear.direction(0) + "\n")
    println("\ndirection(1): " + car.gearManager.gear.direction(1) + "\n")
    println("carPos: " + car.position)
    car.drive('=', 0, map)
    println("carPos: " + car.position)
    println("\ndirection(0): " + car.gearManager.gear.direction(0) + "\n")
    println("\ndirection(1): " + car.gearManager.gear.direction(1) + "\n")
    
    
    
    assert(car.position === (2, 3))/*Array(
        Array('T', 'T', 'T', 'T', 'T', 'T'), 
        Array('T', 'O', 'O', 'O', 'O', 'T'), 
        Array('T', '+', '=', 'O', 'O', 'T'), 
        Array('T', '+', '=', 'A', 'O', 'T'), 
        Array('T', '+', '=', 'O', 'O', 'T'),
        Array('T', 'O', 'O', 'O', 'O', 'T'),
        Array('T', 'T', 'T', 'T', 'T', 'T')))*/
    
  }
  
  
  
}