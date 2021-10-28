package com.example.games.service

import com.example.games.model.GameDetail
import com.example.games.model.GameList
import com.example.games.model.GameModel
import io.reactivex.Observable

import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path


interface GameAPI {

    @GET("games?key=61e0c0a8c8f0450ab754eae99ff94d70")
    fun getData(): Observable<GameList?>

    @GET("games/{id}?key=61e0c0a8c8f0450ab754eae99ff94d70")
    fun getDetail(@Path("id")Id : String): Observable<GameDetail?>
}