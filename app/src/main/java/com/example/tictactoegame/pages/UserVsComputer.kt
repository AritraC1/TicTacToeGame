package com.example.tictactoegame.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoegame.functions.TicTacToeState
import com.example.tictactoegame.functions.uservsai.makeMoveAI
import com.example.tictactoegame.functions.uservsuser.makeMove
import com.example.tictactoegame.ui.theme.BgColor
import com.example.tictactoegame.ui.theme.BtnColor
import com.example.tictactoegame.ui.theme.TextColor
import kotlinx.coroutines.delay

@Composable
fun UserVsComputer() {
    // State Management with remember and mutableStateOf
    var gameState by remember { mutableStateOf(TicTacToeState()) }
    var showDialog by remember { mutableStateOf(false) }

    // Animation
    val animatedProgress by animateFloatAsState(
        targetValue = if (gameState.winner != null) 1f else 0f,
        animationSpec = tween(durationMillis = 500), label = "" // Speed of animation
    )

    LaunchedEffect(gameState.winner) {
        if (gameState.winner != null) {
            delay(1500)
            showDialog = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Heading
            Text(
                "Player vs Computer",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(30.dp))

            Box(modifier = Modifier.size(320.dp)) {
                Column {
                    gameState.board.forEachIndexed { rowIndex, row ->
                        Row {
                            row.forEachIndexed { colIndex, cell ->
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .background(BtnColor)
                                        .border(5.dp, BgColor)
                                        .clickable {
                                            // Only allow the player to move if the game is ongoing and it's their turn
                                            if (gameState.currentPlayer == "X" && gameState.winner == null) {
                                                gameState = makeMove(gameState, rowIndex, colIndex)

                                                // AI move after the player
                                                if (gameState.currentPlayer == "O" && gameState.winner == null) {
                                                    // AI's turn after player
                                                    gameState = makeMoveAI(gameState)
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        cell ?: "",
                                        style = MaterialTheme.typography.displayMedium,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // Draw the strike-through line when a player wins
                gameState.winningLine?.let { winningCells ->
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val first = winningCells.first()
                        val last = winningCells.last()

                        val cellSize = size.width / 3f // Assuming square board

                        val startX = first.second * cellSize + cellSize / 2
                        val startY = first.first * cellSize + cellSize / 2
                        val endX = last.second * cellSize + cellSize / 2
                        val endY = last.first * cellSize + cellSize / 2

                        drawLine(
                            color = Color.White,
                            start = Offset(startX, startY),
                            end = Offset(
                                endX * animatedProgress,
                                endY * animatedProgress
                            ), // Animate from start to end
                            strokeWidth = 10f,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showDialog) {
                gameState.winner?.let {
                    AlertDialog(
                        containerColor = BtnColor,
                        textContentColor = Color.White,
                        onDismissRequest = {
                            gameState = TicTacToeState()
                            showDialog = false  // Close dialog
                        },
                        title = { Text("Game Over!") },
                        titleContentColor = Color.White,
                        text = {
                            Column {
                                Text(
                                    text = if (it == "Draw") "It's a Draw!" else "Winner: $it",
                                    color = if (it == "Draw") TextColor else TextColor,
                                    fontSize = 24.sp
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    gameState = TicTacToeState()
                                    showDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                            ) {
                                Text("Restart Game", color = BgColor)
                            }
                        }
                    )
                }
            }
        }
    }
}