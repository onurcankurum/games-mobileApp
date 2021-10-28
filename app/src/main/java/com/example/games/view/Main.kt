package com.example.games.view

import android.opengl.Visibility
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation

import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.games.R
import com.example.games.adapter.RecyclerViewAdapter
import com.example.games.adapter.ViewPagerAdapter
import com.example.games.databinding.FragmentMainBinding
import com.example.games.model.GameModel
import com.example.games.modelview.MainViewModel
import io.reactivex.disposables.CompositeDisposable
//import kotlinx.android.synthetic.main.fragment_main.*


class Main : Fragment(),RecyclerViewAdapter.Listener {
 private lateinit var binding : FragmentMainBinding


    private lateinit var viewModel : MainViewModel
    private  var recyclerViewAdapter: RecyclerViewAdapter?=null
    private  var viewPagerAdapter: ViewPagerAdapter?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.loadData()

        val layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context)
        binding.recyclerView1.layoutManager=layoutManager


        binding.homeButton.setOnClickListener {
            viewModel.loadData()
            binding.homeButton.drawable.setTint(resources.getColor(R.color.fourteen))
            binding.likeButton.drawable.setTint(resources.getColor(R.color.nine))
        }

        binding.likeButton.setOnClickListener {
            binding.likeButton.drawable.setTint(resources.getColor(R.color.fourteen))
            binding.homeButton.drawable.setTint(resources.getColor(R.color.nine))

            viewModel.getDataFromSQLite()
        }
        observeLiveData()
        searchBarObserver()


    }




    private fun observeLiveData() {
       viewModel.isLoading.observe(viewLifecycleOwner, Observer {
           it?.let {
               if(it){
                   binding.progressBar.visibility=View.VISIBLE
               }else{
                   binding.progressBar.visibility=View.GONE
               }
           }
       })


        viewModel.games.observe(viewLifecycleOwner, Observer {

            it?.let {


               if(recyclerViewAdapter!=null){
                   recyclerViewAdapter!!.updateGameList(it)

                   binding.recyclerView1.adapter = recyclerViewAdapter
               }
                else{

                   recyclerViewAdapter = RecyclerViewAdapter(it,this)
                   binding.recyclerView1.adapter = recyclerViewAdapter

               }
                if(viewPagerAdapter==null){

                       val threePhotoModel : ArrayList<GameModel> = ArrayList()
                    threePhotoModel.add(it[0])
                    threePhotoModel.add(it[1])
                    threePhotoModel.add(it[2])

                     viewPagerAdapter = ViewPagerAdapter(threePhotoModel,this)
                    binding.viewPager.adapter=viewPagerAdapter
                    binding.dotsIndicator.setViewPager( binding.viewPager)
                }
                else{
                    viewPagerAdapter!!.updateGameList(it)
                    binding.viewPager.adapter=viewPagerAdapter
                   binding.dotsIndicator.setViewPager(  binding.viewPager)

                }


            }
        })


    }

    private fun searchBarObserver(){

        binding.searchBar.addTextChangedListener {
            it?.let {
                if(it.toString().length==0){
                    binding.viewPagerLayout.visibility=View.VISIBLE

                }else{
                    binding.viewPagerLayout.visibility=View.GONE
                }
                if(it.toString().length>=3){
                    viewModel.listFilter(it.toString())

                }else{
                  if(viewModel.isLoading.value==false){
                      viewModel.loadData()
                  }
                }

            }
        }


    }

    override fun onItemClick(gameModel: GameModel) {

         val action = MainDirections.actionMain5ToDetail6(gameModel = gameModel)


        Navigation.findNavController(this.requireView()).navigate(action)
    }


}