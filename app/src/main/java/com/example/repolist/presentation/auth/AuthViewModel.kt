package com.example.repolist.presentation.auth

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.repolist.App
import com.example.repolist.data.repository.AuthRepositoryImpl
import com.example.repolist.domain.model.Tokens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repositoryImpl: AuthRepositoryImpl
) : AndroidViewModel(context as Application) {

    val openAuthPageFlow = MutableSharedFlow<Intent>()

    val tokensFlow = MutableStateFlow<Tokens?>(null)

    fun openLoginPage() {
        viewModelScope.launch {
            val intent = repositoryImpl.getAuthorizationIntent()
            openAuthPageFlow.emit(intent)
        }
    }

    fun handleAuthResponse(intent: Intent) {
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest =AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()

        if (exception == null && tokenExchangeRequest != null)
            getToken(tokenExchangeRequest)
    }

    private fun getToken(tokenRequest: TokenRequest) {
        repositoryImpl.getToken(tokenRequest)
        viewModelScope.launch {
            repositoryImpl.tokensStateFlow.collect { result ->
                if (result != null && result.isSuccess) {
                    tokensFlow.value = result.getOrNull()
                    Log.d("OAuth", result.getOrNull().toString())
                }
            }
        }
    }

}
