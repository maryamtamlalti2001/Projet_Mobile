package com.androiddevs.mvvmpostsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.androiddevs.mvvmpostsapp.R
import com.androiddevs.mvvmpostsapp.models.CreatePostData
import com.androiddevs.mvvmpostsapp.ui.PostsActivity
import com.androiddevs.mvvmpostsapp.ui.PostsViewModel
import kotlinx.android.synthetic.main.fragment_create_post.*

class CreatePostFragment :  Fragment(R.layout.fragment_create_post) {

    lateinit var viewModel: PostsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as PostsActivity).viewModel

        submitButton.setOnClickListener {

            var image = imageUrl.text.toString()
            var text = text.text.toString()
            var likes = likes.text.toString().toInt()
            var tags = tags.text.toString().split(", ") as List<String>
            var owner = owner.text.toString()

            // data à envoyer (instance de la data classe CreatePostData)
            val createPostData = CreatePostData(owner, image, text, likes, tags)

            // appel de fonction pour poster
            viewModel.createPost(createPostData)

            // redirection au fragment principal
            findNavController().navigate(
                R.id.action_createPostFragment_to_mainFragment
            )

        }


    }

}