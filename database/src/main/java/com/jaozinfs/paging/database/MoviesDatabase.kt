package com.jaozinfs.paging.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaozinfs.paging.database.local.converters.Converters
import com.jaozinfs.paging.database.local.dao.MoviesDao
import com.jaozinfs.paging.database.local.dao.TvSeriesDao
import com.jaozinfs.paging.database.local.entities.MovieEntity
import com.jaozinfs.paging.database.local.entities.series.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun tvsDao(): TvSeriesDao
}