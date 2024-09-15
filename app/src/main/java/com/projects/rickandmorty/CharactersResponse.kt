package com.projects.rickandmorty

data class CharactersResponse(
    val info: Info,
    val results: List<Result>
)