package com.jaozinfs.moovs.extensions

import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


fun BottomNavigationView.setupWithNavController(navController: NavController) {
    setOnNavigationItemSelectedListener {
        if (selectedItemId != it.itemId)
            NavigationUI.onNavDestinationSelected(it, navController)
        else
            false
    }
}