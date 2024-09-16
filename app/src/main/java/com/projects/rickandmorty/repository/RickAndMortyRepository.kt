package com.projects.rickandmorty.repository

import com.projects.rickandmorty.model.CharactersResponse
import com.projects.rickandmorty.remote.RickAndMortyService
import com.projects.rickandmorty.remote.RetrofitHelper
import retrofit2.Response

//repository will have the reference of the remote calls
class RickAndMortyRepository {

    val retrofit = RetrofitHelper.getInstance()

    suspend fun getCharacters(page: Int):Response<CharactersResponse>{
        return retrofit.create(RickAndMortyService::class.java).getCharacters(page)
    }

}