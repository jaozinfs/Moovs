package com.jaozinfs.paging.tvs.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.ui.adapter.TvsCarouselAdapter
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import kotlinx.android.synthetic.main.fragment_tvs_category.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentTvsCategory : Fragment(R.layout.fragment_tvs_category) {
    private val tvsViewModel: TvsViewModel by viewModel()
    private val adapter = TvsCarouselAdapter {
        findNavController().navigate(FragmentTvsCategoryDirections.actionNavTvsCategoryToNavTvDetails(it))
    }
    private val args: FragmentTvsCategoryArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        lifecycleScope.launch {
            tvsViewModel.getTvsByCategory(args.category).collect {
                adapter.submitList(it)
            }
        }
    }

    private fun setupAdapter() {
        with(tvs_category_rv) {
            layoutManager = GridLayoutManager(context, 3)
            adapter = this@FragmentTvsCategory.adapter
        }
    }
}