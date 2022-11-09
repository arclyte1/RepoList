package com.example.repolist.data.repository

import com.example.repolist.common.Constants
import com.example.repolist.data.remote.GithubApi
import com.example.repolist.domain.model.Repo
import com.example.repolist.domain.repository.RepoListRepository
import javax.inject.Inject

class RepoListRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : RepoListRepository {

    override suspend fun getRepoList(authorizationToken: String): List<Repo> {
        val response = api.getRepoList(
            authorization = authorizationToken,
            host = Constants.GITHUB_API_HOST,
            userAgent = Constants.USER_AGENT,
            sort = Constants.GITHUB_API_REPO_LIST_SORT,
            perPage = Constants.GITHUB_API_REPO_LIST_LIMIT,
            accept = Constants.GITHUB_API_ACCEPT,
        ).map { it.toRepo() }
        return response
    }

}