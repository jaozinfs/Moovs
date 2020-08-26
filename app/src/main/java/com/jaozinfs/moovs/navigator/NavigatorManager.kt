package com.jaozinfs.moovs.navigator

import androidx.navigation.NavController
import com.jaozinfs.moovs.R

typealias NavigationID = Int

object NavigatorManager {
    const val DEFAULT: NavigationID = R.id.navigation_base_xml
    const val FEATURE_SETTINGS: NavigationID = R.id.nav_features_settings
}

data class NavigationFeature(var id: NavigationID = NavigatorManager.DEFAULT)

fun NavController.navigateFeature(scope: NavigationFeature.() -> Unit) {
    val navigationFeature = NavigationFeature()
    scope.invoke(navigationFeature)
    navigate(navigationFeature.id)
}