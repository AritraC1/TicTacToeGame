package com.example.tictactoegame.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.tictactoegame.ui.theme.BtnColor

@Composable
fun ButtonWithIcon(text: String, icon: Painter, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(250.dp) // Adjust the width as needed
            .height(60.dp), // Adjust the height as needed
        colors = ButtonDefaults.buttonColors(containerColor = BtnColor) // Optional color customization
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // Icon before the text
            Image(
                painter = icon,
                contentDescription = text,
                modifier = Modifier.size(100.dp) // Adjust size as needed
            )
        }
    }
}