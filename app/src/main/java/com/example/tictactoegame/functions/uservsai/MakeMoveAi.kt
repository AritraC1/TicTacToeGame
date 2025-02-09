package com.example.tictactoegame.functions.uservsai

import com.example.tictactoegame.functions.TicTacToeState

fun makeMoveAI(state: TicTacToeState): TicTacToeState {
    // Ignore if game is already over
    if (state.winner != null) return state

    val emptyCells = state.board.flatMapIndexed { row, rowList ->
        rowList.mapIndexedNotNull { col, cell -> if (cell == null) Pair(row, col) else null }
    }

    if (emptyCells.isEmpty()) return state // No moves left

    // AI tries to win first, then blocks player, then chooses best Minimax move
    val aiMove = findWinningMove(state.board, "O") ?: // AI winning move
    findWinningMove(state.board, "X") ?: // Block player
    (if ((0..3).random() == 0) emptyCells.random() // 25% chance of randomness
    else minimax(state.board, 0, true).second) // Otherwise, pick best move

    val (aiRow, aiCol) = aiMove

    // Create a new board with AI's move
    val newBoard = state.board.mapIndexed { r, rowList ->
        rowList.mapIndexed { c, cell -> if (r == aiRow && c == aiCol) "O" else cell }
    }

    // Check for a winner
    val (winner, winningLine) = checkWinner(newBoard)

    // Check for a draw (if the board is full and no winner)
    val isDraw = newBoard.flatten().none { it == null } && winner == null
    val newWinner = winner ?: if (isDraw) "Draw" else null

    // Update the game state
    return state.copy(
        board = newBoard,
        currentPlayer = if (newWinner == null) "X" else state.currentPlayer, // Switch turn only if no winner
        winner = newWinner,
        winningLine = winningLine
    )
}

fun findWinningMove(board: List<List<String?>>, player: String): Pair<Int, Int>? {
    for (row in board.indices) {
        for (col in board[row].indices) {
            if (board[row][col] == null) { // Empty cell
                val tempBoard = board.mapIndexed { r, rowList ->
                    rowList.mapIndexed { c, cell -> if (r == row && c == col) player else cell }
                }
                if (checkWinner(tempBoard).first == player) return Pair(row, col)
            }
        }
    }
    return null
}



fun checkWinner(board: List<List<String?>>): Pair<String?, List<Pair<Int, Int>>?> {
    // Define Winning Combinations
    val winningCombinations = listOf(
        // Rows
        listOf(0 to 0, 0 to 1, 0 to 2),
        listOf(1 to 0, 1 to 1, 1 to 2),
        listOf(2 to 0, 2 to 1, 2 to 2),
        // Columns
        listOf(0 to 0, 1 to 0, 2 to 0),
        listOf(0 to 1, 1 to 1, 2 to 1),
        listOf(0 to 2, 1 to 2, 2 to 2),
        // Diagonals
        listOf(0 to 0, 1 to 1, 2 to 2),
        listOf(0 to 2, 1 to 1, 2 to 0)
    )

    // Check Each Winning Combination
    for (combination in winningCombinations) {
        val (row1, col1) = combination[0]
        val (row2, col2) = combination[1]
        val (row3, col3) = combination[2]

        // Check if the Combination Forms a Winning Line
        val symbol = board[row1][col1]
        if (symbol != null && symbol == board[row2][col2] && symbol == board[row3][col3]) {
            // Return winner and the winning positions
            return symbol to combination
        }
    }

    // Return No Winner if No Winning Line is Found
    return null to null
}

// Minimax Algorithm
fun minimax(board: List<List<String?>>, depth: Int, isMaximizing: Boolean): Pair<Int, Pair<Int, Int>> {
    val score = evaluateBoard(board)

    if (score != 0 || board.flatten().none { it == null }) {
        // No moves left or game over, return the score and no move
        return Pair(score, Pair(-1, -1))
    }

    var bestScore = if (isMaximizing) Int.MIN_VALUE else Int.MAX_VALUE
    var bestMove = Pair(-1, -1)

    for (r in board.indices) {
        for (c in board[r].indices) {
            if (board[r][c] == null) { // Empty cell
                val newBoard = board.mapIndexed { i, row ->
                    row.mapIndexed { j, cell ->
                        if (i == r && j == c) if (isMaximizing) "O" else "X" else cell
                    }
                }
                val moveScore = minimax(newBoard, depth + 1, !isMaximizing).first

                if (isMaximizing) {
                    if (moveScore > bestScore) {
                        bestScore = moveScore
                        bestMove = Pair(r, c)
                    }
                } else {
                    if (moveScore < bestScore) {
                        bestScore = moveScore
                        bestMove = Pair(r, c)
                    }
                }
            }
        }
    }

    return Pair(bestScore, bestMove)
}

fun evaluateBoard(board: List<List<String?>>): Int {
    val (winner, _) = checkWinner(board)
    return when {
        winner == "O" -> 1  // AI wins
        winner == "X" -> -1 // Player wins
        else -> 0          // Draw or ongoing
    }
}
