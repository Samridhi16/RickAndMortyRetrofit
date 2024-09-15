package com.projects.rickandmorty


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.rickandmorty.databinding.ActivityItemBinding
import com.squareup.picasso.Picasso

class CharacterAdapter(private val list:List<Result> ):
    RecyclerView.Adapter<CharacterAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(val binding: ActivityItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ActivityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        var currentItem = list[position]

        holder.binding.tvName.text = currentItem.name
        holder.binding.tvType.text = currentItem.type.ifEmpty { "Unknown Type" }
        holder.binding.tvLocation.text = currentItem.location.name

        Picasso.get().load(currentItem.image).into(holder.binding.image)
    }
}