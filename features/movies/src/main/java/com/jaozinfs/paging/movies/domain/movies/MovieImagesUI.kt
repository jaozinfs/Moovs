package com.jaozinfs.paging.movies.domain.movies

data class MovieImagesUI(
    val backdrops: List<BackDropUI> = emptyList(),
    val posters: List<PostersUI> = emptyList()
)

data class BackDropUI(val file_path: String)
data class PostersUI(val file_path: String)
