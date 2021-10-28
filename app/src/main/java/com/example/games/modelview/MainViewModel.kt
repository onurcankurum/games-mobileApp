package com.example.games.modelview

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.games.adapter.RecyclerViewAdapter
import com.example.games.model.GameList
import com.example.games.model.GameModel
import com.example.games.service.GameAPI
import com.example.games.service.GameDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
//import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(application: Application):CoroutineBase(application) {

    val games =  MutableLiveData<ArrayList<GameModel>>()
    private val base_URl="https://api.rawg.io/api/"
    private var compositeDisposable: CompositeDisposable=CompositeDisposable()
    val isLoading =  MutableLiveData<Boolean>()
    private lateinit var everyTimeFulldata: List<GameModel>

    fun loadData(){
        compositeDisposable.delete(compositeDisposable)

        isLoading.value=true

        val retrofit = Retrofit.Builder()
            .baseUrl(base_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GameAPI::class.java)

        compositeDisposable.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                 {
                         handleResponse(it)
                }
            ))


    }

    private fun handleResponse(gameList: GameList?) {

        gameList?.let {
            games.value = ArrayList(gameList.elements)
            everyTimeFulldata=ArrayList(gameList.elements)
        }
        isLoading.value=false

    }

     fun getDataFromSQLite() {
         isLoading.value=true

        launch {
            val likedGames = GameDatabase(getApplication()).gameModelDao().getAllGames()
           games.value= ArrayList(likedGames)
        }
         isLoading.value= false
    }
    fun listFilter(subString:String){


        val (match, rest) = everyTimeFulldata.partition { it.name!!.contains(subString,ignoreCase = true) }
        games.value=ArrayList(match)



    }




    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }





}

