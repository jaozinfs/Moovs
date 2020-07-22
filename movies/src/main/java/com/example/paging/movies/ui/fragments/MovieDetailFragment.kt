package com.example.paging.movies.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.paging.data.repository.local.entities.MovieEntity
import com.example.paging.data.repository.network.NetworkRepositoryRequest
import com.example.paging.movies.R
import com.example.paging.ui.loadImageUrl
import kotlinx.android.synthetic.main.fragment_movie_details.*

inline fun <reified T> Fragment.argument(key: String, crossinline default: () -> T?): Lazy<T?> =
    lazy {
        val value = arguments?.get(key)
        if (value is T) value else default()
    }

class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {
    companion object {

        fun getBundle(movieEntity: MovieEntity): Bundle = Bundle().apply {
            putSerializable(MOVIE_ARG, movieEntity)
        }

        const val MOVIE_ARG = "MOVIES_ARG"
    }

    val movieEntity by argument<MovieEntity?>(MOVIE_ARG) { null }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri = Uri.parse(NetworkRepositoryRequest.BASE_BACKDROP_IMAGE_PATTER)
            .buildUpon()
            .appendEncodedPath(movieEntity?.backdrop_path)
            .build()

        banner loadImageUrl uri
        movie_name.text = movieEntity?.title
    }

}