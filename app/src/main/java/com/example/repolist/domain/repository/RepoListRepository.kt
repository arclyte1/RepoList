package com.example.repolist.domain.repository

import com.example.repolist.domain.model.Repo

interface RepoListRepository {

    fun getRepoList(authorizationToken: String): List<Repo>

}