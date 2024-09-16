package com.projects.rickandmorty.model

data class CharactersResponse(
    val info: Info,
    val results: List<Result>
)