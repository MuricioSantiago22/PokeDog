package com.example.pokedog.presentation.userDogList

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedog.components.CollectionDogGridItem
import com.example.pokedog.components.DogGridItem
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.domain.model.Dog
import com.example.pokedog.presentation.dogList.DogListViewModel
import com.example.pokedog.presentation.theme.PokeDogTheme
import com.example.pokedog.utils.GRID_SPAN_COUNT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDogListScreen(
    viewModel: DogListViewModel,
    onDogClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val dogList by viewModel.dogList.observeAsState(emptyList())
    val userDogList by viewModel.userDogList.observeAsState(emptyList())
    val status by viewModel.status.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserDogs()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi colección") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(GRID_SPAN_COUNT),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(dogList.size) { index ->
                    val dog = dogList[index]
                    val isInCollection = userDogList.any { it.id == dog.id }
                    CollectionDogGridItem(
                        dog = dog,
                        isInCollection = isInCollection,
                        onClick = { if (isInCollection) onDogClick(index) }
                    )
                }
            }

            when (status) {
                is ApiResponseStatus.Error -> {
                    val error = status as ApiResponseStatus.Error
                    Toast.makeText(context, error.messageId, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDogListScreenPreview() {
    PokeDogTheme {
        val fakeDogs = listOf(
            Dog(id = 1, index = 1, name = "Chihuahua", type = "Toy",
                weightFemale = "4 kg", weightMale = "5 kg",
                heightFemale = "25 cm", heightMale = "28 cm",
                lifeExpectancy = "12 - 15", temperament = "Juguetón", imageUrl = ""),
            Dog(id = 2, index = 2, name = "Labrador", type = "Sporting",
                weightFemale = "25 kg", weightMale = "30 kg",
                heightFemale = "55 cm", heightMale = "60 cm",
                lifeExpectancy = "10 - 12", temperament = "Amigable", imageUrl = "")
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(fakeDogs) { dog ->
                DogGridItem(dog = dog, onClick = {})
            }
        }
    }
}
