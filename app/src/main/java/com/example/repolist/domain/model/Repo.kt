package com.example.repolist.domain.model

import java.util.Date

data class Repo(
    val name: String,
    val private: Boolean,
    val language: String,
    val updatedAt: Date?,
)