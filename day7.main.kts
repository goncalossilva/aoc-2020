#!/usr/bin/env kotlinc -script

import java.io.File

val rules = File("day7.txt").readLines()
val bagRegex = "(^[\\w\\s]+) bags contain".toRegex()
val ruleRegex = "(\\d+) ([\\w\\s]+) bags?,?".toRegex()

data class Rule(val bag: String, val count: Int)

val bagRules = mutableMapOf<String, List<Rule>>()
rules.forEach { rule ->
    val node = bagRegex.find(rule)!!.groupValues[1]
    val links = ruleRegex.findAll(rule).map {
        val (count, bag) = it.destructured
        Rule(bag, count.toInt())
    }
    bagRules[node] = links.toList()
}

fun contains(bag: String, otherBag: String): Boolean {
    val links = bagRules[bag] ?: return false
    return links.any { it.bag == otherBag } || links.any { contains(it.bag, otherBag) }
}

fun countInside(bag: String): Int {
    val links = bagRules[bag] ?: return 1
    return links.sumOf { it.count } + links.sumOf { it.count * countInside(it.bag) }
}

// Part 1
println("# can contain: ${bagRules.count { (bag, _) -> contains(bag, "shiny gold") }}")

// Part 2
println("# inside: ${countInside("shiny gold")}")
