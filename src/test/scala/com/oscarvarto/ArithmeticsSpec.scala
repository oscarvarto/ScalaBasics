package com.oscarvarto

import org.specs2.mutable.Specification
import org.specs2.ScalaCheck

/**
 * Created by oscarvarto on 3/29/14.
 */
class ArithmeticsSpec extends Specification with ScalaCheck {
  "Multiplicar por dos en Scala" in {
    import HelloVariables.multiplyByTwo
    multiplyByTwo(5) === 10
  }

  "Multiplicar por dos en Java" in {
    import HelloVariablesJava.multiplyByTwo
    multiplyByTwo(4) === 8
  }

  "La multiplicación y la suma están relacionadas" in prop {
    (a: Int) => a + a == HelloVariables.multiplyByTwo(a)
  }
}
