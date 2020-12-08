#!/usr/bin/env kotlinc -script

import java.io.File

val map = File("day3.txt").readLines()

fun countTrees(rowInc: Int, colInc: Int): Int {
    var treeCount = 0
    var i = 0
    var j = 0
    while (i < map.size) {
        if (map[i][j % map[i].length] == '#') {
            treeCount += 1
        }
        i += rowInc
        j += colInc
    }
    return treeCount
}

// Part 1
println("Tree count: ${countTrees(1, 3)}")

// Part 2
val increments = arrayOf(1 to 1, 1 to 3, 1 to 5, 1 to 7, 2 to 1)
val multiplication = increments.fold(1L) { acc: Long, increment ->
    val (rowInc, colInc) = increment
    acc * countTrees(rowInc, colInc)
}
println("Multiplication of tree for each slope: $multiplication")
