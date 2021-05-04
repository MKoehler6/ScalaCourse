package com.micha.scala.commands

import com.micha.scala.files.{DirEntry, Directory}
import com.micha.scala.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path, name)

}
