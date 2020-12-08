#!/usr/bin/env kotlinc -script

import java.io.File

val groups = File("day6.txt").readText().trim().split("\n\n")

// Part 1
val countAnyone = groups.fold(0) { count, groupAnswer ->
    count + groupAnswer.replace("[^a-z]+".toRegex(), "").toSet().size
}
println("Sum anyone answered yes: $countAnyone")

// Part 2
val countEveryone = groups.fold(0) { count, groupAnswer ->
    val individualAnswers = groupAnswer.split("\n")
    count + individualAnswers[0].count { c -> individualAnswers.all { a -> a.contains(c) } }
}
println("Sum everyone answered yes: $countEveryone")
