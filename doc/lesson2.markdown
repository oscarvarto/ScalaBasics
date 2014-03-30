XHTML Header: <script type="text/javascript">hljs.configure({tabReplace: '  '}); hljs.initHighlightingOnLoad();</script>

## Javadoc y Scaladoc
Utilicemos el siguiente ejemplo para recordar el uso de Javadoc. Después se introducirá Scaladoc.

```java
package com.oscarvarto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class ControlFlowJava {
    public static void main(String[] args) {
      List<Integer> xs = Arrays.asList(1, 2, 3, 4, 5);
      // Cómo multiplicar por dos los elementos de una lista antes de Java 8
      List<Integer> ys = new ArrayList<>();
      for (int x: xs) {
        ys.add(x * 2);
      }
      // Utilizando lambdas
      List<Integer> zs = xs.stream().map(x -> 2*x).collect(toList());
    }
}
```

Revisa el javadoc de `java.util.List<E>`, específicamente el método cuya *signature* es:
```java
boolean add(E e)
```

Si no tienes una versión local de la documentación de la API de JDK, entonces puedes usar [este vínculo](http://docs.oracle.com/javase/8/docs/api/).

También puedes utilizar la navegación en IntelliJ IDEA para ver el código fuente de este método.

**Ejercicio: Utiliza la documentación de la API de JDK para comprender mejor qué es lo que sucede en la última línea del ejemplo anterior.**

La versión correspondiente en Scala es:
```scala
package com.oscarvarto

object ControlFlowScala extends App {
  val xs = (1 to 5).toList
  val ys = xs map { _ * 2 }
}
```

<pre>
 mkdir temp
$ mv specs2scaladoc/ temp/
$ cd temp/
$ pwd
/Users/oscarvarto/temp
$ mkdir scalaLibrary
$ cd scalaLibrary/
$ cp ~/.ivy2/cache/org.scala-lang/scala-library/docs/scala-library-2.11.0-RC3-javadoc.jar .
$ jar -xf scala-library-2.11.0-RC3-javadoc.jar
$ open index.html
</pre>

(En Linux, tal vez puedas usar `$ firefox index.html` en lugar de la última línea)

Verás el Scaladoc de la librería estándar de Scala, como se muestra en [Scala Library Scaladoc][]

![Scala Library Scaladoc][]

Ahora busca la entrada para `List` en Scaladoc. Deberás ver algo como [Scaladoc de List][]

![Scaladoc de List][]

Busca el scaladoc para el método `map`

![Scaladoc de List#map][]

En parte, dicha documentación dice:

```scala
 final def map[B](f: (A) ⇒ B): List[B]
```

<pre>
[use case]
Builds a new collection by applying a function to all elements of this list.
</pre>

**B**: the element type of the returned collection.</br>
**f**: the function to apply to each element.</br>
**returns**: a new list resulting from applying the given function f to each element of this list and collecting the results.

[Scala Library Scaladoc]: figures/scalaLibrary-scaladoc.png
[Scaladoc de List]: figures/list-scaladoc.png
[Scaladoc de List#map]: figures/map-list-scaladoc.png
