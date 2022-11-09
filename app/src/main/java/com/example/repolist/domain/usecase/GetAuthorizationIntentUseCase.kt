package com.example.repolist.domain.usecase

import com.example.repolist.common.Resource
import com.example.repolist.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetAuthorizationIntentUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getAuthorizationIntent()
            emit(Resource.Success(response))
        } catch (e : Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

}