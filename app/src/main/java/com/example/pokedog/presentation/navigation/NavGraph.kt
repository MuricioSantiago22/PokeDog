package com.example.pokedog.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.pokedog.components.AppDialog
import com.example.pokedog.components.BottomNavItem
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.presentation.auth.AuthViewModel
import com.example.pokedog.presentation.auth.LoginScreen
import com.example.pokedog.presentation.auth.RegisterScreen
import com.example.pokedog.presentation.dogDetail.DogDetailScreen
import com.example.pokedog.presentation.dogList.DogListScreen
import com.example.pokedog.presentation.dogList.DogListViewModel
import com.example.pokedog.presentation.userDogList.UserDogListScreen
import androidx.compose.runtime.setValue

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    startDestination: String = NavRoutes.Login.route
) {
    val dogListViewModel: DogListViewModel = viewModel()
    val authStatus by authViewModel.status.observeAsState()
    val dogStatus by dogListViewModel.status.observeAsState()
    val addDogStatus by dogListViewModel.addDogStatus.observeAsState()

    val isLoading = authStatus is ApiResponseStatus.Loading ||
            dogStatus is ApiResponseStatus.Loading ||
            addDogStatus is ApiResponseStatus.Loading

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        NavRoutes.DogList.route,
        NavRoutes.UserDogList.route
    )

    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AppDialog(
            title = "Cerrar sesión",
            message = "¿Estás seguro que deseas cerrar sesión?",
            confirmText = "Sí, salir",
            dismissText = "Cancelar",
            onConfirm = {
                showLogoutDialog = false
                authViewModel.logout()
                navController.navigate(NavRoutes.Login.route) {
                    popUpTo(NavRoutes.Login.route) { inclusive = true }
                }
            },
            onDismiss = { showLogoutDialog = false }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        BottomNavItem(
                            selected = currentRoute == NavRoutes.DogList.route,
                            onClick = {
                                navController.navigate(NavRoutes.DogList.route) {
                                    popUpTo(NavRoutes.DogList.route) { inclusive = true }
                                }
                            },
                            icon = Icons.Default.List,
                            label = "Perros"
                        )
                        BottomNavItem(
                            selected = currentRoute == NavRoutes.UserDogList.route,
                            onClick = {
                                navController.navigate(NavRoutes.UserDogList.route) {
                                    popUpTo(NavRoutes.UserDogList.route) { inclusive = true }
                                }
                            },
                            icon = Icons.Default.Favorite,
                            label = "Colección"
                        )
                        BottomNavItem(
                            selected = false,
                            onClick = { showLogoutDialog = true },
                            icon = Icons.AutoMirrored.Filled.Logout,
                            label = "Salir"
                        )
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(NavRoutes.Login.route) {
                    LoginScreen(
                        viewModel = authViewModel,
                        onLoginSuccess = {
                            dogListViewModel.downloadDogs()
                            navController.navigate(NavRoutes.DogList.route)
                        },
                        onRegisterClick = { navController.navigate(NavRoutes.Register.route) }
                    )
                }
                composable(NavRoutes.Register.route) {
                    RegisterScreen(
                        viewModel = authViewModel,
                        onRegisterSuccess = {
                            dogListViewModel.downloadDogs()
                            navController.navigate(NavRoutes.DogList.route)
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable(NavRoutes.DogList.route) {
                    DogListScreen(
                        viewModel = dogListViewModel,
                        onDogClick = { index ->
                            dogListViewModel.selectDog(index)
                        },
                    )
                }
                composable(NavRoutes.UserDogList.route) {
                    UserDogListScreen(
                        viewModel = dogListViewModel,
                        onDogClick = { index ->
                            dogListViewModel.selectDog(index, fromUserList = true)
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