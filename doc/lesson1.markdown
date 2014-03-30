XHTML Header: <script type="text/javascript">hljs.configure({tabReplace: '  '}); hljs.initHighlightingOnLoad();</script>

## Primeros pasos

### Tu ambiente de desarrollo para este curso

Se recomienda que instales lo siguiente:

* La última versión de IntelliJ IDEA EAP de [aquí](http://confluence.jetbrains.com/display/IDEADEV/IDEA+13+EAP)
* Java SDK 8.
* sbt, siguiendo [estas instrucciones](http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html)
* git para el sistema operativo que estés usando.

### `HelloWorld` en Java

```java
package com.oscarvarto;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

### `HelloWorld` en Scala

```scala
package com.oscarvarto

object HelloWorld extends App {
  println("Hello World")
}
```

### Ejecutando el código de ejemplo con sbt

Una vez que puedas usar sbt desde la línea de comandos, inicia sbt, compila el código de ejemplo y ejecuta
`com.oscarvarto.HelloScala`:

<pre>
$ pwd
/Users/oscarvarto/ScalaBasics
oscar-imac:ScalaBasics oscarvarto$ ls
README.md	build.sbt	doc		project		src		target
oscar-imac:ScalaBasics oscarvarto$ sbt
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=256M; support was removed in 8.0
[info] Loading project definition from /Users/oscarvarto/ScalaBasics/project
[info] Set current project to scalaBasics (in build file:/Users/oscarvarto/ScalaBasics/)
> compile
[info] Compiling 5 Scala sources and 4 Java sources to /Users/oscarvarto/ScalaBasics/target/scala-2.11.0-RC3/classes...
[success] Total time: 6 s, completed Mar 29, 2014 10:45:01 AM
> run

Multiple main classes detected, select one to run:

 [1] com.oscarvarto.Arithmetics
 [2] com.oscarvarto.ControlFlowScala
 [3] com.oscarvarto.HelloJava
 [4] com.oscarvarto.ControlFlowJava
 [5] com.oscarvarto.ArithmeticsJava
 [6] com.oscarvarto.HelloVariablesJava
 [7] com.oscarvarto.fp.OptionExperiment
 [8] com.oscarvarto.HelloScala
 [9] com.oscarvarto.HelloVariables

Enter number: 8

[info] Running com.oscarvarto.HelloScala
Hello Scala
[success] Total time: 29 s, completed Mar 29, 2014 10:45:36 AM
</pre>

Por supuesto, también puedes utilizar IntelliJ IDEA (o el IDE de tu preferencia para ejecutar código de Scala).

### Utilizando IntelliJ IDEA (o Eclipse) para editar tu código

Desde la consola de sbt, genera un proyecto para IntelliJ IDEA con:

<pre>
> gen-idea
</pre>

La primera vez que hagas esto, tal vez tengas que esperar un poco de tiempo mientras se descargan varias dependencias.

sbt utiliza **plugins** para agregar funcionalidad a tu proyecto. Inspeccionemos el contenido de `project > plugins.sbt`:

```sbt
resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.7.0-SNAPSHOT")
```

Es gracias al plugin `sbt-idea` que puedes invocar la tarea (*task*) `gen-idea` y generar un proyecto para IntelliJ IDEA. Si prefieres utilizar Eclipse, existe un [plugin correspondiente](https://github.com/typesafehub/sbteclipse) (Nota: en estas notas se utiliza IntelliJ IDEA).

![HelloScala][]

Pon atención en la estructura de directorios que se está utilizando. Tu proyecto puede mezclar código fuente escrito en Java y en Scala. Además, el código de *producción* está en el directorio `src > main` y el código dedicado a *testing* está en el directorio `src > test`.

[HelloScala]: figures/HelloScalaIntelliJ.png

### Variables en Java y Scala

El siguiente ejemplo utiliza un estilo de programación tradicional, que probablemente sea adecuado para Java < 8.

```java
package com.oscarvarto;

public class HelloVariablesJava {
    public static void main(String[] args) {
        final int x = 8*5 + 2;
        final int y = multiplyByTwo(x);
        System.out.println(y);
    }

    public static int multiplyByTwo(final int input) {
        return input * 2;
    }
}
```

Nota el uso de la palabra reservada `final`. En Scala, puedes utilizar `val`s en lugar de `var`s para evitar que alguna variable sea modificada. La versión correspondiente en Scala se ve así:

```scala
package com.oscarvarto

object HelloVariables extends App {
  val x = 8*5 + 2
  val y = multiplyByTwo(x)
  println(y)

  def multiplyByTwo(input: Int) = {
    input * 2
  }
}
```

### Testing con Specs2

Imagina que ya has escrito algunos métodos y que deseas probarlos, para ver si puedes encontrar fallas en ellos. Una opción es utilizar JUnit para tu código escrito en Java. Sin embargo, en este curso utilizaremos Specs2 para poder probar nuestro código escrito en Scala también.

Para añadir esta dependencia a tu proyecto, visita [http://search.maven.org/](http://search.maven.org/) y busca `specs2`:

![Specs2 Maven Central][]

Busca la versión más reciente de Specs2 y una vez que la hayas seleccionado, utiliza la información de dependencia (busca la sección *Dependency Information*) bajo sbt, como se indica en [Specs2 sbt dependency][]:

![Specs2 sbt dependency][]

Es momento de añadir esta dependencia a tu archivo `build.sbt`, que debe verse similar a este:

```sbt
name := "scalaBasics"

version := "0.1"

scalaVersion := "2.11.0-RC3"

scalacOptions ++= Seq(
  "-language:_"
)

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.scalaz" % "scalaz-core_2.11.0-RC3" % "7.0.6",
  "org.specs2" % "specs2_2.11.0-RC3" % "2.3.10" % "test"  // <------ Añade esta línea
)
```

Observa que se está añadiendo `% "test"` para indicar el `scope` de la dependencia (es una dependencia que se utilizará para hacer testing, pero no para el código de producción.

Crea el paquete `com.oscarvarto` dentro de `src > test` y añade `ArithmeticsSpec.scala`, con el contenido siguiente:


```scala
package com.oscarvarto

