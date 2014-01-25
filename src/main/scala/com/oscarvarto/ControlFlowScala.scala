package com.oscarvarto

/**
 * Created by oscarvarto on 1/21/14.
 */
object ControlFlowScala extends App {
  for (i <- 1 to 5) {
    println(i)
  }

  (1 to 5) foreach println

  val xs = (1 to 5).toList
  val ys = xs map { _ * 2 }
}
