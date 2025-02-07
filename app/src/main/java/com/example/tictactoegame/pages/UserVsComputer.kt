package com.example.tictactoegame.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tictactoegame.ui.theme.BgColor

@Composable
fun UserVsComputer(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(12.dp),
    )
}