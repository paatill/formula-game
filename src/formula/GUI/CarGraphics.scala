package formula.GUI

object CarGraphics {
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
        val temporaryDownMiddle2 = temporaryDownMiddle.reverse
        temporaryDownMiddle2.drop(1)
        val downMiddle = temporaryDownMiddle2.toVector
        
        
        val temporaryMiddleRight = upMiddle.toArray.map(_.toArray)
        for {
          j <- temporaryMiddleRight.indices
          i <- temporaryMiddleRight(0).indices
        } {
          temporaryMiddleRight(j)(i) = upMiddle(i)(j)
        }
        val middleRight = temporaryMiddleRight.map(_.toVector).toVector
        
        val temporaryMiddleLeft = middleRight.map(_.reverse).map(_.toArray).toArray
        val asVector = temporaryMiddleLeft.toVector.map(_.toVector)
        for {
          j <- temporaryMiddleLeft.indices
          i <- temporaryMiddleLeft(0).indices
        } {
          temporaryMiddleLeft(j)((i + 1) % 16) = asVector(j)(i)
        }
        
        val middleLeft = temporaryMiddleLeft.map(_.toVector).toVector
        
        val upRight = Vector(
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'D', 'D', 'n', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'D', 'D', 'D', 'D', 'n', 'n', 'B', 'B', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'D', 'D', 'D', 'D', 'n', 'n', 'B', 'B', 'B', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'D', 'D', 'n', 'B', 'B', 'B', 'B', 'n', 'n'),
          Vector('n', 'n', 'n', 'D', 'D', 'n', 'n', 'n', 'n', 'n', 'B', 'B', 'B', 'n', 'n', 'n'),
          Vector('n', 'n', 'D', 'D', 'D', 'D', 'n', 'n', 'n', 'B', 'B', 'B', 'B', 'n', 'D', 'n'),
          Vector('n', 'D', 'D', 'D', 'D', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D'),
          Vector('n', 'n', 'D', 'D', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D', 'D'),
          Vector('n', 'n', 'n', 'n', 'B', 'B', 'B', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D', 'n'),
          Vector('n', 'n', 'n', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'n', 'n', 'n', 'D', 'n', 'n'),
          Vector('n', 'n', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'n', 'D', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'B', 'B', 'B', 'B', 'B', 'B', 'n', 'D', 'D', 'D', 'D', 'n', 'n', 'n'),
          Vector('n', 'n', 'B', 'B', 'B', 'B', 'B', 'n', 'D', 'D', 'D', 'D', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'B', 'B', 'B', 'B', 'n', 'n', 'D', 'D', 'D', 'n', 'n', 'n', 'n', 'n'),
          Vector('n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'D', 'n', 'n', 'n', 'n', 'n', 'n'),
        )
        
        val temporaryUpLeft = upRight.map(_.reverse)
        val temporaryUpLeft2 = temporaryUpLeft.map(_.toArray).toArray
        
        for {
          j <- temporaryUpLeft2.indices
          i <- temporaryUpLeft2(0).indices
        } {
          temporaryUpLeft2(j)((i + 1) % 16) = temporaryUpLeft(j)(i)
        }
        val upLeft = temporaryUpLeft2.map(_.toVector).toVector
        
        val downLeftTemporary = upLeft.reverse
        val downLeftTemporary2 = downLeftTemporary.reverse.map(_.toArray).toArray
        
        for {
          j <- downLeftTemporary2.indices
          i <- downLeftTemporary2(0).indices
        } {
          downLeftTemporary2((j + 1) % 16)(i) = downLeftTemporary(j)(i)
        }
        val downLeft = downLeftTemporary2.map(_.toVector).toVector
        
        
        val downRightTemporary = upRight.reverse
        val downRightTemporary2 = downRightTemporary.reverse.map(_.toArray).toArray
        
        for {
          j <- downRightTemporary2.indices
          i <- downRightTemporary2(0).indices
        } {
          downRightTemporary2((j + 1) % 16)(i) = downRightTemporary(j)(i)
        }
        val downRight = downRightTemporary2.map(_.toVector).toVector
        
        
        def apply(direction: String): Vector[Vector[Char]] = {
          
          direction match {
            case "UPRIGHT"     => upRight
            case "UPMIDDLE"    => upMiddle
            case "UPLEFT"      => upLeft
            case "MIDDLERIGHT" => middleRight
            case "MIDDLELEFT"  => middleLeft
            case "DOWNRIGHT"   => downRight
            case "DOWNMIDDLE"  => downMiddle
            case "DOWNLEFT"    => downLeft
          }
          
        }
}