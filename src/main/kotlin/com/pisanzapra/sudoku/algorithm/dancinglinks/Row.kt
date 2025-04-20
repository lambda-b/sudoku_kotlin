package com.pisanzapra.sudoku.algorithm.dancinglinks

/**
 * A row in the matrix used by the Dancing Links algorithm.
 *
 * This class is used to represent the rows in the matrix.
 */
class Row<R, C>(val id: R) : Iterable<LinkNode<R, C>> {
  var origin: LinkNode<R, C>? = null

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

  override fun iterator(): Iterator<LinkNode<R, C>> {
    return RowIterator(origin)
  }

  class RowIterator<R, C>(private var current: LinkNode<R, C>?) : Iterator<LinkNode<R, C>> {

    override fun hasNext(): Boolean {
      return current != null && current!!.right != current
    }

    override fun next(): LinkNode<R, C> {
      val next = current
      current = current!!.right
      return next!!
    }
  }
}
