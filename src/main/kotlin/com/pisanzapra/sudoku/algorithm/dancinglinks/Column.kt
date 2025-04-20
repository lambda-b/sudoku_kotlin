package com.pisanzapra.sudoku.algorithm.dancinglinks

/**
 * A column in the matrix used by the Dancing Links algorithm.
 *
 * This class is used to represent the columns in the matrix.
 */
class Column<R, C>(val id: C) : Iterable<LinkNode<R, C>> {
  var origin: LinkNode<R, C>? = null

  fun size(): Int {
    return asSequence().count()
  }

  override fun iterator(): Iterator<LinkNode<R, C>> {
    return ColumnIterator(origin)
  }

  class ColumnIterator<R, C>(private var current: LinkNode<R, C>?) : Iterator<LinkNode<R, C>> {

    override fun hasNext(): Boolean {
      return current != null && current!!.down != current
    }

    override fun next(): LinkNode<R, C> {
      val next = current
      current = current!!.down
      return next!!
    }
  }
}
