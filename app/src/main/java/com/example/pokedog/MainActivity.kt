package com.example.pokedog

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.pokedog.ui.doglist.DogListActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, DogListActivity::class.java))
        finish()
    }
}