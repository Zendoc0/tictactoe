var counter = 0
const val X:Char = 'X'
const val O:Char = 'O'
const val emptyCell:Char = ' '
fun main() {
    println("\n---------")
    println("|       |")
    println("|       |")
    println("|       |")
    println("---------\n")


    //val input = readLine()!!
    val storage =
        charArrayOf(emptyCell, emptyCell, emptyCell, emptyCell, emptyCell, emptyCell, emptyCell, emptyCell, emptyCell)

    mainLoop@ while (gameNotFinished(storage)){
    // loop for X user input
        do {
            val coordinates: CharArray = readln().toCharArray()
            print("Enter the Coordinates: ")
            for (i in coordinates) {
                print(i)
            }

            if (xyValidiation(coordinates, storage)) {
                fieldState(mapInputX(coordinates, storage))
                if (processingGamestate(storage)) break@mainLoop
                break
            }
        } while (true)

        // loop for O user Input
        do {
            val coordinates: CharArray = readln().toCharArray()
            print("Enter the Coordinates: ")
            for (i in coordinates) {
                print(i)
            }

            if (xyValidiation(coordinates, storage)) {
                fieldState(mapInputO(coordinates, storage))
                if (processingGamestate(storage)) break@mainLoop
                break
            }
        } while (true)
    }
}

fun xyValidiation(xy:CharArray, storage: CharArray):Boolean{
    var returnValue = false
    when{
        !notNum(xy) -> println("\nYou should enter numbers!")
        !notCoordinates(xy) -> println("\nCoordinates should be from 1 to 3!")
        cellOccupied(xy, storage) -> println("\nThis cell is occupied! Choose another one!")
        else -> returnValue = true
    }
    return returnValue

}
// prints the current state of the field
fun fieldState(storage: CharArray):Int{
    println("\n---------")
    println("| ${storage[0]} ${storage[1]} ${storage[2]} |")
    println("| ${storage[3]} ${storage[4]} ${storage[5]} |")
    println("| ${storage[6]} ${storage[7]} ${storage[8]} |")
    println("---------")

    return counter ++
}

//first check which cells are occupied then check if any of these cells == cells
fun cellOccupied(xy: CharArray, storage:CharArray):Boolean{
    return when{
        xy[0] == '1' && xy[2] == '1' && storage[0] == emptyCell -> false
        xy[0] == '1' && xy[2] == '2' && storage[1] == emptyCell -> false
        xy[0] == '1' && xy[2] == '3' && storage[2] == emptyCell -> false
        xy[0] == '2' && xy[2] == '1' && storage[3] == emptyCell -> false
        xy[0] == '2' && xy[2] == '2' && storage[4] == emptyCell -> false
        xy[0] == '2' && xy[2] == '3' && storage[5] == emptyCell -> false
        xy[0] == '3' && xy[2] == '1' && storage[6] == emptyCell -> false
        xy[0] == '3' && xy[2] == '2' && storage[7] == emptyCell -> false
        xy[0] == '3' && xy[2] == '3' && storage[8] == emptyCell -> false
        else -> true
    }
}

// checks if the xy input are digits
fun notNum(xy: CharArray):Boolean{
    return xy[0].isDigit() && xy[2].isDigit()
}

// checks if the xy input are valid number (1..3 range)
fun notCoordinates(xy: CharArray):Boolean{
    return xy[0].digitToInt() in 1..3 && xy[2].digitToInt() in 1..3
}


// maps the xy input to the right storage place
fun mapInputX(xy: CharArray, storage: CharArray):CharArray{
    when{
        xy[0] == '1' && xy[2] == '1' -> storage[0] = X
        xy[0] == '1' && xy[2] == '2' -> storage[1] = X
        xy[0] == '1' && xy[2] == '3' -> storage[2] = X
        xy[0] == '2' && xy[2] == '1' -> storage[3] = X
        xy[0] == '2' && xy[2] == '2' -> storage[4] = X
        xy[0] == '2' && xy[2] == '3' -> storage[5] = X
        xy[0] == '3' && xy[2] == '1' -> storage[6] = X
        xy[0] == '3' && xy[2] == '2' -> storage[7] = X
        xy[0] == '3' && xy[2] == '3' -> storage[8] = X
    }
    return storage
}


