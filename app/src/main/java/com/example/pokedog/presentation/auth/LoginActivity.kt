package com.example.pokedog.presentation.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.example.pokedog.presentation.navigation.NavGraph
import com.example.pokedog.presentation.navigation.NavRoutes
import com.example.pokedog.presentation.theme.PokeDogTheme
import com.example.pokedog.utils.SecurePreferences

class LoginActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val savedToken = SecurePreferences.getAuthToken(this)
        setContent {
            PokeDogTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    authViewModel = authViewModel,
                    startDestination = if (savedToken.isNotEmpty())
                        NavRoutes.DogList.route
                    else
                        NavRoutes.Login.route
                )
            }
        }
    }
}