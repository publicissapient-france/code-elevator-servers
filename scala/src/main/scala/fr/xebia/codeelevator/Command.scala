package fr.xebia.codeelevator

object Command extends Enumeration {
  type Command = Value
  val UP = Value("UP")
  val DOWN = Value("DOWN")
  val OPEN = Value("OPEN")
  val CLOSE = Value("CLOSE")
  val NOTHING = Value("NOTHING")
}
