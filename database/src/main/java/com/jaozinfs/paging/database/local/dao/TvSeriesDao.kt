package com.jaozinfs.paging.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaozinfs.paging.database.local.entities.series.TvEntity

@Dao
interface TvSeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTvDetails(tvDetailsEntity: TvEntity): Long

    @Query("SELECT * FROM tventity ORDER by id DESC")
    fun getTvsFavorited(): List<TvEntity>
}