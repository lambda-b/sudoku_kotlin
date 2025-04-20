package com.pisanzapra.sudoku.domain.simple

import com.pisanzapra.sudoku.domain.Cell

data class SimpleCell(
        val row: Int,
        val column: Int,
        override val value: Int,
) : Cell<SimpleBindingCondition> {

    override val address: Int
        get() = (row - 1) * 9 + column - 1

    override val bindings: List<SimpleBindingCondition>
        get() =
                listOf(
                        SimpleBindingCondition.valueOf( //
                                String.format("ADDRESS_%d_%d", row, column)
                        ),
                        SimpleBindingCondition.valueOf( //
                                String.format("ROW_%d_%d", row, value)
                        ),
                        SimpleBindingCondition.valueOf( //
                                String.format("COLUMN_%d_%d", column, value)
                        ),
                        SimpleBindingCondition.valueOf( //
                                String.format(
                                        "BOX_%d_%d",
                                        ((row - 1) / 3) * 3 + (column - 1) / 3 + 1,
                                        value
                                )
                        )
                )
}
