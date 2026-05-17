package com.example.pokedog.ui.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedog.api.ApiResponseStatus
import com.example.pokedog.databinding.ActivityDogListBinding
import com.example.pokedog.ui.dogdetail.DogDetailActivity
import com.example.pokedog.utils.DOG_KEY
import com.example.pokedog.utils.GRID_SPAN_COUNT


class DogListActivity : FragmentActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loadingP= binding.pB
        val recycler = binding.dogRecycler
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
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

        dogListViewModel.status.observe(this){
            status ->
            when(status){
                is ApiResponseStatus.Error -> {
                    loadingP.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> {
                    loadingP.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    loadingP.visibility = View.GONE
                }
            }
        }
    }
}