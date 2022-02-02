package com.puzzleProductions.lunarLockout

import java.awt.event.{WindowEvent, WindowListener}
import java.awt.{BorderLayout, Color, Dimension, FlowLayout, GridLayout}
import javax.swing.{BoxLayout, JFrame, JPanel}

object MainEntry extends JFrame {

  def main(args: Array[String]): Unit = {
    System.out.println("Starting puzzle...")

    //Dimensions
    val buttonPanelWidth:Int = 500
    val buttonPanelHeight: Int = 100
    val boardWidth:Int = 500
    val boardHeight: Int = 500
    val totalHeight = buttonPanelHeight + boardHeight


    //========== GameBoard ==============
    val boardDimension =new Dimension(boardWidth,boardHeight)
    val board = GameBoard(boardDimension);

    //========== Buttons ==============
    val buttonPanel = Robots(new Dimension(buttonPanelWidth, buttonPanelHeight));

    this.setSize(new Dimension(boardWidth, totalHeight))
    this.addWindowListener(new MainWindowListener)
    this.setLayout(new BorderLayout())

    this.getContentPane.add(buttonPanel, BorderLayout.NORTH)
    this.getContentPane.add(board, BorderLayout.CENTER)
    this.setVisible(true);
  }

  class MainWindowListener extends WindowListener {
    override def windowOpened(windowEvent: WindowEvent): Unit = {}
    override def windowClosing(windowEvent: WindowEvent): Unit = {
      System.exit(0)
    }
    override def windowClosed(windowEvent: WindowEvent): Unit = {}
    override def windowIconified(windowEvent: WindowEvent): Unit = {}
    override def windowDeiconified(windowEvent: WindowEvent): Unit = {}
    override def windowActivated(windowEvent: WindowEvent): Unit = {}
    override def windowDeactivated(windowEvent: WindowEvent): Unit = {}
  }
}
