package com.projects.rickandmorty.util


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.projects.rickandmorty.databinding.ActivityItemBinding
import com.projects.rickandmorty.model.Result
import com.squareup.picasso.Picasso

class CharacterAdapter(private var list:List<Result> ):
    RecyclerView.Adapter<CharacterAdapter.ResultViewHolder>() {
        
        private val differCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
               return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this,differCallback)

    inner class ResultViewHolder(val binding: ActivityItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ActivityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        var currentItem = differ.currentList[position]

        holder.binding.tvName.text = currentItem.name
        holder.binding.tvStatus.text = currentItem.status
        holder.binding.tvType.text = currentItem.type.ifEmpty { "Unknown Type" }
        holder.binding.tvLocation.text = currentItem.location.name

        Picasso.get().load(currentItem.image).into(holder.binding.image)
    }

    fun updateList(characters: List<Result>){
        differ.submitList(characters)
        //list = characters
        //this will refresh the entire recyclerview for even a single change
        //hence using diffutil
        //notifyDataSetChanged()
    }
}