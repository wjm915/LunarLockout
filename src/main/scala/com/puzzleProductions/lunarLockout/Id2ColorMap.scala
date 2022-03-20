package com.puzzleProductions.lunarLockout

import java.awt.Color

object Id2ColorMap extends Enumeration {
  type Fred = Value

  val X: Color = Color.red
  val A: Color = new Color(227, 95, 30) // orange
  val B: Color = Color.green
  val E: Color = new Color(0, 153, 255) // blue
  val D: Color = Color.yellow
  val C: Color = new Color(137,80,175)  // purple

  def getColor(_id: Char): Color = {
    _id match {
      case 'X' => Id2ColorMap.X
      case 'A' => Id2ColorMap.A
      case 'B' => Id2ColorMap.B
      case 'C' => Id2ColorMap.C
      case 'D' => Id2ColorMap.D
      case 'E' => Id2ColorMap.E
      case _ => Color.gray
    }
  }
}
