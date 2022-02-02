package com.puzzleProductions.lunarLockout


import java.awt.event.{ActionEvent, ActionListener}
import scala.collection.mutable
import scala.math.abs

object SolvePuzzle {

  def showBoard(board: Array[Array[Char]]): Unit = {
    for (r <- board.indices) {
      println()
      for (c <- board.indices) {
        printf(" %c", board(r)(c))
      }
    }
    println()
  }

  def findMoves(board: Array[Array[Char]]): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    var ans = mutable.Stack[(Int, Int, Char, Int, Int)]()

    for (r <- board.indices) {
      for (c <- board.indices) {
        if (board(r)(c) != '_') {
          ans = ans.concat(lookLeft(board, r, c))
            .concat(lookRight(board, r, c))
            .concat(lookUp(board, r, c))
            .concat(lookDown(board, r, c))
          println("Ans: " + ans.toString())
        }
      }
    }
    ans
  }

  def lookLeft(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    //printf("Looking left: row, %d, col: %d\n", _row, _col)
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var c: Int = _col - 1

    //printf("  c: %d\n", c)
    while (c >= 0) {
      //printf("  LookingLeft: %d, %d\n", _row, c);
      if (_board(_row)(c) != '_') {
        //printf("   lookLeft: found: %c\n", _board(_row)(c))
        if (abs(c - _col) > 1) {
          ans.push((_row, _col, _board(_row)(_col), _row, c + 1))
          //println("  Left: Found move: " + ans.toString())
          return ans
        }
      }
      c -= 1
    }
    ans
  }

  def lookRight(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    //printf("Looking right: row, %d, col: %d\n", _row, _col)
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var c: Int = _col + 1

    //printf("  c: %d\n", c)
    while (c < _board.length) {
      //printf("  LookingRight: %d, %d\n", _row, c)
      if (_board(_row)(c) != '_') {
        //printf("   lookRight: found: %c\n", _board(_row)(c))
        if (abs(c - _col) > 1) {
          ans.push((_row, _col, _board(_row)(_col), _row, c - 1))
          //println("  Found move: " + ans.toString())
          return ans
        }
      }
      c += 1
    }
    ans
  }

  def lookUp(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    printf("Looking up: row, %d, col: %d\n", _row, _col)
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var r: Int = _row - 1

    printf("  r: %d\n", r)
    while (r >= 0) {
      printf("  LookingUp: %d, %d\n", r, _col)
      if (_board(r)(_col) != '_') {
        printf("   lookUp: found: %c\n", _board(r)(_col))
        if (abs(r - _row) > 1) {
          ans.push((_row, _col, _board(_row)(_col), r + 1, _col))
          println("  Found move: " + ans.toString())
          return ans
        }
      }
      r -= 1
    }
    ans
  }

  def lookDown(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    printf("Looking down: row, %d, col: %d\n", _row, _col)
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var r: Int = _row + 1

    printf("  r: %d\n", r)
    while (r < _board.length) {
      printf("  LookingDown: %d, %d\n", r, _col)
      if (_board(r)(_col) != '_') {
        printf("   lookDown: found: %c\n", _board(r)(_col))
        if (abs(r - _row) > 1) {
          ans.push((_row, _col, _board(_row)(_col), r - 1, _col))
          println("  Found move: " + ans.toString())
          return ans
        }
      }
      r += 1
    }
    ans
  }

  //=========== Inner Class =========
  class SolveButtonActionListener() extends ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      println("Solver!!")
      val gameMap: Array[Array[Char]] = GameBoard.tileMap.map(_.map(x => x.getId()))
      println("gameMap: ")
      showBoard(gameMap)
      findMoves(gameMap)
    }
  }
}
