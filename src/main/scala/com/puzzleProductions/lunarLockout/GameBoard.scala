package com.puzzleProductions.lunarLockout

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Dimension, GridLayout, Font}
import javax.swing.JPanel


object GameBoard extends JPanel {
  val rows: Int = 5
  val cols: Int = 5
  val gap: Int = 2
  val tileMap: Array[Array[Tile]] = Array.ofDim[Tile](5,5)

  def apply(_dimension: Dimension): JPanel = {
    //Setup the physical board
    this.setPreferredSize(_dimension)
    this.setBackground (Color.green)
    this.setLayout(new GridLayout(5,5, gap, gap))
    addTiles()
    this
  }

  def addTiles(): Unit = {
    for (r <- 0 until this.rows) {
      for (c <- 0 until this.cols) {
        tileMap(r)(c) = new Tile(r, c)
        this.add(tileMap(r)(c))
      }
    }
  }

  def memberId(_id: Char): Boolean = {
    for (r <- this.tileMap.indices) {
      for (c <- this.tileMap.indices) {
        if (_id == tileMap(r)(c).getId()) {
          return true
        }
      }
    }
    false
  }

  def setTilesEnabled(_flag: Boolean): Unit = {
    for (r <- tileMap.indices) {
      for (c <- tileMap.indices) {
        if(tileMap(r)(c).getId() == '_')  tileMap(r)(c).setEnabled(_flag)
      }
    }
  }

  //=========== Inner classes =============================
  class Tile(_row: Int, _col: Int) extends TileTraits {
    val row: Int = _row
    val col: Int = _col
    private var id: Char = '_'
    private var color: Color = if (row == col && col == 2) Color.white else Color.gray

    this.setPreferredSize(new Dimension(48,48))
    this.setBackground(color)
    this.addActionListener(new TileActionListener(this))
    this.setEnabled(false)

    // Methods
    def setDefaults(): Unit = {
      this.setId('_')
      this.color = if (row == col && col == 2) Color.white else Color.gray
      this.setBackground(color)
      this.setEnabled(false)
    }

    override def setId(_id:Char): Unit = {
      this.id = _id
      this. setFont(new Font("Serif", Font.BOLD, 16))
      this.setText("")
      if (this.id != '_') {
        this.setText(_id.toString)
      }
      this.color = Id2ColorMap.getColor(_id)
      this.setBackground(this.color)
    }

    override def getId(): Char = {
      this.id
    }
  }

  class TileActionListener(_parent: Tile) extends ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      _parent.setDefaults()
      //println(actionEvent.getActionCommand)
      MoveRobots.pushTitleStack(_parent.row, _parent.col, _parent.getId())
    }
  }

}
