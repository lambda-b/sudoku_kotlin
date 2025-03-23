package com.pisanzapra.sudoku.algorithm.dancinglinks

/**
 * A collection of nodes that can be restored.
 *
 * This class is used to represent the nodes that can be restored.
 */
class Restores(val column: Column) : Iterable<LinkNode> {
  private val restores = mutableListOf<LinkNode>()

  fun add(node: LinkNode) {
    restores.add(node)
  }

  override fun iterator(): Iterator<LinkNode> {
    return restores.iterator()
  }
}
