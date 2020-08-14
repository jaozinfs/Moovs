package com.jaozinfs.moovs.movies.data.network.model

data class MovieImagesNetwork(
    val backdrops: List<BackDropNetwork> = emptyList(),
    val posters: List<PostersNetwork> = emptyList()
)

data class BackDropNetwork(val file_path: String)
data class PostersNetwork(val file_path: String)
