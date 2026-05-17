package com.example.pokedog.ui.doglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedog.databinding.DogListItemBinding
import com.example.pokedog.Dog

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Dog>(){
        override fun areItemsTheSame(
            p0: Dog,
            p1: Dog
        ): Boolean {
            return p0 == p1
        }

        override fun areContentsTheSame(
            p0: Dog,
            p1: Dog
        ): Boolean {
            return p0.id == p1.id
        }

    }

    private var onItemClickListener: ((Dog) -> Unit)? = null

    fun setOnClickListener(onItemClickListener: (Dog) -> Unit){
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DogViewHolder {
        val binding = DogListItemBinding
            .inflate(LayoutInflater.from(p0.context), p0, false)
        return DogViewHolder(binding)

    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, p1: Int) {
        val dog = getItem(p1)
        dogViewHolder.bind(dog)
    }

    inner class DogViewHolder(val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) {
           binding.dogName.text = dog.name ?: "Sin nombre"
            binding.dogName.setOnClickListener {
                onItemClickListener?.invoke(dog)
            }

        }
    }
}