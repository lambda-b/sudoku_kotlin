package com.pisanzapra.sudoku.algorithm.dancinglinks

/**
 * A node in a doubly linked list.
 *
 * This class is used to represent the nodes in the columns and rows of the matrix used by the
 * Dancing Links algorithm.
 */
class LinkNode private constructor(val row: Row, val column: Column) {
  var left: LinkNode = this
  var right: LinkNode = this
  var up: LinkNode = this
  var down: LinkNode = this

  companion object {
    fun create(row: Row, column: Column): LinkNode {
      val node = LinkNode(row, column)
      node.restoreVertical()
      node.restoreHorizontal()
      return node
    }
  }

  fun clearVertical() {
    if (column.origin == this) {
      column.origin = if (this == down) null else this.down
    }
    up.down = down
    down.up = up

    up = this
    down = this
  }

  fun restoreVertical() {
    if (column.origin != null) {
      val origin = column.origin!!
      up = origin.up
      down = origin
      origin.up.down = this
      origin.up = this
    } else {
      column.origin = this
    }
  }

  fun restoreHorizontal() {
    if (row.origin != null) {
      val origin = row.origin!!
      left = origin.left
      right = origin
      origin.left.right = this
      origin.left = this
    } else {
      row.origin = this
    }
  }
}
