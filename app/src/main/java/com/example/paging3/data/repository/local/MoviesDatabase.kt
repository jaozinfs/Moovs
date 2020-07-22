package com.example.paging3.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3.data.repository.local.dao.MoviesDao
import com.example.paging3.data.repository.local.dao.MoviesKeyDao
import com.example.paging3.data.repository.local.entities.MovieEntity

//@Database(entities = [MovieEntity::class], version = 1)
//abstract class MoviesDatabase : RoomDatabase() {
//    abstract fun moviesDao(): MoviesDao
//    abstract fun moviesKeyIdDao(): MoviesKeyDao
//}