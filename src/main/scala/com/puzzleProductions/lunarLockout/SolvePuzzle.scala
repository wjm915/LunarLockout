package com.puzzleProductions.lunarLockout


import java.awt.event.{ActionEvent, ActionListener}
import scala.collection.mutable

object SolvePuzzle {

  def showBoard(board: Array[Array[Char]]): Unit = {
    println()
    for (r <- board.indices) {
      for (c <- board.indices) {
        printf(" %c", board(r)(c))
      }
      println()
    }
  }

  def showCurrentSet(set: mutable.Stack[Array[Array[Char]]]): Unit = {
    printf("\n *** CurrentSet  (Size: %d)", set.length)
    set.reverse.foreach(x => showBoard(x))
  }

  def sort(_moves: mutable.Stack[(Int, Int, Char, Int, Int)]): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    var swaps: Int = 1
    var i = 0
    while (swaps > 0) {
      swaps = 0
      for (j <- 1 until _moves.length) {
        i = j - 1
        val (_, _, src_id1, _, _) = _moves(i)
        val (_, _, src_id2, _, _) = _moves(j)

        if(src_id1 != 'X' && src_id2 == 'X') {
          val temp = _moves(i)
          _moves(i) = _moves(j)
          _moves(j) = temp
          swaps += 1
        }
      }
    }
    _moves
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
        }
      }
    }
    //sort(ans)
    ans
  }

  def lookLeft(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var c: Int = _col - 1

    while (c >= 0 && _board(_row)(c) == '_') c -=1

    if(c < 0 || c == _col -1)
      return ans
    else
      ans.push((_row, _col, _board(_row)(_col), _row, c+1))

    ans
  }

  def lookRight(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var c: Int = _col + 1

    while (c < _board.length && _board(_row)(c) == '_') c += 1

    if(c >= _board.length || c == _col + 1)
      return ans
    else
      ans.push((_row, _col, _board(_row)(_col), _row, c-1))

    ans
  }

  def lookUp(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var r: Int = _row - 1

    while (r >= 0 && _board(r)(_col) == '_') r -= 1

    if(r < 0 || r == _row - 1)
      return ans
    else
      ans.push((_row, _col, _board(_row)(_col), r+1 , _col))
    ans
  }

  def lookDown(_board: Array[Array[Char]], _row: Int, _col: Int): mutable.Stack[(Int, Int, Char, Int, Int)] = {
    val ans = mutable.Stack[(Int, Int, Char, Int, Int)]()
    var r: Int = _row + 1

    while (r < +_board.length && _board(r)(_col) == '_') r += 1

    if(r >= _board.length || r == _row + 1)
      return ans
    else
      ans.push((_row, _col, _board(_row)(_col), r-1 , _col))
    ans
  }

  def copyGameMap(_board: Array[Array[Char]]): Array[Array[Char]] = {
    _board.map(_.map(x => x))
  }

  def applyMove(src_r: Int, src_c: Int, src_id: Char, dst_r: Int, dst_c: Int, _board: Array[Array[Char]]): Array[Array[Char]] = {
    val newBoard = copyGameMap(_board)
    val temp_id = newBoard(dst_r)(dst_c)
    newBoard(dst_r)(dst_c) = newBoard(src_r)(src_c)
    newBoard(src_r)(src_c) = temp_id
    newBoard
  }

  def member(_board: Array[Array[Char]], setOfBoards: mutable.Stack[Array[Array[Char]]]): Boolean = {
    for (i <- setOfBoards.indices) {
      if (setOfBoards(i).sameElements(_board)) {
        return true
      }
    }
    false
  }

  def solve(level: Int, currentGameMap: Array[Array[Char]], currentSet: mutable.Stack[Array[Array[Char]]]): Boolean = {
    println("*** Solve()  Level: " + level.toString + " Set size: " + currentSet.length)
    if (level <= 0) {
      return false
    }

    if (currentGameMap(2)(2) == 'X') {
      println("\n\n **** Solved ****")
      showCurrentSet(currentSet)
      return true
    }

    val moves = findMoves(currentGameMap)

    while(moves.nonEmpty) {
      // Get a move
      val (src_r, src_c, src_id, dst_r, dst_c) = moves.pop()

      // Apply the move to the current game
      val newGameMap = applyMove(src_r, src_c, src_id, dst_r, dst_c, currentGameMap)

      // If a new game state has been generated, then make a recursive call to solve()
      if (!member(newGameMap, currentSet)) {
        if (solve(level-1, newGameMap, currentSet.push(newGameMap))) {
          return true
        }
        currentSet.pop()
      } else {
        println("*********** MEMBER *********************")
      }
    }
    false
  }

  def changeBoard(_board: Array[Array[Char]], r: Int, c: Int, id: Char): Array[Array[Char]] = {
    val newBoard = copyGameMap(_board)
    newBoard(r)(c) = id
    newBoard
  }

  //=========== Inner Class =========
  class SolveButtonActionListener() extends ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      println("Solver!!")
      val gameMap: Array[Array[Char]] = GameBoard.tileMap.map(_.map(x => x.getId()))
      val setOfMaps = mutable.Stack[Array[Array[Char]]]()
      solve(25, gameMap, setOfMaps.push(gameMap))
    }
  }
}
