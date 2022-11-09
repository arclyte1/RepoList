package com.example.repolist.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.repolist.R
import com.example.repolist.common.Constants
import com.example.repolist.databinding.FragmentAuthBinding
import com.example.repolist.domain.model.Tokens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment: Fragment(R.layout.fragment_auth) {

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var binding: FragmentAuthBinding

    private val getAuthResponse = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val dataIntent = result.data ?: return@registerForActivityResult
        Log.d("OAuth", dataIntent.toString())
        viewModel.handleAuthResponse(dataIntent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            viewModel.openAuthPageFlow.collect { intent ->
                getAuthResponse.launch(intent)
            }
        }

        binding.button.setOnClickListener {
            viewModel.openLoginPage()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.tokensFlow.collect { tokens ->
                if (tokens != null)
                    navigateToRepoList(tokens)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect { isLoading ->
                binding.loading.isVisible = isLoading
            }
        }
    }

    private fun navigateToRepoList(tokens: Tokens) {
        val bundle = bundleOf(
            Constants.TOKEN_TYPE_FIELD to tokens.tokenType,
            Constants.ACCESS_TOKEN_FIELD to tokens.accessToken
        )
        findNavController().navigate(R.id.action_authFragment_to_repoListFragment, bundle)
    }
}