package fr.xebia.codeelevator

import Direction.Direction
import Command.Command

class ElevatorEngine {
  def nextCommand: Command = {
    println(s"nextCommand : ${Command.NOTHING}")
    Command.NOTHING
  }

  def call(atFloor: Int, to: Direction): Unit = {
    println(s"call($atFloor, $to)")
  }

  def go(floorToGo: Int): Unit = {
    println(s"go($floorToGo)")
  }

  def userHasEntered(): Unit = {
    println(s"userHasEntered")
  }

  def userHasExited(): Unit = {
    println(s"userHasExited")
  }

  def reset(cause: String): Unit = {
    println(s"reset($cause)")
  }
}
