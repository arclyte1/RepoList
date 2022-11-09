package com.example.repolist.domain.model

data class Tokens(
    val tokenType: String,
    val accessToken: String,
    val refreshToken: String,
)
