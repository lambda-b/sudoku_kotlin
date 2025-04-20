import com.pisanzapra.sudoku.algorithm.dancinglinks.Column
import com.pisanzapra.sudoku.algorithm.dancinglinks.LinkNode
import com.pisanzapra.sudoku.algorithm.dancinglinks.Row

class Matrix<R, C>(private val headers: MutableSet<Column<R, C>>) {

  /**
   * 解として選択された行を引数とし、関連するノードを削除します。
   * 削除したノードを後の復元のために返します。
   * @param selected 選択された行
   * @return 削除したノードのリスト
   */
  fun select(selected: Row<R, C>): List<Restores<R, C>> {
    val restorationsList = mutableListOf<Restores<R, C>>()
    for (hNode in selected) {
      val restorations = Restores(hNode.column)
      for (vNode in hNode.column) {
        restorations.add(vNode)
        vNode.row.clear()
      }
      restorationsList.add(restorations)
      headers.remove(hNode.column)
    }
    return restorationsList
  }

  /**
   * 引数で渡した削除済ノードを削除と逆の順番で復元します。
   * @param restorationsList 復元するノードのリスト
   */
  fun deselect(restorationsList: List<Restores<R, C>>) {
    for (restorations in restorationsList.asReversed()) {
      headers.add(restorations.column)
      for (node in restorations) {
        node.row.restore()
      }
    }
  }

  /**
   * ExactCover問題のSolver
   *
   * @param solution 現在仮定している解の部分
   * @returns 得られた解
   */
  fun solveExactCover(solution: ArrayDeque<Row<R, C>>): Sequence<List<Row<R, C>>> = sequence {
    if (headers.isEmpty()) {
      yield(solution.toList())
    } else {
      val minCol = headers.minBy { it.size() }
      for (node in minCol) {
        solution.add(node.row)
        val restorationsList = select(node.row)
        yieldAll(solveExactCover(solution))
        deselect(restorationsList)
        solution.removeLast()
      }
    }
  }

  /**
   * Restores
   * @param column
   */
  private static class Restores<R, C>(val column: Column<R, C>) : Iterable<LinkNode<R, C>> {
    private val restores = mutableListOf<LinkNode<R, C>>()

    fun add(node: LinkNode<R, C>) {
      restores.add(node)
    }

    override fun iterator(): Iterator<LinkNode<R, C>> {
      return restores.iterator()
    }
  }
}
