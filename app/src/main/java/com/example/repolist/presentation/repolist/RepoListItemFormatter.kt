package com.example.repolist.presentation.repolist

import android.content.Context
import android.content.res.Resources
import com.example.repolist.domain.model.Repo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RepoListItemFormatter @Inject constructor(
    @ApplicationContext context: Context
) {

    private val resources: Resources

    init {
        resources = context.resources
    }

    fun format(repos: List<Repo>): List<RepoListItemVo> {
        return repos.map { repo ->
            RepoListItemVo(
               repo.name,
               repo.private.toString(),
               repo.language,
               "",
               repo.updatedAt.toString(),
            )
        }
    }

}