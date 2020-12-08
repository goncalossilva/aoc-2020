#!/usr/bin/env kotlinc -script

import java.io.File

val passes = File("day5.txt").readLines()
val rowCount = 128
val colCount = 8
fun seatId(s: String): Int {
    var rowMin = 0
    var rowMax = rowCount - 1
    var colMin = 0
    var colMax = colCount - 1
    s.forEach { l ->
        when (l) {
            'F' -> rowMax -= (rowMax - rowMin + 1) / 2
            'B' -> rowMin += (rowMax - rowMin + 1) / 2
            'L' -> colMax -= (colMax - colMin + 1) / 2
            'R' -> colMin += (colMax - colMin + 1) / 2
        }
    }
    return rowMin * 8 + colMin
}

// Part 1
println("Highest seat id: ${passes.maxOf { seatId(it) }}")

// Part 2
val ids = passes.map { seatId(it) }
println("Seat id: ${ids.find { !ids.contains(it + 1) && ids.contains(it + 2) }?.plus(1)}")
