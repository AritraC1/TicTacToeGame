package com.example.tictactoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoegame.ui.theme.TicTacToeGameTheme
import com.example.tictactoegame.pages.HomePage
import com.example.tictactoegame.pages.UserVsUser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeGameTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomePage") {
         composable("homepage") {
             HomePage {
                 navController.navigate("uservsuserpage")
             }
         }
         composable(route = "uservsuserpage"){
             UserVsUser()
         }
    }
}