import org.specs2.mutable.Specification

class ArithmeticsSpec extends Specification {
  "Multiplicar por dos en Scala" in {
    import HelloVariables.multiplyByTwo
    multiplyByTwo(5) === 10
  }

  "Multiplicar por dos en Java" in {
    import HelloVariablesJava.multiplyByTwo
    multiplyByTwo(4) === 8
  }
}
```

Como puedes notar, podemos probar tanto código escrito en Java como código escrito en Scala. Para ejecutar los tests desde la consola de sbt, invoca la tarea (*task*) `test`:

<pre>
> test
[info] Compiling 1 Scala source to /Users/oscarvarto/ScalaBasics/target/scala-2.11.0-RC3/test-classes...
[info] ArithmeticsSpec
[info] + Multiplicar por dos en Scala
[info] + Multiplicar por dos en Java
[info]
[info] Total for specification ArithmeticsSpec
[info] Finished in 41 ms
[info] 2 examples, 0 failure, 0 error
[info] Passed: Total 2, Failed 0, Errors 0, Passed 2
</pre>

Una manera inteligente de crear *expectativas* en specs2 es mediante la librería [**ScalaCheck**](http://www.scalacheck.org/documentation.html).

Modifica la especificación anterior de la manera siguiente:

```scala
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
```

Observa que nuestra especificación ahora utiliza la *trait* `ScalaCheck`:

```scala
class ArithmeticsSpec extends Specification with ScalaCheck
```

Al probar la propiedad
```scala
"La multiplicación y la suma están relacionadas" in prop {
  (a: Int) => a + a == HelloVariables.multiplyByTwo(a)
}
```

con `test` obtenemos el siguiente resultado:

<pre>
> test
[info] Compiling 1 Scala source to /Users/oscarvarto/ScalaBasics/target/scala-2.11.0-RC3/test-classes...
[info] ArithmeticsSpec
[info] + Multiplicar por dos en Scala
[info] + Multiplicar por dos en Java
[info] + La multiplicación y la suma están relacionadas
[info]
[info] Total for specification ArithmeticsSpec
[info] Finished in 15 ms
[info] 3 examples, 102 expectations, 0 failure, 0 error
[info] Passed: Total 3, Failed 0, Errors 0, Passed 3
[success] Total time: 3 s, completed Mar 29, 2014 12:23:06 PM
</pre>

La siguiente línea nos indica que la última propiedad se verificó **100 veces (con entradas aleatorias)**:
<pre>
[info] 3 examples, 102 expectations, 0 failure, 0 error
</pre>

Si deseas aprender un poco más de ScalaCheck, revisa [este pdf](http://www.artima.com/samples/ScalaCheckCh2.pdf).

[Specs2 Maven Central]: figures/specs2-mavenCentral.png
[Specs2 sbt dependency]: figures/specs2-sbt.png
