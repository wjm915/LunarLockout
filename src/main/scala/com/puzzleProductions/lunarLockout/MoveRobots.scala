package com.puzzleProductions.lunarLockout
import scala.collection.mutable
object MoveRobots {
  val tileStack: mutable.Stack[(Int,Int,Char)] = mutable.Stack()

  def pushTitleStack(row:Int, col:Int, id:Char): Unit = {
    tileStack.push((row, col, id))
    if (tileStack.length == 2) {
      //Turn the board off
      GameBoard.setTilesEnabled(false)

      var (dst_r, dst_c, dst_id) = tileStack.pop()
      var (src_r, src_c, src_id) = tileStack.pop()
      if (!GameBoard.memberId(src_id)) {
        GameBoard.tileMap(dst_r)(dst_c).setEnabled(true)
        GameBoard.tileMap(dst_r)(dst_c).setId(src_id)
      }
    }
  }

  def pushRobotStack(row:Int, col:Int, id:Char): Unit = {
    tileStack.clear()
    tileStack.push((row, col, id))
    //println(tileStack.toString())
    GameBoard.setTilesEnabled(true)
  }

  def makeMoves(_set: mutable.Stack[Array[Array[Char]]]): Unit = {
    printf("\n *** makeMoves  (Size: %d)\n", _set.length)
    val rset = _set.reverse
    var b0 = rset.pop()
    while(rset.nonEmpty) {
      val b1 = rset.pop()
      Thread.sleep(1500L)
      moveRobot(b0, b1)
      GameBoard.paint(GameBoard.getGraphics)
      b0 = b1
    }
  }

  def moveRobot(_b0: Array[Array[Char]], _b1: Array[Array[Char]]): Unit = {
    println("--moveRobot")
    //var items = mutable.Stack[(Int, Int)]()
    var r0: Int = 0
    var c0: Int = 0
    var toggle: Boolean = false

    //Find the difference
    for (r <- _b0.indices) {
      for (c <- _b0.indices){
        if(_b0(r)(c) != _b1(r)(c)) {
          if (!toggle) {
            r0 = r
            c0 = c
            printf("r0: %d, c0: %d\n", r0, c0)
           toggle = true
          } else {
            printf("r: %d, c: %d\n", r, c)
            val id0 = _b0(r0)(c0)
            GameBoard.tileMap(r0)(c0).setId(GameBoard.tileMap(r)(c).getId())
            GameBoard.tileMap(r0)(c0).validate()
            GameBoard.tileMap(r)(c).setId(id0)
            GameBoard.tileMap(r)(c).validate()
            toggle = false
          }
        }
      }
    }
  }
}
