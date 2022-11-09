package com.example.repolist.presentation.repolist

import android.content.Context
import android.content.res.Resources
import com.example.repolist.R
import com.example.repolist.common.LanguageColors
import com.example.repolist.domain.model.Repo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Month
import java.time.format.TextStyle
import java.util.*
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
            val colorId = LanguageColors.getColorRes(repo.language)
            val color = colorId?.let { resources.getColor(it) }
            var date = ""
            if (repo.updatedAt != null) {
                val day = repo.updatedAt.dayOfMonth
                val month = Month.of(repo.updatedAt.monthOfYear).getDisplayName(TextStyle.SHORT, Locale.US)
                val year = repo.updatedAt.year
                date = resources.getString(R.string.updated_on_date, month, day, year)
            }
            RepoListItemVo(
                name = repo.name,
                visibility = if (repo.private) resources.getString(R.string.repo_visibility_private)
                    else resources.getString(R.string.repo_visibility_public),
                language = repo.language ?: "",
                languageColor = color,
                updatedOn = date,
                url = repo.url
            )
        }
    }

}