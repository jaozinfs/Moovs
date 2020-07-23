package com.example.paging.navigator

sealed class Navigations(val moduleName: String, val className: String) {
    object Movies :
        Navigations("movies", "com.example.paging.movies.ui.MoviesActivity")
}

