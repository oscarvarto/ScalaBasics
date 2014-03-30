package com.oscarvarto

import scalaz.\/

/**
 * Created by oscarvarto on 3/29/14.
 */
object Conversions {
  implicit def womanToCitizen(lady: Woman): Citizen = \/.left(lady)
  implicit def manToCitizen(guy: Man): Citizen = \/.right(guy)
}

object PatternMatching {
  def dayToFrench(aDay: String): String = aDay match {
    case "Sunday" => "Dimanche"
    case "Monday" => "Lundi"
    case "Tuesday" => "Mardi"
    case "Wednesday" => "Mercredi"
    case "Thursday" => "Jeudi"
    case "Friday" => "Vendredi"
    case "Saturday" => "Samedi"
    case _ => "Error: '" + aDay + "' is not a day of the week"
  }

  import scalaz.{-\/, \/-}
  def greet1(person: Citizen): String = person match {
    case -\/(girl) => s"Hi Ms. $girl"
    case \/-(guy) => s"What's up $guy"
  }

  def greet2(person: Citizen): String = person.fold(girl => s"Hi Ms. $girl", guy => s"What's up $guy")
}
