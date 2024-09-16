package com.projects.rickandmorty.remote


import com.projects.rickandmorty.model.CharactersResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character/")
    suspend fun getCharacters(@Query("page")page: Int) : Response<CharactersResponse>

}