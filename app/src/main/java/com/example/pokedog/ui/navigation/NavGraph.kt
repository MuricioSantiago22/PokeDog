package com.example.pokedog.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.ui.auth.AuthViewModel
import com.example.pokedog.ui.auth.LoginScreen
import com.example.pokedog.ui.auth.RegisterScreen
import com.example.pokedog.ui.dogDetail.DogDetailScreen
import com.example.pokedog.ui.dogList.DogListScreen
import com.example.pokedog.ui.dogList.DogListViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val dogListViewModel: DogListViewModel = viewModel()
    val authStatus by authViewModel.status.observeAsState()
    val dogStatus by dogListViewModel.status.observeAsState()

    val isLoading = authStatus is ApiResponseStatus.Loading ||
            dogStatus is ApiResponseStatus.Loading

    Box(modifier = Modifier.fillMaxSize()) {
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
                    viewModel = authViewModel,
                    onRegisterSuccess = { navController.navigate(NavRoutes.DogList.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(NavRoutes.DogList.route) {
                DogListScreen(
                    viewModel = dogListViewModel,
                    onDogClick = { index ->
                        dogListViewModel.selectDog(index)
                        navController.navigate(NavRoutes.DogDetail.createRoute(index))
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

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}