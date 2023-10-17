package bullscows

import kotlin.random.Random
import kotlin.system.exitProcess

fun main() {
    var secretCodeInput: Int = 0
    var positionInput: Int = 0
    while (true) {
        val read = readln()
        println("Please, enter the secret code's length:")
        try {
          read.toInt()
        }
        catch (e: NumberFormatException) {
            print("Error: \"$read\" isn't a valid number.")
            exitProcess(0)
        }
       secretCodeInput = read.toInt()
        try {
            println("Input the number of possible symbols in the code:")
            positionInput = readln().toInt()
            if (positionInput > 36)
                throw StringIndexOutOfBoundsException()
        }
        catch (e:StringIndexOutOfBoundsException){
            println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
            exitProcess(0)
        }
        try {
            if (positionInput < secretCodeInput)
                throw MyException()
        }
        catch (e:MyException){
            println("Error: it's not possible to generate a code with a length of $secretCodeInput with $positionInput unique symbols.")
            exitProcess(0)
        }
        try {
            if (secretCodeInput == 0)
                throw MyException()
        }
        catch (e:MyException){
            println("error")
            exitProcess(0)
        }
        if (secretCodeInput != positionInput) break
    }
    val symbols = "0123456789abcdefghijklmnopqrstuvwxyz"
    var randomNumbers = ""
    if (secretCodeInput > 36)
        println("Error: can't generate a secret number with a length of 36 because there aren't enough unique digits.")
    else if (positionInput >= secretCodeInput && secretCodeInput != 0 && positionInput in 1..36 && secretCodeInput is Int) {
        while (true){
            val code = symbols.take(positionInput).random()
            if (!randomNumbers.contains(code)) randomNumbers += code
            if (randomNumbers.length == secretCodeInput) break
        }
    }
        println(buildString {
            append("The secret is prepared: ")
            append("*".repeat(secretCodeInput))
            append(" (0-")
            append(if (positionInput > 10) "9, a-${symbols[positionInput - 1]})" else "${positionInput - 1})")
        })
        println("Okay, let's start a game!")

    val secretCode = randomNumbers
    var count = 0

        while (true) {
            if (positionInput < secretCodeInput)
                break
            count++
            println("Turn $count:")
            val userInput = readln()
            var countCows = 0
            var countBulls = 0
            for (i in userInput.indices) {
                if (userInput[i] == secretCode[i]) {
                    countBulls++
                } else if (userInput[i] in secretCode && userInput[i] != secretCode[i]) {
                    countCows++
                }
            }
            if (countBulls == secretCodeInput) {
                println(
                    "Grade: $countBulls bulls\n" +
                            "Congratulations! You guessed the secret code.")
                break
            }
            when {
                countBulls > 0 && countCows > 0 -> {
                    when {
                        countBulls > 1 && countCows > 1 -> println("Grade: $countBulls bulls and $countCows cows")
                        countBulls > 1 -> println("Grade: $countBulls bulls and $countCows cow")
                        countCows > 1 -> println("Grade: $countBulls bull and $countCows cows")
                        else -> println("Grade: $countBulls bull and $countCows cow")
                    }
                }
                countBulls > 0 -> {
                    when {
                        countBulls > 1 -> println("Grade: $countBulls bulls")
                        else -> println("Grade: $countBulls bull")
                    }
                }
                countCows > 0 -> {
                    when {
                        countCows > 1 -> println("Grade: $countCows cows")
                        else -> println("Grade: $countCows cow")
                    }
                }
                countCows == 0 && countBulls == 0 -> println("Grade: None")
            }
        }
}

class MyException: Exception()





