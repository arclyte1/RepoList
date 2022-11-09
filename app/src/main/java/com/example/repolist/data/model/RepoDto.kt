package com.example.repolist.data.model

import com.example.repolist.domain.model.Repo
import java.text.SimpleDateFormat
import java.util.*

data class RepoDto(
    val name: String,
    val private: Boolean,
    val language: String,
    val updated_at: String,
) {
    fun toRepo() = Repo(
        name = name,
        private = private,
        language = language,
        updatedAt = SimpleDateFormat("yyyy-MM-ddThh:mm:ssZ", Locale.ENGLISH)
            .parse(updated_at),
    )
}
