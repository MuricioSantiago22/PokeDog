package com.example.pokedog.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedog.ui.theme.PokeDogTheme

@Composable
fun AppDialog(
    title: String,
    message: String,
    confirmText: String = "Aceptar",
    dismissText: String = "Cancelar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppDialogPreview() {
    PokeDogTheme {
        AppDialog(
            title = "Cerrar sesión",
            message = "¿Estás seguro que deseas cerrar sesión?",
            confirmText = "Sí, salir",
            dismissText = "Cancelar",
            onConfirm = {},
            onDismiss = {}
        )
    }
}