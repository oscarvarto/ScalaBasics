package com.oscarvarto

/**
 * Created by oscarvarto on 1/21/14.
 */
object HelloVariables extends App {
  val x = 8*5 + 2
  val y = multiplyByTwo(x)
  println(y)

  def multiplyByTwo(input: Int) = {
    input * 2
  }
}
