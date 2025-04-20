package com.pisanzapra.sudoku.service

import Matrix
import com.pisanzapra.sudoku.algorithm.dancinglinks.Column
import com.pisanzapra.sudoku.algorithm.dancinglinks.LinkNode
import com.pisanzapra.sudoku.algorithm.dancinglinks.Row
import com.pisanzapra.sudoku.domain.Cell
import com.pisanzapra.sudoku.domain.simple.SimpleBindingCondition
import com.pisanzapra.sudoku.domain.simple.SimpleCell
import org.springframework.stereotype.Service

@Service
class SimpleSudokuService {

    /**
     * Solves a Sudoku puzzle using the Dancing Links algorithm.
     *
     * @param sudoku A 2D list representing the Sudoku puzzle, where 0 represents an empty cell.
     * @return A list of cells representing the solved Sudoku puzzle.
     * @throws IllegalStateException if no solution is found.
     */
    fun solve(sudoku: String): List<Cell<SimpleBindingCondition>> {
        val covers =
                (1..9)
                        .map { row ->
                            (1..9)
                                    .map { col ->
                                        (1..9).map { value -> SimpleCell(row, col, value) }
                                    }
                                    .flatMap { it }
                        }
                        .flatMap { it }
                        .map { cell -> Row<SimpleCell, SimpleBindingCondition>(cell) }
        val headers =
                covers
                        .map { row ->
                            row.id.bindings
                                    .map {
                                        LinkNode.create(
                                                row,
                                                Column<SimpleCell, SimpleBindingCondition>(it)
                                        )
                                    }
                                    .map { it.column }
                        }
                        .flatMap { it }
                        .toMutableSet()
        val matrix = Matrix(headers)

        val mapper = covers.associateBy { it.id }
        sudoku.forEachIndexed { i, c ->
            val value = c.toString().toInt()
            if (value != 0) {
                val algRow = mapper[SimpleCell(i / 9 + 1, i % 9 + 1, value)]
                if (algRow != null) {
                    matrix.select(algRow)
                }
            }
        }

        val solution = matrix.solveExactCover(ArrayDeque())?.map { it.id }
        if (solution == null) {
            throw IllegalStateException("No solution found")
        }

        return solution
    }
}
