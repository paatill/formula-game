package exceptions

case class FileException(description: String, found: String) extends java.lang.Exception(description) {
  
}