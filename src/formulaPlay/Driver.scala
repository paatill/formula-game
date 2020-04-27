

package formulaPlay

case class Driver(val info: String) {
  val name = info.drop(2).take(info.take(2).toInt)
}