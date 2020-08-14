package com.jaozinfs.moovs.tvs.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.jaozinfs.moovs.tvs.R
import com.jaozinfs.moovs.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.tvs.ui.adapter.TvsCarouselAdapter
import com.jaozinfs.moovs.tvs.ui.viewmodels.TvsViewModel
import kotlinx.android.synthetic.main.fragment_tvs_category.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentTvsCategory : Fragment(R.layout.fragment_tvs_category) {
    private val tvsViewModel: TvsViewModel by viewModel()
    private val adapter = TvsCarouselAdapter {
        findNavController().navigate(
            FragmentTvsCategoryDirections.actionNavTvsCategoryToNavTvDetails(
                it
            )
        )
    }
    private val args: FragmentTvsCategoryArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        lifecycleScope.launch {
            tvsViewModel.getTvsByCategory(args.category).collect {
                adapter.submitList(it)
                tvs_category_rv.scheduleLayoutAnimation()
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