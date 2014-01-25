package com.oscarvarto.fp

/**
 * Created by oscarvarto on 1/22/14.
 */
object OptionExperiment extends App {
  /*
  // This group of imports is required to avoid the massive import next

  // The first block is more obvious
  import scalaz.std.option.{optionInstance, none}
  import optionInstance.monadSyntax._
  import scalaz.syntax.equal._
  import scalaz.syntax.std.string._

  // This is harder, and we can get some help from scalac flag: "-Xprint:typer"
  import scalaz.Scalaz.{optionOrder, optionShow}
  import scalaz.Scalaz.booleanInstance
  implicit val ob = optionOrder(booleanInstance)
  implicit val os = optionShow(booleanInstance)
  */
  import scalaz._, Scalaz._

  // These two are simulating the text contents of two Android TextView elements ...
  var nameTextViewContents = "oscar"
  var passwordTextViewContents = ""

  /* Returns None if nameTextViewContents is the empty string */
  def getName: Option[String] = nameTextViewContents.charsNel map { _.list.mkString }
  /* Returns None if passWordTextViewContents is the empty string */
  def getPassword: Option[String] = passwordTextViewContents.charsNel map { _.list.mkString }

  // This is a dummy implementation for illustration purposes
  // Notice how we avoided "infesting" our code to be option-aware
  // because we can *later* lift it in the context of option
  // Read Section 4.3.2 Option composition, lifting, and wrapping exception-oriented APIs
  // of "Functional Programming in Scala" MEAP 12
  def validateUser(name: String, password: String): Boolean = true

  /* I could simplify the imports using Option.isEmpty assert_=== true
     but the next thing should be possible too (and easier, see second commented block of imports)
   */
  val O = Monad[Option]
  O.lift2(validateUser)(getName, getPassword) assert_=== none[Boolean]

  /* Avoid shooting ourselves in the foot passing null here
     Solution is to wrap null into Option _before_ passing the argument,
     Option(null) would return None
   */
  // O.lift2(validateUser)("Oscar".some, null) // <-- Fails at runtime
}
