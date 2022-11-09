package com.example.repolist.presentation.repolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repolist.common.Resource
import com.example.repolist.domain.usecase.GetRepoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repoListItemFormatter: RepoListItemFormatter,
    private val getRepoListUseCase: GetRepoListUseCase,
) : ViewModel(){

    var authorizationToken: String? = null

    val isLoading = MutableStateFlow(false)

    val repoListStateFlow = MutableStateFlow<List<RepoListItemVo>>(emptyList())

    fun getRepoList() {
        authorizationToken?.let { token ->
            getRepoListUseCase(token).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Error -> {
                        isLoading.value = false
                        Log.e("getRepoList", result.message ?: "Unexpected error")
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        repoListStateFlow.value = repoListItemFormatter.format(result.data.orEmpty())
                        Log.d("getRepoList", result.data.toString())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}