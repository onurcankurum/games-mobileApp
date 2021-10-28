package com.example.games.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.example.games.R
import com.example.games.databinding.GameItemBinding
import com.example.games.model.GameModel
import com.squareup.picasso.Picasso
import com.example.games.databinding.ViewPageItemBinding
//import kotlinx.android.synthetic.main.game_item.view.*
//import kotlinx.android.synthetic.main.view_page_item.view.*
//import kotlinx.android.synthetic.main.view_page_item.view.img
//import kotlinx.android.synthetic.main.view_page_item.view.name


class ViewPagerAdapter(var list: ArrayList<GameModel>,private val listener : RecyclerViewAdapter.Listener) : PagerAdapter() {





    lateinit var layoutInflater: LayoutInflater






    override fun getCount(): Int {

         if(list.size<3){
             return list.size
         }
         else{
             return 3
         }


    }



    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view.equals(`object`)

    }



        override fun instantiateItem(parent: ViewGroup, position: Int): Any {

            val binding = ViewPageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            Picasso.get().load(list[position].background_image).into(binding.img)
            parent?.addView(binding.root)
            return binding.root

    }



    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
    fun updateGameList(newGameList: List<GameModel>) {
        list.clear()
        list.addAll(newGameList)
        notifyDataSetChanged()
    }


}
