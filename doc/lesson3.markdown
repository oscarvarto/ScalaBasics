XHTML Header: <script type="text/javascript">hljs.configure({tabReplace: '  '}); hljs.initHighlightingOnLoad();</script>

## Secciones del libro "Scala for the Impatient" que hay que estudiar

Descarga una parte del libro de [aquí](http://typesafe.com/resources/book/scala-for-the-impatient). Tal vez puedascomprar el eBook, o inclusive una versión impresa. El libro vale la pena (esa es mi opinión).

### Section 1.6: The apply method

En Scala es común utilizar una sintaxis que parece una invocación a una función. Intenta lo siguiente en la consola de sbt (revisa lo que hace la tarea `consoleQuick` en la [documentación oficial de sbt](http://www.scala-sbt.org/0.13.1/docs/Howto/scala.html#consolequick)):

<pre>
> consoleQuick
[info] Starting scala interpreter...
[info]
Welcome to Scala version 2.11.0-RC3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0).
Type in expressions to have them evaluated.
Type :help for more information.

scala> "Hello"(4)
</pre>

En otras palabras, `"Hello"(4)` es una abreviatura para `"Hello".apply(4)`.

Busca el scaladoc de `Map#apply`. Asegúrate de ver en el scaladoc del **companion object** (observa que en [Map#apply scaladoc][] se ve una **o** en lugar de una **t** o una **c** en la parte superior):

![Map#apply scaladoc][]

Utilicemos este método para crear un mapeo entre países y sus áreas en km^2:
```scala
val capitales = Map("Mexico" -> 1964375, "Estados Unidos" -> 9629091, "United Kingdom" -> 242900)
```

Prueba introducir esto en la REPL de scala para **ver el tipo** de la variable capitales:
<pre>
scala> :t areas
scala.collection.immutable.Map[String,Int]
</pre>

La variable `capitales` es un mapa con llaves (*keys*) de tipo `String` y valores (*values*) de tipo Int.

[Map#apply scaladoc]: figures/map-apply-scaladoc.png

### Section 10.5: Traits with Concrete Implementations

Java 8 introduce los `DefaultMethods`. Supón que creas la interfaz `Named` junto con la clase `Person`, como se indica a continuación:
```java
package com.oscarvarto;

interface Named {
    default String getName() {
        return getClass().getName() + "_" + hashCode();
    }
}

public class Person implements Named {}
```

Nota el uso de la palabra reservada `default`. Java 8 permite que los métodos de una interfaz tengan implementaciones concretas (dichos métodos son los llamados **default methods**).

```java
package com.oscarvarto;

public class DefaultMethods {
    public static void main(String[] args) {
        Person oscar = new Person();
        System.out.println(oscar.getName());
    }
}
```

El código correspondiente en Scala es:
```scala
package com.oscarvarto

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
```

### Chapter 14: Pattern Matching and Case Classes

A continuación verá dos formas distintas (una que funciona con Java 7+ y otra con Scala) de convertir un día de la semana en Inglés a Francés. Primero en Java (se puede usar Strings en `switch` statements desde Java 7):

```java
package com.oscarvarto;

public class StringsInSwitch {
    public static String dayOfWeekInFrench(String dayOfWeek) {
        switch (dayOfWeek) {
            case "Sunday": return "Dimanche";
            case "Monday": return "Lundi";
            case "Tuesday": return "Mardi";
            case "Wednesday": return "Mercredi";
            case "Thursday": return "Jeudi";
            case "Friday": return "Vendredi";
            case "Saturday": return "Samedi";
            default: return "Error: '"+ dayOfWeek +"' is not a day of the week";
        }
    }
}
```

Ahora en Scala utilizando *pattern matching*:

```scala
package com.oscarvarto

object PatternMatching {
   def dayToFrench(aDay: String): String = aDay match {
     case "Sunday" => "Dimanche"
     case "Monday" =>  "Lundi"
     case "Tuesday" => "Mardi"
     case "Wednesday" => "Mercredi"
     case "Thursday" => "Jeudi"
     case "Friday" => "Vendredi"
     case "Saturday" => "Samedi"
     case _ => "Error: '" + aDay + "' is not a day of the week"
   }
}
```

**Nota: Para entender los siguientes ejemplos, primero estudie las Secciones 7.5 y 14.9**

Suponga que escribimos las siguientes definiciones en un *package object* para que estén visibles en todo archivo dentro del paquete `oscarvarto`.

```scala
package com

import scalaz.\/

package object oscarvarto {
  case class Woman(name: String)
  case class Man(name: String)
  type Citizen = Woman \/ Man
}
```

Crear objetos que sean instancias de case classes es muy simple y no requiere del uso del operador `new`. Por ejemplo:
```scala
val bety = Woman("Bety") // equivalente a Woman.apply("Bety")
val oscar = Man("Oscar") // no es necesario escribir new Man("Oscar")
```

La línea `type Citizen = Woman \/ Man` dice que un ciudadano es o bien una mujer o un hombre. Con el uso de la palabra reservada `type` se está estableciendo un *type alias* para abreviar la escritura del tipo más largo `Woman \/ Man`. Note la semejanza entre `\/` y el operador de disyunción que es ampliamente conocido en matemáticas \\(\lor\\).

El primer ejemplo de la siguiente especificación es el más simple. Estúdialo primero.

```scala
package com.oscarvarto

import org.specs2.mutable.Specification
import scalaz.\/
import PatternMatching._

class PatternMatchingSpec extends Specification {
  "Days of week from English to French" in {
    val days = List("Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday")
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
```

El segundo ejemplo
```scala
"Greeting people" in {
  val bety: Citizen = \/.left(Woman("Beatriz"))
  val oscar: Citizen = \/.right(Man("Oscar"))

  val people = List(bety, oscar)
  (people map greet1) === (people map greet2)
}
```

crea dos objectos de tipo `Citizen` de manera explícita. Busque el scaladoc para `\/#left` y `\/.right#right` en el scaladoc de scalaz-core. Vea la Figura [\/#left scaladoc][].

![\/#left scaladoc][]

Modifica las preferencias de IntelliJ IDEA para que puedas ver el tipo de las expresiones al dejar inmóvil el ratón sobre las variables. Vaya a `Preferences > IDE Settings > Scala` y palomee la opción **Show type info on mouse motion with delay`. Vea la Figura [Preferences Type Information][].

![Preferences Type Information][]

Ahora pon el cursor encima de la declaración de la constante `people`
```scala
val people = List(bety, oscar)
```

y deberás ver que su tipo es `List[oscarvarto.Citizen]`.

Estudiemos ahora el tercer ejemplo, que requiere de una comprensión básica del concepto de conversiones implícitas (**lee la sección 21.1 del libro**, o también puedes leer algunos *blog posts* en internet, como [este](http://tomjefferys.blogspot.mx/2011/11/implicit-conversions-in-scala.html)). Pero, posiblemente sea suficiente con poner atención a los tipos en nuestro tercer ejemplo...

```scala
"Greeting people using implicit conversions" in {
  import Conversions._
  val bety = Woman("Beatriz")
  val oscar = Man("Oscar")

  val people: List[Citizen] = List(bety, oscar)
  (people map greet1) === (people map greet2)
}
```

Los tipos `Woman` y `Man` son distintos a `Citizen`. ¿Cómo obtuvimos una lista de `Citizen`s a partir de instancias de `Woman` y `Man`? La respuesta está en las conversiones implicitas dentro del objeto `Conversions`:
```scala
object Conversions {
  implicit def womanToCitizen(lady: Woman): Citizen = \/.left(lady)
  implicit def manToCitizen(guy: Man): Citizen = \/.right(guy)
}
```

Observa que nuestro tercer ejemplo hace que estas conversiones implícitas estén disponibles en el ámbito del método (*in scope*) gracias al import
```scala
import Conversions._
```

Así pues, el compilador espera que todos los elementos de esta lista sean de tipo `Citizen`.

* Si encuentra una instancia de `Woman` entonces busca una conversión implícita (que esté *in scope*) de `Woman` a `Citizen`. Este es el método **implícito** `Conversions#womanToCitizen`.
* Si encuentra una instancia de `Man` entonces busca una conversión implícita (que esté *in scope*) de `Man` a `Citizen`. Este es el método **implícito** `Conversions#manToCitizen`.

Hay que hacer notar que es necesario indicar al compilador que deseamos habilitar esta característica del lenguaje. Esto se puede realizar de varias maneras. Una de ellas es habilitarlas globalmente (es decir, para todos los archivos de código fuente de nuestro proyecto), utilizando un switch del compilador `scalac` en nuestro *build file*: `build.sbt`:

```sbt
scalacOptions ++= Seq(
  "-language:implicitConversions"
)
```

Si se desea habilitar todas las características disponibles, simplemente puede escribir:

```sbt
scalacOptions ++= Seq(
  "-language:_"
)
```

Una segunda manera de habilitar características del lenguaje es utilizando cláusulas `import` explícitas en los lugares en donde así se desee. Vea la Figura [Language Features][].

![Language Features][]


[\/#left scaladoc]: figures/scalaz-either-left-scaladoc.png
[Preferences Type Information]: figures/preferences-type-information.png
[Language Features]: figures/scala-language-features.png
