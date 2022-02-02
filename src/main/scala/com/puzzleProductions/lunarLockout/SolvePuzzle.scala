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
          //println("Ans: " + ans.toString())
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
    //printf("Looking up: row, %d, col: %d\n", _row, _col)
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var r: Int = _row - 1

    //printf("  r: %d\n", r)
    while (r >= 0) {
      //printf("  LookingUp: %d, %d\n", r, _col)
      if (_board(r)(_col) != '_') {
        //printf("   lookUp: found: %c\n", _board(r)(_col))
        if (abs(r - _row) > 1) {
          ans.push((_row, _col, _board(_row)(_col), r + 1, _col))
          //println("  Found move: " + ans.toString())
          return ans
        }
      }
      r -= 1
    }
    ans
  }

  def lookDown(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    //printf("Looking down: row, %d, col: %d\n", _row, _col)
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var r: Int = _row + 1

    //printf("  r: %d\n", r)
    while (r < _board.length) {
      //printf("  LookingDown: %d, %d\n", r, _col)
      if (_board(r)(_col) != '_') {
        //printf("   lookDown: found: %c\n", _board(r)(_col))
        if (abs(r - _row) > 1) {
          ans.push((_row, _col, _board(_row)(_col), r - 1, _col))
          //println("  Found move: " + ans.toString())
          return ans
        }
      }
      r += 1
    }
    ans
  }

  def applyMove(src_r: Int, src_c: Int, src_id: Char, dst_r: Int, dst_c: Int, _board: Array[Array[Char]]): Array[Array[Char]] = {
    var newBoard = _board.clone()
    var temp_id = newBoard(dst_r)(dst_c)
    newBoard(dst_r)(dst_c) = newBoard(src_r)(src_c)
    newBoard(src_r)(src_c) = temp_id
    newBoard
  }

  def member(_board: Array[Array[Char]], setOfBoards: mutable.Stack[Array[Array[Char]]]): Boolean = {
    for (i <- setOfBoards.indices) {
      if (setOfBoards(i).equals(_board)) return true
    }
    false
  }

  def solve(currentGameMap: Array[Array[Char]], currentSet: mutable.Stack[Array[Array[Char]]]): Boolean = {
    println("gameMap: ")
    showBoard(currentGameMap)

    if (currentGameMap(2)(2) == 'X') {
      println("\n **** Solved ****")
      return true
    }

    val moves = findMoves(currentGameMap)
    println("Moves: " + moves.toString())

    while(moves.length > 0) {
      // Get a move
      var (src_r, src_c, src_id, dst_r, dst_c) = moves.pop()
      // Apply the move to the current game
      var newGameMap = applyMove(src_r, src_c, src_id, dst_r, dst_c, currentGameMap)
      // Show the modified game
      showBoard(newGameMap)

      // If a new game state has been generated, then make a recursive call to solve()
      if (!member(newGameMap, currentSet)) {
        if (solve(newGameMap, currentSet.push(newGameMap))) return true
        currentSet.pop()
      }
    }

    false
  }

  //=========== Inner Class =========
  class SolveButtonActionListener() extends ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      println("Solver!!")
      val gameMap: Array[Array[Char]] = GameBoard.tileMap.map(_.map(x => x.getId()))
      var setOfMaps = mutable.Stack[Array[Array[Char]]]()
      solve(gameMap, setOfMaps.push(gameMap))
    }
  }
}
