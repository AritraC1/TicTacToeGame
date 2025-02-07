package com.example.tictactoegame.functions

fun makeMove(state: TicTacToeState, row: Int, col: Int): TicTacToeState {
    if (state.board[row][col] != null || state.winner != null) return state // Ignore if cell is occupied or game is over

    val newBoard = state.board.mapIndexed { r, rowList ->
        rowList.mapIndexed { c, cell -> if (r == row && c == col) state.currentPlayer else cell }
    }

    val (winner, winningLine) = checkWinner(newBoard)

    return state.copy(
        board = newBoard,
        currentPlayer = if (winner == null) if (state.currentPlayer == "X") "O" else "X" else state.currentPlayer,
        winner = winner,
        winningLine = winningLine
    )
}

fun checkWinner(board: List<List<String?>>): Pair<String?, List<Pair<Int, Int>>?> {
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

    for (combination in winningCombinations) {
        val (row1, col1) = combination[0]
        val (row2, col2) = combination[1]
        val (row3, col3) = combination[2]

        val symbol = board[row1][col1]
        if (symbol != null && symbol == board[row2][col2] && symbol == board[row3][col3]) {
            return symbol to combination // Return winner and the winning positions
        }
    }

    return null to null
}

