package com.pisanzapra.sudoku.controller

import com.pisanzapra.sudoku.domain.Cell
import com.pisanzapra.sudoku.domain.simple.SimpleBindingCondition
import com.pisanzapra.sudoku.service.SimpleSudokuService
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.lang.invoke.MethodHandles
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sudoku/simple")
class SimpleSudokuController(val service: SimpleSudokuService) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    @GetMapping("/solve")
    fun solve(
            @RequestParam("query", required = true)
            @Size(min = 81, max = 81)
            @Pattern(regexp = "[0-9]+")
            query: String
    ): List<Cell<SimpleBindingCondition>> {

        logger.info("Solving Sudoku with query: $query")

        return service.solve(query)
    }
}
