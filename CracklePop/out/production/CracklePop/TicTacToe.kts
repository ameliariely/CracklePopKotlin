import kotlin.system.exitProcess

val game = Game()
game.playTicTacToe()

class Game {
    val gridSize = 3;
    val grid = Array<Array<Char>>(gridSize, { Array<Char>(gridSize, { '*' }) })
    var player = 1;
    val symbols = listOf<Char>('x', 'o')
    var playerSymbol = symbols[player - 1]

    fun playTicTacToe() {
        println("\n\nHello and welcome to command line TicTacToe in Kotlin. Let's get started.")
        printGame()
        //pass in the total number of spots, so that we can end the game easily when there are none left
        recursivePlayATurn(gridSize*gridSize)
    }

    private fun recursivePlayATurn(spotsLeft: Int) {
        if (spotsLeft == 0) {
            noWinner()
        }
        println("It's player $player's turn. Remember that your symbol is $playerSymbol.")
        if (player == 1) {
            //human
            recursiveGetAndEvaluateASpot()
        } else {
            //computer

        }
        printGame()
        prepareNextTurn()
        recursivePlayATurn(spotsLeft - 1)
    }

    private fun noWinner() {
        println("Whoops! Looks like there are no more spots left and no one can win the game. Try a new game.")
        exitProcess(0)
    }

    private fun prepareNextTurn() {
        if(player == 1) {
            print("\t")
            //just some random commentary from the computer
            when (Math.floor(Math.random() * 3).toInt()) {
                0 -> println("Great pick, player $player.")
                1 -> println("Interesting choice, player $player.")
                2 -> println("Hmmm, not sure I would have picked that, player $player...")
            }
            print("\n\n")
        }
        //rotate the player (1 or 2) and then adjust the player to a 0-1 scale when finding the player's symbol
        player = player % 2 + 1
        playerSymbol = symbols[player - 1]
    }

    private fun recursiveGetAndEvaluateASpot() {
        var row : Int
        val column : Int
        if (player == 1) {
            row = getInputRow()
            column = getInputColumn()
        } else {
            row = getIndexBetween1AndSize()
            column = getIndexBetween1AndSize()
        }

        if (grid[row][column] != '*') {
            if (player == 1) println("This spot is already occupied. Try another!")
            recursiveGetAndEvaluateASpot()
        } else {
            //this is a valid spot!
            if (player != 1) {
                println("The computer selected $row, $column.")
            }
            markSpotAndCheckForWin(row, column)
        }
    }

    private fun markSpotAndCheckForWin(row: Int, column: Int) {
        grid[row][column] = playerSymbol
        //end game if the user just won this row
        checkDimensionForWin(grid[row])
        //end game if the user just won this column
        checkDimensionForWin(Array<Char>(gridSize, { i -> grid[i][column]}))
        //end game if the user won top left to bottom right diagonal
        checkDimensionForWin(Array<Char>(gridSize, { i -> grid[i][i]}))
        //end game if user won top right to bottom left diagonal
        checkDimensionForWin(Array<Char>(gridSize, { i -> grid[2-i][i]}))
    }


    private fun checkDimensionForWin(dimensionArray: Array<Char>) {
        //if the every spot in this dimension has the player's symbol, the player has won!
        //we can assume that the other player has not won since we have checked every dimension after every turn
        if (dimensionArray.all { c -> c == playerSymbol }) {
            println("""Player $player, you have won!
            |Amazing!
            |You are really good at TicTacToe.
            |High Five.

            |Here is the winning board:""".trimMargin())
            printGame()
            exitProcess(0)
        }
    }

    //lots of new lines to make things pretty.
    private fun printGame() {
        print("\n\n\n   ")
        for (i in 1..gridSize) print("  $i")
        println()
        var i = 1
        for (row in grid) {
            print("  $i")
            i++
            for (spot in row) {
                print("  $spot")
            }
            print("\n\n\n")
        }
    }

    private fun recursiveGetInputRowOrColumn(rowOrColumn: String): Int {
        //Int? because we might get null from readline, which isn't a null-safe method
        val input: Int?
        println("Please enter the $rowOrColumn number " +
                "in which you would like to place your piece.\n")
        try {
            input = readLine()?.toInt();
            //if the input is acceptable, adjust it by subtracting 1 and return it the recursiveGetAndEvaluateSpot method
            if (input != null && input >= 1 && input <= gridSize) {
                println("\nOk, choosing $rowOrColumn $input.\n")
                return input - 1
            }
        } catch (e: NumberFormatException) {
            //do nothing, since this is expected if the user doesn't type a number
        } catch (e :Exception) {
            //this is unexpected
            e.printStackTrace()
        }
        //if we got here, something went wrong. Ask the user for new input.
        println("Invalid input. Please enter only a number between 1 and $gridSize.")
        return recursiveGetInputRowOrColumn(rowOrColumn)
    }

    private fun getIndexBetween1AndSize() : Int {
        return Math.floor(Math.random() * gridSize).toInt()
    }

    //these methods are here to abstract away the need to pass a string.
    //ask for a row until you get a valid one
    private fun getInputRow(): Int {
        return recursiveGetInputRowOrColumn("row")
    }

    //ask for a column until you get a valid one
    private fun getInputColumn(): Int {
        return recursiveGetInputRowOrColumn("column")
    }

}


