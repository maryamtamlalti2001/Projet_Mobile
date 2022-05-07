package com.androiddevs.mvvmpostsapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmpostsapp.repository.PostsRepository
import com.androiddevs.mvvmpostsapp.ui.PostsViewModel
import com.androiddevs.mvvmpostsapp.ui.PostsViewModelProviderFactory

// deleting post with deletePost method

class DeletePostActivity : AppCompatActivity() {
    //init
    private lateinit var viewModel: PostsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_post)
        // activity on intent
        incomingIntent
    }
    private val incomingIntent: Unit
        private get() {
            if (intent.hasExtra("post_id")) {

                // collecting data from longClicked post
                val postId = intent.getStringExtra("post_id")

                // init warning message
                val warning: TextView = findViewById<View>(R.id.successText) as TextView
                val warningText = "Post with id : $postId has been deleted"

                // sending post delete warning
                warning.setText(warningText)

                // forward
                if (postId != null) {
                    delete(postId)
                }
            }
        }


    private fun delete(postId: String) {
        // forward with deletePost
        val repository = PostsRepository()
        val viewModelFactory = PostsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostsViewModel::class.java)
        viewModel.deletePost(postId)
    }
}