package com.oscarvarto

/**
 * Created by oscarvarto on 3/29/14.
 */
trait SNamed {
  def name: String = getClass.getName + "_" + hashCode()
}

class SPerson extends SNamed

object TraitLesson {
  def main (args: Array[String]) {
    val miguel = new SPerson()
    println(miguel.name)
  }
}
