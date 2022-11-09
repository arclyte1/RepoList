package com.example.repolist.data.model

import com.example.repolist.domain.model.Repo
import org.joda.time.format.ISODateTimeFormat

data class RepoDto(
    val name: String,
    val private: Boolean,
    val language: String?,
    val pushed_at: String,
    val html_url: String,
) {
    fun toRepo() = Repo(
        name = name,
        private = private,
        language = language,
        updatedAt = ISODateTimeFormat.dateTimeParser().parseDateTime(pushed_at),
        url = html_url,
    )
}
