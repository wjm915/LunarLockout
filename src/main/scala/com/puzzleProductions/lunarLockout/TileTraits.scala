package com.puzzleProductions.lunarLockout

import java.awt.Color
import javax.swing.JButton

trait TileTraits extends JButton {
  def getId(): Char
  def setId(_id: Char): Unit
}
