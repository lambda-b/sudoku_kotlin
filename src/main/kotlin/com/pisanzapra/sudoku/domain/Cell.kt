package com.pisanzapra.sudoku.domain

interface Cell<T> {
  val address: Int
  val value: Int
  val bindings: List<T>
}
