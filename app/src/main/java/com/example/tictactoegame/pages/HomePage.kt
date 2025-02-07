package com.example.tictactoegame.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tictactoegame.R
import com.example.tictactoegame.components.ButtonWithIcon
import com.example.tictactoegame.ui.theme.BgColor
import com.example.tictactoegame.ui.theme.BtnColor
import com.example.tictactoegame.ui.theme.TextColor

@Composable
fun HomePage(onGameSelected: (String) -> Unit) {

    // State to control the dialog visibility
    var isDialogOpen by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(
                bottom = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding() + 8.dp,
                end = 12.dp
            ),
    ) {
        // Content in the center of the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header
            Text(
                "Tic-Tac-Toe",
                color = TextColor,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // User logo vs AI logo button
            ButtonWithIcon(
                text = "User vs AI",
                icon = painterResource(id = R.drawable.ic_user_vs_ai),
                onClick = {
                    Toast.makeText(context, "Player vs Computer Selected", Toast.LENGTH_SHORT).show()
                    onGameSelected("uservsaipage")
                }
            )

            Spacer(modifier = Modifier.height(27.dp))

            // User 1 vs User 2 button
            ButtonWithIcon(
                text = "User vs User",
                icon = painterResource(id = R.drawable.ic_user_vs_user),
                onClick = {
                    Toast.makeText(context, "Player vs Player Selected", Toast.LENGTH_SHORT).show()
                    onGameSelected("uservsuserpage")
                }
            )
        }

        // Floating Help button at the bottom-right corner
        Button(
            onClick = {
                isDialogOpen = true
            },
            modifier = Modifier
                .size(70.dp)
                .padding(6.dp)// Adds space between button and edges
                .align(Alignment.BottomEnd), // Positions button in the bottom-right corner
            shape = CircleShape, // Optional: Makes the button circular
            colors = ButtonDefaults.buttonColors(containerColor = BtnColor) // Optional color customization
        ) {
            Text(
                "?",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            ) // Text or Icon in the button
        }

        // Popup dialog when the button is clicked
        if (isDialogOpen) {
            Dialog(onDismissRequest = { isDialogOpen = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .padding(2.dp)
                        .background(BtnColor, shape = RoundedCornerShape(12.dp))
                ) {
                    IconButton(
                        onClick = { isDialogOpen = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(2.dp) // Padding for better positioning
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(27.dp))
                        Text(
                            text = "Rules",
                            color = TextColor,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "1. The goal is to place three marks (X or O) in a row, column, or diagonal to win a game",
                            color = TextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "2. Winning: A player wins if they have three marks in a row, column, or diagonal.",
                            color = TextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "3. Tie: If all cells are filled and no player has won, it's a tie.",
                            color = TextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}