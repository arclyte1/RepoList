package com.example.repolist.data.repository

import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.example.repolist.domain.model.Tokens
import kotlinx.coroutines.flow.MutableStateFlow
import net.openid.appauth.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthorizationService
) {

    val tokensStateFlow = MutableStateFlow<Result<Tokens>?>(null)

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        AuthConfig.AUTH_URI.toUri(),
        AuthConfig.TOKEN_URI.toUri(),
        null,
        AuthConfig.END_SESSION_URI.toUri()
    )

    fun getAuthorizationIntent(): Intent {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val authRequest = AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            AuthConfig.CALLBACK_URL.toUri()
        )
            .setScope(AuthConfig.SCOPE)
            .build()

        return authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
    }

    fun getToken(tokenRequest: TokenRequest) {
        tokensStateFlow.value = null

        authService.performTokenRequest(
            tokenRequest,
            ClientSecretPost(AuthConfig.CLIENT_SECRET)
        ) { response, exception ->
            when {
                response != null -> {
                    Log.d("Oauth", response.jsonSerializeString())
                    val tokens = Tokens(
                        tokenType = response.tokenType.orEmpty(),
                        accessToken = response.accessToken.orEmpty(),
                        refreshToken = response.refreshToken.orEmpty(),
                    )
                    tokensStateFlow.value = Result.success(tokens)
                }
                exception != null -> {
                    tokensStateFlow.value = Result.failure(exception)
                }
                else -> error("unreachable")
            }
        }
    }

    private object AuthConfig {
        const val AUTH_URI = "https://github.com/login/oauth/authorize"
        const val TOKEN_URI = "https://github.com/login/oauth/access_token"
        const val END_SESSION_URI = "https://github.com/logout"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE = "user,repo"
        const val CLIENT_ID = "d6997366eaa44c4e3cce"
        const val CLIENT_SECRET = "26ec3947d1bdd3a5dfa54f993220ef7ecd54a522"
        const val CALLBACK_URL = "com.example.oauth://github.com/callback"
        const val LOGOUT_CALLBACK_URL = "com.example.oauth://github.com/logout_callback"
    }

}