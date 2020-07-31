package com.jaozinfs.paging.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaozinfs.paging.database.local.converters.Converters
import com.jaozinfs.paging.database.local.dao.MoviesDao
import com.jaozinfs.paging.database.local.dao.MoviesKeyDao
import com.jaozinfs.paging.database.local.entities.MovieEntity

//@Database(entities = [MovieEntity::class], version = 1)
//@TypeConverters(Converters::class)
//abstract class MoviesDatabase : RoomDatabase() {
//    abstract fun moviesDao(): MoviesDao
//    abstract fun moviesKeyIdDao(): MoviesKeyDao
//}