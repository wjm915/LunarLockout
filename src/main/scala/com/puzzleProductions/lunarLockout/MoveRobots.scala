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
        GameBoard.tileMap(dst_r)(dst_c).setEnabled(true);
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

}
