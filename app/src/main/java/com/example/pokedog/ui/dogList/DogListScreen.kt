package com.example.pokedog.ui.dogList

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokedog.data.remote.api.ApiResponseStatus
import com.example.pokedog.ui.theme.PokeDogTheme
import com.example.pokedog.utils.GRID_SPAN_COUNT
import androidx.compose.foundation.lazy.grid.items
import com.example.pokedog.domain.model.Dog


@Composable
fun DogListScreen(
    viewModel: DogListViewModel = viewModel(),
    onDogClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val dogList by viewModel.dogList.observeAsState(emptyList())
    val status by viewModel.status.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(dogList.size) { index ->
                DogGridItem(
                    dog = dogList[index],
                    onClick = { onDogClick(index) }
                )
            }
        }

        when (status) {
            is ApiResponseStatus.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ApiResponseStatus.Error -> {
                val error = status as ApiResponseStatus.Error
                Toast.makeText(context, error.messageId, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}

@Composable
fun DogGridItem(dog: Dog, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = dog.imageUrl,
                contentDescription = dog.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dog.name ?: "Sin nombre",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DogListScreenPreview() {
    PokeDogTheme {
        val fakeDogs = listOf(
            Dog(id = 1, index = 1, name = "Chihuahua", type = "Toy",
                weightFemale = "4 kg", weightMale = "5 kg",
                heightFemale = "25 cm", heightMale = "28 cm",
                lifeExpectancy = "12 - 15", temperament = "Juguetón",
                imageUrl = ""),
            Dog(id = 2, index = 2, name = "Labrador", type = "Sporting",
                weightFemale = "25 kg", weightMale = "30 kg",
                heightFemale = "55 cm", heightMale = "60 cm",
                lifeExpectancy = "10 - 12", temperament = "Amigable",
                imageUrl = "")
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