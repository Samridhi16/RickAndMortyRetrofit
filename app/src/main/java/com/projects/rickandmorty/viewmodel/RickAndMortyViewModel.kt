package com.projects.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.rickandmorty.model.Result
import com.projects.rickandmorty.repository.RickAndMortyRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


//Activity will observe this, so we will make our fetched data Live Data
class RickAndMortyViewModel: ViewModel() {

    val repository = RickAndMortyRepository()

    private val _characters = MutableLiveData<List<Result>>()
    val characters: LiveData<List<Result>> = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun getCharacters(page: Int){
        try{
            viewModelScope.launch {
                val response = repository.getCharacters(page)
                if(response.isSuccessful){
                    val result = response.body()?.results
                    _characters.postValue(result)
                }else{
                    _error.postValue("Error: ${response.message()}")
                }
            }
        }catch (e: Exception){
            _error.postValue("Failure:${e.message}")
        }
    }


}