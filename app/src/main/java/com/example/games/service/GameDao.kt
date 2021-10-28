package com.example.games.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.games.model.GameModel

@Dao
interface GameDao {

    @Insert
    suspend fun insertAll(vararg games:GameModel ):List<Long>

    @Query("SELECT * FROM GameModel")
    suspend fun getAllGames():List<GameModel>

    @Query("DELETE  FROM GameModel")
    suspend fun deleteGameModel():Int

    @Query("SELECT EXISTS (SELECT 1 FROM GameModel WHERE id = :id)")
    suspend fun exists(id: Int): Boolean

    @Query("DELETE  FROM GameModel WHERE id = :id")
    suspend fun deleteItem(id: Int): Int

}