package com.example.pokedog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedog.ui.auth.LoginScreen
import com.example.pokedog.ui.auth.RegisterScreen
import com.example.pokedog.ui.dogDetail.DogDetailScreen
import com.example.pokedog.ui.dogList.DogListScreen
import com.example.pokedog.ui.dogList.DogListViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val dogListViewModel: DogListViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ) {
        composable(NavRoutes.Login.route) {
            LoginScreen(
                onLoginClick = { navController.navigate(NavRoutes.DogList.route) },
                onRegisterClick = { navController.navigate(NavRoutes.Register.route) }
            )
        }
        composable(NavRoutes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(NavRoutes.DogList.route) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.DogList.route) {
            DogListScreen(
                viewModel = dogListViewModel,
                onDogClick = { dogIndex ->
                    dogListViewModel.selectDog(dogIndex)
                    navController.navigate(NavRoutes.DogDetail.createRoute(dogIndex))
                }
            )
        }
        composable(
            route = NavRoutes.DogDetail.route,
            arguments = listOf(navArgument("dogIndex") { type = NavType.IntType })
        ) {
            DogDetailScreen(
                viewModel = dogListViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}