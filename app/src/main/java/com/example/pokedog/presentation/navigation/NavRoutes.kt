package com.example.pokedog.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("login")
    object Register : NavRoutes("register")
    object DogList : NavRoutes("dogList")
    object DogDetail : NavRoutes("dogDetail/{dogIndex}") {
        fun createRoute(dogId: Int) = "dogDetail/$dogId"
    }
}