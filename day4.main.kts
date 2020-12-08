#!/usr/bin/env kotlinc -script

import java.io.File

val passports = File("day4.txt").readText().trim().split("\n\n")
val fieldRegex = Regex("(\\w{3}):(\\S+)")

// Part 1
val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"/*, "cid"*/)
val requiredCount = passports.count { passport ->
    val fields = fieldRegex.findAll(passport).map { it.groupValues[1] }.toList()
    fields.containsAll(requiredFields)
}
println("Passports with required fields: $requiredCount")

// Part two
val validFields = mapOf(
    "byr" to { s: String -> s.toInt() in 1920..2002 },
    "iyr" to { s: String -> s.toInt() in 2010..2020 },
    "eyr" to { s: String -> s.toInt() in 2020..2030 },
    "hgt" to { s: String ->
        "(\\d{3})cm".toRegex().find(s)?.let { it.groupValues[1].toInt() in 150..193 }
            ?: "(\\d{2})in".toRegex().find(s)?.let { it.groupValues[1].toInt() in 59..76 }
            ?: false
    },
    "hcl" to { s: String -> "#[a-f0-9]{6}".toRegex().matches(s) },
    "ecl" to { s: String -> s in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
    "pid" to { s: String -> "[0-9]{9}".toRegex().matches(s) }
)
val validCount = passports.count { passport ->
    val fields =
        fieldRegex.findAll(passport).associate { it.groupValues[1] to it.groupValues[2] }
    fields.keys.containsAll(validFields.keys) && fields.all { (name, value) ->
        validFields[name]?.invoke(value) ?: true
    }
}
println("Passports with valid fields: $validCount")
