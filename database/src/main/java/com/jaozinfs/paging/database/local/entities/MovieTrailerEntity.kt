package com.jaozinfs.paging.database.local.entities

import androidx.room.Entity


@Entity
data class MovieTrailerEntity(
    val backdrops: List<BackDropEntity> = emptyList(),
    val posters: List<PostersEntity> = emptyList()
)

data class BackDropEntity(val file_path: String)
data class PostersEntity(val file_path: String)
