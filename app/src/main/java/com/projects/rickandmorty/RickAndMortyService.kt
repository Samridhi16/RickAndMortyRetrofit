package com.projects.rickandmorty


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character/")
    fun getCharacters(@Query("page")page: Int) : Call<CharactersResponse>

}