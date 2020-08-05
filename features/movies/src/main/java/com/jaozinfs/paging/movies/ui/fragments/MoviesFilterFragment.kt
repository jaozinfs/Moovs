package com.jaozinfs.paging.movies.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.jaozinfs.paging.movies.R
import com.jaozinfs.paging.movies.domain.movies.GenreUI
import com.jaozinfs.paging.movies.ui.MoviesViewModel
import com.jaozinfs.paging.movies.ui.utils.setChipsSelectedByIds
import kotlinx.android.synthetic.main.fragment_movies_filter.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.io.Serializable

class MoviesFilterFragment : Fragment(R.layout.fragment_movies_filter) {
    companion object {
        const val FILTER_OBJECT = "FLAG_FILTER"
    }

    data class FilterObject(
        val voteAvarege: String,
        val isAdult: Boolean,
        val genres: List<Int> = emptyList()
    ) : Serializable

    private val movieViewModel: MoviesViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentVoteAvarege()

        lifecycleScope.launchWhenCreated {
            movieViewModel.getGenresList().collectLatest { setGenres(it) }
        }

        bt_filter.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                FILTER_OBJECT,
                FilterObject(vote_avarege.text.toString(), adult_switch.isChecked, getChips())
            )
            findNavController().popBackStack()
        }

    }

    private fun setCurrentVoteAvarege() {
        findNavController().previousBackStackEntry?.savedStateHandle?.getLiveData<FilterObject>(
            FILTER_OBJECT
        )?.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            with(vote_avarege) {
                setText(it?.voteAvarege)
                setSelection(it?.voteAvarege.toString().length)
            }
            setChipsSelected(it.genres)
            adult_switch.isChecked = it.isAdult
        })
    }

    private fun getChips() = genres_chip_group.children.filter {
        (it as Chip).isChecked
    }.map {
        it.id
    }.toList()

    private fun setGenres(genres: List<GenreUI>) {
        genres_chip_group.removeAllViews()
        for (genre in genres) {
            (layoutInflater.inflate(
                com.jaozinfs.paging.R.layout.common_chip_view,
                genres_chip_group,
                false
            ) as? Chip)?.apply {
                text = genre.name
                id = genre.id
                genres_chip_group.addView(this)
            }

        }
    }

    private fun setChipsSelected(listSelected: List<Int>) {
        genres_chip_group.setChipsSelectedByIds(listSelected)
    }

    //inflate menu on fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movies_filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    //listener menu item click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.movies_filter_remove_filters ->
                closeAndRemoveFilter()
        }
        return true
    }

    private fun closeAndRemoveFilter() {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            FILTER_OBJECT, null)
        findNavController().popBackStack()
    }
}