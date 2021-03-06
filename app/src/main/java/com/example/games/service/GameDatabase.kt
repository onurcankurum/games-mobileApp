package com.example.games.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.games.model.GameModel

@Database(entities = arrayOf(GameModel::class),version = 1)
abstract class GameDatabase :RoomDatabase(){

    abstract fun gameModelDao():GameDao


    companion object{
        @Volatile private var instance :GameDatabase?=null

        private val lock = Any()

        operator fun invoke (context: Context)= instance?: synchronized(lock){
            instance?:makeDatabase(context).also{

                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,GameDatabase::class.java,"gameDatabase"
        ).build()


    }

}