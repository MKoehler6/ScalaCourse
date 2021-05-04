package com.micha.scala.commands

import com.micha.scala.filesystem.State

class Pwd extends Command {

  override def apply(state: State): State =
    state.setMessage(state.wd.path)

}
