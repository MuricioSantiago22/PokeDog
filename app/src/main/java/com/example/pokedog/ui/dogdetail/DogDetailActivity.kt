package com.example.pokedog.ui.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedog.Dog
import com.example.pokedog.databinding.ActivityDogDetailBinding
import com.example.pokedog.utils.DOG_KEY

class DogDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        if (dog == null){
            Toast.makeText(this, "Dog not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
    }
}