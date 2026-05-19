package com.example.pokedog.ui.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.example.pokedog.ui.navigation.NavGraph
import com.example.pokedog.ui.theme.PokeDogTheme

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeDogTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}