// maps the xy input to the right storage place
fun mapInputO(xy: CharArray, storage: CharArray):CharArray{
    when{
        xy[0] == '1' && xy[2] == '1' -> storage[0] = O
        xy[0] == '1' && xy[2] == '2' -> storage[1] = O
        xy[0] == '1' && xy[2] == '3' -> storage[2] = O
        xy[0] == '2' && xy[2] == '1' -> storage[3] = O
        xy[0] == '2' && xy[2] == '2' -> storage[4] = O
        xy[0] == '2' && xy[2] == '3' -> storage[5] = O
        xy[0] == '3' && xy[2] == '1' -> storage[6] = O
        xy[0] == '3' && xy[2] == '2' -> storage[7] = O
        xy[0] == '3' && xy[2] == '3' -> storage[8] = O
    }
    return storage
}


fun processingGamestate(storage: CharArray):Boolean{
    var returnValue = false

    if (impossible(storage) || draw(storage) || xWon(storage) || oWon(storage)){
        checkGameState(storage)
        returnValue = true
    }
    return returnValue
}

// THE FUNCTIONS BELOW ARE CHECKING THE POSSIBLE GAMESTATES
// checkGameState checks from which gamestate the conditions are satisfied
fun checkGameState(user_input:CharArray) {
    when{
        impossible(user_input) -> println("Impossible")
        //gameNotFinished(user_input) -> println("Game not finished")
        draw(user_input) -> println("Draw")
        xWon(user_input) -> println("X wins")
        oWon(user_input) -> println("O wins")
    }
}

// checks if the gamestate is possible -> there can't be 3 in a row from both 'O' and 'X'
fun impossible(input: CharArray):Boolean{
    var returnValue = false
    var countX = 0
    var countO = 0
    for (i in input){
        if (i == X){
            countX++
        } else if (i == O){
            countO++
        }
    }
    if (countX - countO >= 2 || countO - countX >= 2 || oWon(input) && xWon(input)){
        returnValue = true
    }
    return returnValue
}


// checks if the game is finished or not (oWon or xWon are not fulfilled and there are still emptycells to be filled)
fun gameNotFinished(input: CharArray):Boolean{
    var returnValue = false
    for (i in input){
        if (i == emptyCell && !xWon(input) && !oWon(input)){
            returnValue = true
        }
    }
    return returnValue
}

// checks if the game is a draw -> all cells are filled and oWon and xWon are both not fullfilled
fun draw(input: CharArray):Boolean{
    var returnValue = false
    if (input[0] != emptyCell && input[1] != emptyCell && input[2] != emptyCell &&
        input[3] != emptyCell && input[4] != emptyCell && input[5] != emptyCell &&
        input[6] != emptyCell && input[7] != emptyCell && input[8] != emptyCell &&
        !xWon(input) && !oWon(input)
    ){
        returnValue = true
    }
    return returnValue
}

// checks if there are three 'X' in a row (winning condition)
fun xWon(input: CharArray):Boolean{
    var ergebnis = false
    if (   input[0] == X && input[1] == X && input[2] == X
        || input[0] == X && input[4] == X && input[8] == X
        || input[1] == X && input[4] == X && input[7] == X
        || input[2] == X && input[5] == X && input[8] == X
        || input[3] == X && input[4] == X && input[5] == X
        || input[6] == X && input[7] == X && input[8] == X
        || input[2] == X && input[4] == X && input[6] == X
        || input[0] == X && input[3] == X && input[6] == X
    ) {
        ergebnis = true
    }
    return ergebnis
}

// checks if there are three 'O' in a row (winning condition)
fun oWon(input: CharArray):Boolean{
    var ergebnis = false
    if (   input[0] == O && input[1] == O && input[2] == O
        || input[0] == O && input[4] == O && input[8] == O
        || input[1] == O && input[4] == O && input[7] == O
        || input[2] == O && input[5] == O && input[8] == O
        || input[3] == O && input[4] == O && input[5] == O
        || input[6] == O && input[7] == O && input[8] == O
        || input[2] == O && input[4] == O && input[6] == O
        || input[0] == O && input[3] == O && input[6] == O
    ) {
        ergebnis = true
    }
    return ergebnis
}