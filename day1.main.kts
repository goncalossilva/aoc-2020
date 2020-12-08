#!/usr/bin/env kotlinc -script

import java.io.File

val numbers = mutableListOf<Int>()
File("day1.txt").forEachLine { numbers.add(it.toInt()) }
numbers.forEach { n1 ->
    numbers.forEach { n2 ->
        val sum2 = n1 + n2
        if (sum2 == 2020) {
            println("Product of 2 entries that sum 2020: ${n1 * n2}")
        } else if (sum2 < 2020) {
            numbers.forEach { n3 ->
                if (n1 + n2 + n3 == 2020) {
                    println("Product of 3 entries that sum 2020: ${n1 * n2 * n3}")
                }
            }
        }
    }
}
