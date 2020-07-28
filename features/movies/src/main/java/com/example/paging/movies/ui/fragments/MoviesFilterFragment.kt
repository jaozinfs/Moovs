package com.example.paging.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.paging.movies.R
import kotlinx.android.synthetic.main.fragment_movies_filter.*
import java.io.Serializable

class MoviesFilterFragment : Fragment(R.layout.fragment_movies_filter) {
    companion object {
        const val FILTER_OBJECT = "FLAG_FILTER"
    }

    data class FilterObject(val voteAvarege: Int) : Serializable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentVoteAvarege()
        bt_filter.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                FILTER_OBJECT,
                FilterObject(vote_avarege.text.toString().toInt())
            )
            findNavController().popBackStack()
        }

    }

    private fun setCurrentVoteAvarege() {
        findNavController().previousBackStackEntry?.savedStateHandle?.getLiveData<FilterObject>(
            FILTER_OBJECT
        )?.observe(viewLifecycleOwner, Observer {
            with(vote_avarege) {
                setText(it?.voteAvarege.toString())
                setSelection(it?.voteAvarege.toString().length)
            }
        })
    }
}