package com.example.repolist.domain.usecase

import android.util.Log
import com.example.repolist.common.Resource
import com.example.repolist.domain.repository.RepoListRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepoListUseCase @Inject constructor(
    private val repoListRepository: RepoListRepository
) {

    operator fun invoke(authorizationToken: String) = flow {
        try {
            emit(Resource.Loading())
            val response = repoListRepository.getRepoList(authorizationToken)
            emit(Resource.Success(response))
        } catch (e : java.lang.Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

}