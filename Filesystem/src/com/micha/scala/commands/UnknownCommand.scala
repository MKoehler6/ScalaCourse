package com.micha.scala.commands

import com.micha.scala.filesystem.State

class UnknownCommand extends Command {

  override def apply(state: State): State =
    state.setMessage(("Command not found"))

}
