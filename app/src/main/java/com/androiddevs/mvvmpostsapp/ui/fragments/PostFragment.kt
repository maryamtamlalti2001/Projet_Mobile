package com.androiddevs.mvvmpostsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmpostsapp.R
import com.androiddevs.mvvmpostsapp.ui.PostsActivity
import com.androiddevs.mvvmpostsapp.ui.PostsViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment(R.layout.fragment_post) {

    lateinit var viewModel: PostsViewModel
    val args : PostFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as PostsActivity).viewModel
        val post = args.post
        Glide.with(this).load(post.image).into(PostImage2)
        Glide.with(this).load(post.owner?.picture).into(userphoto2)
        description2.text= post.text
        fullName2.text=post.owner?.title +". "+post.owner?.firstName+" "+post.owner?.lastName
        publishDate2.text=post.publishDate
        tag11.text = post.tags?.get(0)
        tag22.text = post.tags?.get(1)
        tag33.text = post.tags?.get(2)

        }



}
