package com.example.repolist.data.remote

import com.example.repolist.data.model.RepoDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubApi {

    @GET("/user/repos")
    fun getRepoList(
        @Header("Authorization") authorization: String,
        @Query("sort") sort: String,
        @Query("per_page") perPage: Int,
    ): List<RepoDto>

}