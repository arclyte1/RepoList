package com.example.repolist.presentation.repolist

data class RepoListItemVo(
    val name: String,
    val visibility: String,
    val language: String,
    val languageColor: Int?,
    val updatedOn: String,
    val url: String,
)