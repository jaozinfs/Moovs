package com.example.paging.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.paging.database.local.converters.Converters
import com.example.paging.database.local.dao.MoviesDao
import com.example.paging.database.local.dao.MoviesKeyDao
import com.example.paging.database.local.entities.MovieEntity

//@Database(entities = [MovieEntity::class], version = 1)
//@TypeConverters(Converters::class)
//abstract class MoviesDatabase : RoomDatabase() {
//    abstract fun moviesDao(): MoviesDao
//    abstract fun moviesKeyIdDao(): MoviesKeyDao
//}