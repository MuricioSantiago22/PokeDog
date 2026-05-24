package com.example.pokedog.presentation.dogDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedog.domain.model.Dog
import com.example.pokedog.presentation.dogList.DogListViewModel
import com.example.pokedog.presentation.theme.PokeDogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(
    viewModel: DogListViewModel,
    onBackClick: () -> Unit
) {
    val dog by viewModel.selectedDog.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(dog?.name ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        dog?.let {
            DogDetailContent(dog = it, paddingValues = paddingValues)
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DogDetailContent(dog: Dog, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = dog.imageUrl,
            contentDescription = dog.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = dog.name ?: "Sin nombre",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            DogDetailRow(label = "Tipo", value = dog.type)
            DogDetailRow(label = "Peso hembra", value = dog.weightFemale)
            DogDetailRow(label = "Peso macho", value = dog.weightMale)
            DogDetailRow(label = "Altura hembra", value = dog.heightFemale)
            DogDetailRow(label = "Altura macho", value = dog.heightMale)
            DogDetailRow(label = "Expectativa de vida", value = dog.lifeExpectancy)
            DogDetailRow(label = "Temperamento", value = dog.temperament)
        }
    }
}

@Composable
fun DogDetailRow(label: String, value: String?) {
    if (value != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun DogDetailScreenPreview() {
    PokeDogTheme {
        DogDetailContent(
            dog = Dog(id = 1, index = 1, name = "Golden Retriever", type = "Sporting",
                weightFemale = "25 kg", weightMale = "30 kg", heightFemale = "55 cm",
                heightMale = "60 cm", lifeExpectancy = "10 - 12",
                temperament = "Amigable, Confiable, Juguetón", imageUrl = ""),
            paddingValues = PaddingValues(0.dp)
        )
    }
}