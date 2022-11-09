package com.example.repolist.presentation.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.repolist.R
import com.example.repolist.databinding.FragmentRepoListItemBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class RepoListItem(
    private val repoListItemVo: RepoListItemVo
) : AbstractBindingItem<FragmentRepoListItemBinding>() {

    override val type: Int
        get() = R.id.fragment_repo_list_item

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentRepoListItemBinding {
        return FragmentRepoListItemBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: FragmentRepoListItemBinding, payloads: List<Any>) {
        binding.name.text = repoListItemVo.name
    }
}