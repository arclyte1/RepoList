package com.example.repolist.domain.usecase

import com.example.repolist.domain.model.Tokens
import com.example.repolist.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(tokenRequest: TokenRequest): MutableStateFlow<Result<Tokens>?> {
        return repository.getToken(tokenRequest)
    }
}