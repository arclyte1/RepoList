package com.example.repolist.presentation.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.repolist.R
import com.example.repolist.databinding.FragmentRepoListItemBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class RepoListItem(
    val repoListItemVo: RepoListItemVo
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
        binding.visibility.text = repoListItemVo.visibility
        binding.language.text = repoListItemVo.language
        binding.language.isVisible = repoListItemVo.language != ""
        binding.languageCircle.isVisible = repoListItemVo.languageColor != null
        if (repoListItemVo.languageColor != null)
            binding.languageCircle.setColorFilter(repoListItemVo.languageColor)
        binding.updatedOn.text = repoListItemVo.updatedOn
    }
}