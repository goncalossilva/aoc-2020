#!/usr/bin/env kotlinc -script

import java.io.File

val policies = File("day2.txt").readLines()

// Part 1
val count1 = policies.count {
    val (policy, pwd) = it.split(":")
    "(\\d+)-(\\d+) (\\w+)".toRegex().find(policy)?.destructured?.let { (min, max, word) ->
        pwd.count { ch -> ch == word[0] } in min.toInt()..max.toInt()
    } ?: false
}
println("Valid password with sled rental policy: $count1")

// Part 2
val count2 = policies.count {
    val (policy, pwd) = it.split(":")
    "(\\d+)-(\\d+) (\\w+)".toRegex().find(policy)?.destructured?.let { (pos1, pos2, word) ->
        (pwd[pos1.toInt()] == word[0]) != (pwd[pos2.toInt()] == word[0])
    } ?: false
}
println("Valid password with Official Toboggan Corporate Authentication System policy: $count2")
