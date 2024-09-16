package com.projects.rickandmorty.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.rickandmorty.databinding.ActivityMainBinding
import com.projects.rickandmorty.util.CharacterAdapter
import com.projects.rickandmorty.viewmodel.RickAndMortyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: RickAndMortyViewModel
    private lateinit var characterAdapter: CharacterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ViewModel
        viewModel = ViewModelProvider(this).get(RickAndMortyViewModel::class.java)

        //Recycler View Set up
        binding.rvList.layoutManager = LinearLayoutManager(this)
        characterAdapter = CharacterAdapter(listOf())
        binding.rvList.adapter = characterAdapter


        //Observing view model
        viewModel.characters.observe(this) { characters ->
            characterAdapter.updateList(characters)
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
        }

        //fetch the characters
        viewModel.getCharacters(23)

    }
}