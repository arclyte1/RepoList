package com.example.repolist.data.repository

import android.util.Log
import com.example.repolist.common.Constants
import com.example.repolist.data.remote.GithubApi
import com.example.repolist.domain.model.Repo
import com.example.repolist.domain.repository.RepoListRepository
import javax.inject.Inject

class RepoListRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : RepoListRepository {

    override fun getRepoList(authorizationToken: String): List<Repo> {
        val response = api.getRepoList(
            authorization = authorizationToken,
            sort = Constants.GUTHUB_API_REPO_LIST_SORT,
            perPage = Constants.GITHUB_API_REPO_LIST_LIMIT
        ).map { it.toRepo() }
        return response
    }

}