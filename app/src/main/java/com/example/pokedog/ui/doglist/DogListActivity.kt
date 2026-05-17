package com.example.pokedog.ui.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedog.databinding.ActivityDogListBinding
import com.example.pokedog.ui.dogdetail.DogDetailActivity
import com.example.pokedog.utils.DOG_KEY


class DogListActivity : FragmentActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recycler = binding.dogRecycler
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = DogAdapter()
        adapter.setOnClickListener{
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }
        recycler.adapter = adapter
        dogListViewModel.dogList.observe(this){
            dogList ->
            adapter.submitList(dogList)
        }

    }

}