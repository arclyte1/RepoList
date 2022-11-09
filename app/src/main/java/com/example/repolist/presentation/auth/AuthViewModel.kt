package com.example.repolist.presentation.auth

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repolist.common.Resource
import com.example.repolist.domain.model.Tokens
import com.example.repolist.domain.usecase.GetAuthorizationIntentUseCase
import com.example.repolist.domain.usecase.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getAuthorizationIntentUseCase: GetAuthorizationIntentUseCase,
) : ViewModel() {

    val openAuthPageFlow = MutableSharedFlow<Intent>()

    val tokensFlow = MutableStateFlow<Tokens?>(null)

    val isLoading = MutableStateFlow(false)

    fun openLoginPage() {
        getAuthorizationIntentUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    isLoading.value = true
                }
                is Resource.Error -> {
                    isLoading.value = false
                }
                is Resource.Success -> {
                    isLoading.value = false
                    openAuthPageFlow.emit(result.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun handleAuthResponse(intent: Intent) {
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest =AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()

        if (exception == null && tokenExchangeRequest != null)
            getToken(tokenExchangeRequest)
    }

    private fun getToken(tokenRequest: TokenRequest) {
        getTokenUseCase(tokenRequest).onEach { result ->
            when (result) {
                null -> {
                    isLoading.value = true
                }
                else -> {
                    isLoading.value = false
                    tokensFlow.value = result.getOrNull()
                }
            }
        }.launchIn(viewModelScope)
    }

}
