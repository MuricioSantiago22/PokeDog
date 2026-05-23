package com.example.pokedog.ui.auth

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.utils.AuthValidations

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val status by viewModel.status.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(status) {
        when (status) {
            is ApiResponseStatus.Success -> onRegisterSuccess()
            is ApiResponseStatus.Error -> {
                val error = status as ApiResponseStatus.Error
                Toast.makeText(context, error.messageId, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    RegisterScreenContent(
        name = name,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        nameError = nameError,
        emailError = emailError,
        passwordError = passwordError,
        confirmPasswordError = confirmPasswordError,
        isLoading = status is ApiResponseStatus.Loading,
        onNameChange = { name = it; nameError = AuthValidations.validateName(it) },
        onEmailChange = { email = it; emailError = AuthValidations.validateEmail(it) },
        onPasswordChange = { password = it; passwordError = AuthValidations.validatePassword(it) },
        onConfirmPasswordChange = { confirmPassword = it; confirmPasswordError = AuthValidations.validateConfirmPassword(password, it) },
        onRegisterClick = {
            emailError = AuthValidations.validateEmail(email)
            passwordError = AuthValidations.validatePassword(password)
            confirmPasswordError = AuthValidations.validateConfirmPassword(password, confirmPassword)
            if ( emailError == null &&
                passwordError == null && confirmPasswordError == null) {
                viewModel.signUp(email, password, confirmPassword)
            }
        },
        onBackClick = onBackClick,
        passwordVisible = passwordVisible,
        confirmPasswordVisible = confirmPasswordVisible,
        onPasswordVisibilityClick = { passwordVisible = !passwordVisible },
        onConfirmPasswordVisibilityClick = { confirmPasswordVisible = !confirmPasswordVisible },
    )
}