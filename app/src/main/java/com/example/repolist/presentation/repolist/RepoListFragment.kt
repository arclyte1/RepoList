package com.example.repolist.presentation.repolist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.repolist.R
import com.example.repolist.common.Constants
import com.example.repolist.databinding.FragmentRepoListBinding
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    private lateinit var binding: FragmentRepoListBinding
    private val viewModel: RepoListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepoListBinding.bind(view)
        setUpView()
    }

    private fun setUpView() {
        val tokenType = arguments?.getString(Constants.TOKEN_TYPE_FIELD)
        val accessToken = arguments?.getString(Constants.ACCESS_TOKEN_FIELD)
        if (tokenType == "bearer")
            viewModel.authorizationToken = "Bearer $accessToken"

        val repoItemAdapter = ItemAdapter<RepoListItem>()
        val repoFastAdapter = FastAdapter.with(repoItemAdapter)
        binding.list.adapter = repoFastAdapter

        lifecycleScope.launchWhenCreated {
            viewModel.repoListStateFlow.collect { repos ->
                repoItemAdapter.set(repos.map {
                    RepoListItem(it)
                })
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.isLoading.collect { isLoading ->
                binding.loading.isVisible = isLoading
            }
        }

        viewModel.getRepoList()
    }
}