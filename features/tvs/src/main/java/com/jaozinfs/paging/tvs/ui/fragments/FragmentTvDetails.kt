package com.jaozinfs.paging.tvs.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.jaozinfs.paging.extensions.loadImageCoil
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.ui.adapter.SeasonsAdapter
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import kotlinx.android.synthetic.main.fragment_tv_details.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentTvDetails : Fragment(R.layout.fragment_tv_details) {
    private val tvsViewModel: TvsViewModel by viewModel()
    private val args: FragmentTvDetailsArgs by navArgs()
    private val adapter = SeasonsAdapter {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        lifecycleScope.launch {
            tvsViewModel.getTvDetails(args.tvID).collect {
                constructDetails(it)
            }
        }
    }

    private fun constructDetails(tvDetailsUI: TvDetailsUI) {
        //load image
        val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
            .buildUpon()
            .appendEncodedPath(tvDetailsUI.poster_path)
            .build()

        backdrop.loadImageCoil(uri)
        title_tv.text = tvDetailsUI.name
        //update adapter
        adapter.submitList(tvDetailsUI.seasons)
    }

    private fun setupList() {
        seasons_rv.adapter = adapter
    }

}