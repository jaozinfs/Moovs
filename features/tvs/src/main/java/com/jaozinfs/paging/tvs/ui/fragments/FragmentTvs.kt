package com.jaozinfs.paging.tvs.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.di.tvsFeatureDI
import com.jaozinfs.paging.tvs.domain.model.TvUI
import com.jaozinfs.paging.tvs.ui.adapter.TvsCarouselAdapter
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import kotlinx.android.synthetic.main.fragment_tvs.*
import kotlinx.android.synthetic.main.layout_tv_favorite_category.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.koinApplication

class FragmentTvs : Fragment(R.layout.fragment_tvs) {
    private val tvsViewModel: TvsViewModel by viewModel()

    private val tvsCarouselAdapter = TvsCarouselAdapter {
        findNavController().navigate(FragmentTvsDirections.actionNavTvsToNavTvDetails(it))
    }
    private val tvsOnAirAdapter = TvsCarouselAdapter {
        findNavController().navigate(FragmentTvsDirections.actionNavTvsToNavTvDetails(it))
    }
    private val tvsFavoritesAdapter = TvsCarouselAdapter {
        findNavController().navigate(FragmentTvsDirections.actionNavTvsToNavTvDetails(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        koinApplication {
            unloadKoinModules(tvsFeatureDI)
            loadKoinModules(tvsFeatureDI)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListeners()
        lifecycleScope.launch {
            tvsViewModel.getCategoriesCombined().collect {
                tvsCarouselAdapter.submitList(it.first)
                tvs_carousel_rv.scheduleLayoutAnimation()

                tvsOnAirAdapter.submitList(it.second)
                tvs_on_air_carousel_rv.scheduleLayoutAnimation()

                if (it.third.isNotEmpty())
                    showFavorites(it.third)

            }

        }

    }

    private fun setListeners() {
        see_more_popular.setOnClickListener {
            navigateToCategory(TvsViewModel.POPULAR)
        }
        see_more_on_air.setOnClickListener {
            navigateToCategory(TvsViewModel.ON_AIR)
        }
        see_more_favorited.setOnClickListener {
            navigateToCategory(TvsViewModel.FAVORITED)
        }
    }

    private fun navigateToCategory(categoryName: String) {
        findNavController().navigate(
            FragmentTvsDirections.actionNavTvsToNavTvsCategory(categoryName)
        )
    }

    private fun setAdapter() {
        tvs_carousel_rv.adapter = tvsCarouselAdapter
        tvs_on_air_carousel_rv.adapter = tvsOnAirAdapter
        tvs_favorited_rv.adapter = tvsFavoritesAdapter
    }

    private fun showFavorites(list: List<TvUI>) = with(favorite_items) {

        favorite_items.isVisible = true
        tvsFavoritesAdapter.submitList(list)
        tvs_favorited_rv.scheduleLayoutAnimation()

    }
}