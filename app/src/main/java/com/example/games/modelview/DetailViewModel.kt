package com.example.games.modelview

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.games.model.GameDetail
import com.example.games.model.GameModel
import com.example.games.service.GameAPI
import com.example.games.service.GameDatabase
//import com.example.games.service.GameDatabase
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DetailViewModel(application:Application):CoroutineBase(application) {
    private val base_URl="https://api.rawg.io/api/"
    val id =  MutableLiveData<String>()
    val isExist =  MutableLiveData<Boolean>().apply { postValue(false)}

    val gameModel = MutableLiveData<GameModel>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
fun loadData(handleResponse:(gameDetail:GameDetail)-> Unit){

    val retrofit = Retrofit.Builder()
        .baseUrl(base_URl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(GameAPI::class.java)

    compositeDisposable.add(retrofit.getDetail(gameModel.value!!.id.toString())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(

            {
                it?.let {
                    handleResponse(it)
                }
            }
        )
        )

}

    fun isExist(id:Int){

        launch {
            val dao = GameDatabase(getApplication()).gameModelDao().exists(id)
            isExist.value = dao

        }

    }

     fun storeInSQLite(game:GameModel) {
        launch {
            val dao = GameDatabase(getApplication()).gameModelDao()
            val listLong = dao.insertAll(game)
        isExist.value=true
        }
    }
    fun deleteItem(game:GameModel) {
        launch {
            val dao = GameDatabase(getApplication()).gameModelDao()
            dao.deleteItem(game.id) // -> list -> individual
            isExist.value=false
        }


    }


    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }


}