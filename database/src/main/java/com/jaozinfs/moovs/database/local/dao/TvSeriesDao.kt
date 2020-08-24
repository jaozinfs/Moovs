package com.jaozinfs.moovs.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaozinfs.moovs.database.local.entities.series.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvSeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvDetails(tvDetailsEntity: TvEntity): Long

    @Query("SELECT * FROM tventity ORDER by id DESC")
    suspend fun getTvsFavorited(): List<TvEntity>

    @Query("SELECT * FROM tventity WHERE id is :tvId")
    fun checkTvFavorited(tvId: Int): Flow<List<TvEntity>>

    @Query("DELETE FROM tventity WHERE id is :tvId")
    suspend fun removeTvFavorited(tvId: Int): Int
}