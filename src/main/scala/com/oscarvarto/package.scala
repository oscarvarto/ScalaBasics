package com

import scalaz.\/

/**
 * Created by oscarvarto on 3/29/14.
 */
package object oscarvarto {
  case class Woman(name: String)
  case class Man(name: String)
  type Citizen = Woman \/ Man
}
