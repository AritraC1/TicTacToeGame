package com.example.tictactoegame.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoegame.functions.TicTacToeState
import com.example.tictactoegame.functions.makeMove
import com.example.tictactoegame.ui.theme.BgColor
import com.example.tictactoegame.ui.theme.BtnColor
import com.example.tictactoegame.ui.theme.TextColor
import kotlinx.coroutines.delay

@Composable
fun UserVsUser() {
    var gameState by remember { mutableStateOf(TicTacToeState()) }
    var showDialog by remember { mutableStateOf(false) }

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
            Spacer(modifier = Modifier.height(32.dp))

            // Heading
            Text(
                "Player vs Player",
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
                                            gameState = makeMove(gameState, rowIndex, colIndex)
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
