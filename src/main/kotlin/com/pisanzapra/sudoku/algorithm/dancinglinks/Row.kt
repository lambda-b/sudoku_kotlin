package com.pisanzapra.sudoku.algorithm.dancinglinks

/**
 * A row in the matrix used by the Dancing Links algorithm.
 *
 * This class is used to represent the rows in the matrix.
 */
class Row : Iterable<LinkNode> {
  var origin: LinkNode? = null

  fun clear() {
    for (node in this) {
      node.clearVertical()
    }
  }

  fun restore() {
    for (node in this) {
      node.restoreVertical()
    }
  }

  override fun iterator(): Iterator<LinkNode> {
    return RowIterator(origin)
  }

  class RowIterator(private var current: LinkNode?) : Iterator<LinkNode> {

    override fun hasNext(): Boolean {
      return current != null && current!!.right != current
    }

    override fun next(): LinkNode {
      val next = current
      current = current!!.right
      return next!!
    }
  }
}
