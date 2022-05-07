package com.androiddevs.mvvmpostsapp.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmpostsapp.R

import com.androiddevs.mvvmpostsapp.repository.PostsRepository
import kotlinx.android.synthetic.main.posts_activity.*


class PostsActivity : AppCompatActivity() {

    lateinit var viewModel: PostsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.posts_activity)


        val postsRepository =PostsRepository()
        val viewModelProviderFactory = PostsViewModelProviderFactory(postsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(PostsViewModel::class.java)
        bottomNavigationView.setupWithNavController(postsNavHostFragment.findNavController())



    }









}

