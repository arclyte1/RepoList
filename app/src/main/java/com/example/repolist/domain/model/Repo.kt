package com.example.repolist.domain.model

import org.joda.time.DateTime

data class Repo(
    val name: String,
    val private: Boolean,
    val language: String?,
    val updatedAt: DateTime?,
    val url: String,
)