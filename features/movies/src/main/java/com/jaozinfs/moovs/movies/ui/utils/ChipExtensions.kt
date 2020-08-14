package com.jaozinfs.moovs.movies.ui.utils

import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * Get list of chips children
 * filter only id on [list] predicate
 * map all to ChipView
 * set Checked
 */
fun ChipGroup.setChipsSelectedByIds(list: List<Int>) {
    list.forEach {
        val chip = findViewById<Chip>(it)
        chip?.isChecked = true
    }
}