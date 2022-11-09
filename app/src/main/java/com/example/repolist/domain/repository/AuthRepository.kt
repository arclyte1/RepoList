package com.example.repolist.domain.repository

import android.content.Intent
import com.example.repolist.domain.model.Tokens
import kotlinx.coroutines.flow.MutableStateFlow
import net.openid.appauth.TokenRequest

interface AuthRepository {

    fun getAuthorizationIntent(): Intent

    fun getToken(tokenRequest: TokenRequest): MutableStateFlow<Result<Tokens>?>

}