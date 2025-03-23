import com.pisanzapra.sudoku.algorithm.dancinglinks.Column
import com.pisanzapra.sudoku.algorithm.dancinglinks.Restores
import com.pisanzapra.sudoku.algorithm.dancinglinks.Row

class Matrix(private val headers: MutableSet<Column>) {

  fun select(selected: Row): List<Restores> {
    val restorationsList = mutableListOf<Restores>()
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

  fun deselect(restorationsList: List<Restores>) {
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
  fun solveExactCover(solution: ArrayDeque<Row>): Sequence<List<Row>> = sequence {
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
}
