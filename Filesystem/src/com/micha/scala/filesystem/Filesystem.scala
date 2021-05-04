package com.micha.scala.filesystem

import java.util.Scanner

import com.micha.scala.commands.Command
import com.micha.scala.files.Directory

object Filesystem extends App{

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while(true) {
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
