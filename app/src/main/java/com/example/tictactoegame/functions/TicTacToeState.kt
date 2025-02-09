package com.example.tictactoegame.functions

data class TicTacToeState(
    val board: List<List<String?>> = List(3) { List(3) { null } },
    val currentPlayer: String = "X",
    val winner: String? = null,
    val winningLine: List<Pair<Int, Int>>? = null,
    // New field to track the computer's move
    val computerMove: Pair<Int, Int>? = null
)
