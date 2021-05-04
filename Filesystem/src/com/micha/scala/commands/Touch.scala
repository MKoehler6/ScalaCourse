package com.micha.scala.commands

import com.micha.scala.files.{DirEntry, File}
import com.micha.scala.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path, name)
}
