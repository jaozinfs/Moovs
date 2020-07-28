package com.example.paging.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging.movies.data.local.entities.MoviesKeyId

@Dao
interface MoviesKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MoviesKeyId>)

    @Query("SELECT * FROM movies_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): MoviesKeyId?

    @Query("DELETE FROM movies_keys")
    suspend fun clearRemoteKeys()
}