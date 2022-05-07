package com.androiddevs.mvvmpostsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmpostsapp.repository.PostsRepository

class PostsViewModelProviderFactory(
    val postsRepository: PostsRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsViewModel(postsRepository) as T
    }
}