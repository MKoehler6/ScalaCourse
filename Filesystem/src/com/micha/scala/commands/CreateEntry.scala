package com.micha.scala.commands

import com.micha.scala.files.{DirEntry, Directory}
import com.micha.scala.filesystem.State

abstract class CreateEntry(name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)){
      state.setMessage("Entry " + name + " already exists!")
    }
    else if(name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contains separators")
    }
    else if(checkillegal(name)) {
      state.setMessage(name + ": illegal entry name")
    } else {
      doCreateEntry(state, name)
    }
  }

  def  checkillegal(str: String): Boolean = {
    name.contains(("."))
  }

  def doCreateEntry(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd

    // 1. get all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath
    // 2. create new directory entry in the wd
    val newEntry: DirEntry = createSpecificEntry(state)
    // 3. update the whole directory structure starting from the root
    // (the directory structure is immutable) (but the directory instances will be reused)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    // 4. find new working directory INSTANCE given wd's full path, in the new structure
    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }

  def createSpecificEntry(state: State): DirEntry
}
