package com.puzzleProductions.lunarLockout

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Dimension, FlowLayout}
import javax.swing.{JButton, JPanel}

object Robots extends JPanel {
  val robotsMap: Array[RobotButton] = Array.ofDim[RobotButton](6)

  def apply(_dimension: Dimension): JPanel = {
    this.setBackground(Color.blue)
    this.setPreferredSize(_dimension)
    this.setLayout(new FlowLayout())

    //Added Robotbuttons
    this.robotsMap(0) = new RobotButton('A', Id2ColorMap.A)
    this.add(robotsMap(0))

    this.robotsMap(1) = new RobotButton('B', Id2ColorMap.B)
    this.add(robotsMap(1))

    this.robotsMap(2) = new RobotButton('C', Id2ColorMap.C)
    this.add(robotsMap(2))

    this.robotsMap(3) = new RobotButton('D', Id2ColorMap.D)
    this.add(robotsMap(3))

    this.robotsMap(4) = new RobotButton('E', Id2ColorMap.E)
    this.add(robotsMap(4))

    this.add(new RobotButton('X', Id2ColorMap.X))

    val solveButton = new JButton("Solve")
    solveButton.addActionListener(new SolvePuzzle.SolveButtonActionListener())
    this.add(solveButton)

    val resetButton = new JButton("Reset")
    resetButton.addActionListener(new ResetButtonListener())
    this.add(resetButton)

    this
  }

  def setRobotsEnabled(_flag: Boolean): Unit = {
      for (i <- robotsMap.indices) {
        robotsMap(i).setEnabled(_flag)
      }
  }

  //====== Inner Classes ========================================
  class RobotButton(_id: Char, _color: Color) extends TileTraits {
    private var id: Char = _id

    this.setBackground(_color)
    this.addActionListener(new RobotButtonListener(this))
    this.setPreferredSize(new Dimension(50,50))

    def setId(_id: Char): Unit = {
      this.id = _id
    }

    def getId: Char = {
      this.id
    }

  }

  class RobotButtonListener(_parent: RobotButton) extends ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      //println("Robot button: " + _parent.getId().toString)
      MoveRobots.pushRobotStack(-1, -1, _parent.getId())
    }
  }

  class ResetButtonListener() extends ActionListener {
    override def actionPerformed(actionEvent: ActionEvent): Unit = {
      for (r <- GameBoard.tileMap.indices) {
        for (c <- GameBoard.tileMap.indices) {
          GameBoard.tileMap(r)(c).setDefaults()
        }
      }
    }
  }

}
