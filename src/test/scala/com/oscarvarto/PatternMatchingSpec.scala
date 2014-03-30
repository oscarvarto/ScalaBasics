package com.oscarvarto

import org.specs2.mutable.Specification
import scalaz.\/
import PatternMatching._

/**
 * Created by oscarvarto on 3/29/14.
 */
class PatternMatchingSpec extends Specification {
  "Days of week from English to French" in {
    val days = List("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    import StringsInSwitch.dayOfWeekInFrench
    import PatternMatching.dayToFrench

    val jDays = days map dayOfWeekInFrench
    val sDays = days map dayToFrench

    jDays === sDays
  }

  "Greeting people" in {
    val bety: Citizen = \/.left(Woman("Beatriz"))
    val oscar: Citizen = \/.right(Man("Oscar"))

    val people = List(bety, oscar)
    (people map greet1) === (people map greet2)
  }

  "Greeting people using implicit conversions" in {
    import Conversions._
    val bety = Woman("Beatriz")
    val oscar = Man("Oscar")

    val people: List[Citizen] = List(bety, oscar)
    (people map greet1) === (people map greet2)
  }
}
