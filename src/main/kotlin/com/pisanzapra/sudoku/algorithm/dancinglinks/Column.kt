package com.pisanzapra.sudoku.algorithm.dancinglinks

/**
 * A column in the matrix used by the Dancing Links algorithm.
 *
 * This class is used to represent the columns in the matrix.
 */
class Column : Iterable<LinkNode> {
  var origin: LinkNode? = null

  fun size(): Int {
    return asSequence().count()
  }

  override fun iterator(): Iterator<LinkNode> {
    return ColumnIterator(origin)
  }

  class ColumnIterator(private var current: LinkNode?) : Iterator<LinkNode> {

    override fun hasNext(): Boolean {
      return current != null && current!!.down != current
    }

    override fun next(): LinkNode {
      val next = current
      current = current!!.down
      return next!!
    }
  }
}
