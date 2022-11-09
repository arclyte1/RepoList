package com.example.repolist.di

import android.content.Context
import com.example.repolist.common.Constants
import com.example.repolist.data.remote.GithubApi
import com.example.repolist.data.repository.AuthRepositoryImpl
import com.example.repolist.data.repository.RepoListRepositoryImpl
import com.example.repolist.domain.repository.AuthRepository
import com.example.repolist.domain.repository.RepoListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuthService(
        @ApplicationContext context: Context
    ): AuthorizationService {
        return AuthorizationService(context)
    }

    @Provides
    @Singleton
    fun provideGithubApi(): GithubApi = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideRepoListRepository(
        api: GithubApi
    ) : RepoListRepository {
        return RepoListRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthorizationService
    ) : AuthRepository {
        return AuthRepositoryImpl(authService)
    }

}