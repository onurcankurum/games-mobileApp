package com.example.games.adapter
import com.example.games.databinding.GameItemBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.games.R
import com.example.games.model.GameModel
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val gamelist:ArrayList<GameModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.GameViewHolder>(){

    interface Listener {
        fun onItemClick(cryptoModel: GameModel)
    }
    class GameViewHolder(var view: GameItemBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<GameItemBinding>(inflater,R.layout.game_item,parent,false)
        return  GameViewHolder(view)

        }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.view.name.text  = gamelist[position].name
        holder.view.ratingReleased.text  = gamelist[position].rating+"      "+gamelist[position].released
        Picasso.get().load(gamelist[position].background_image).into( holder.view.img)

        holder.view.root.setOnClickListener {
            listener.onItemClick(gamelist[position])
        }


    }

    override fun getItemCount(): Int {
        return gamelist.size
    }
    fun updateGameList(newGameList: List<GameModel>) {
        gamelist.clear()
        gamelist.addAll(newGameList)
        notifyDataSetChanged()
    }

}