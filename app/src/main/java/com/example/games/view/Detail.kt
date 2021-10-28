package com.example.games.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.games.R
import com.example.games.databinding.FragmentDetailBinding
import com.example.games.model.GameDetail
import com.example.games.model.GameModel
import com.example.games.modelview.DetailViewModel
import com.squareup.picasso.Picasso



class Detail : Fragment() {


    private lateinit var binding : FragmentDetailBinding
    private lateinit var gameModel: GameModel
    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        observeIsLiked()


        arguments?.let {
            gameModel = DetailArgs.fromBundle(it).gameModel
            viewModel.gameModel.value=gameModel
            viewModel.isExist(gameModel.id)
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.loadData(handleResponse = this::handleResponse)



        binding.likeButton.setOnClickListener {
            if (viewModel.isExist.value!!) {

                viewModel.deleteItem(gameModel)
                Toast.makeText(
                    getActivity(),
                    "oyun beğenilerden çıkarılıyor",
                    Toast.LENGTH_SHORT
                ).show();


            } else {
                viewModel.storeInSQLite(gameModel)
                Toast.makeText(
                    getActivity(),
                    "oyun beğenilenlere ekleniyor",
                    Toast.LENGTH_SHORT
                ).show();
            }

        }

    }

    fun handleResponse (gameDetail: GameDetail){
        binding.likeButton.visibility=VISIBLE
        Picasso.get().load(gameDetail.background_image).into(binding.img)
        binding.description.loadData( "<body style=\"margin: 0; padding: 0;\">"+gameDetail.description, "text/html", "utf-8")
        binding.gameName.text=gameDetail.name
        binding.release.text=gameDetail.released
        binding.metricRate.text=gameDetail.metacritic.toString()

    }

    private fun observeIsLiked(){
        viewModel.isExist.observe(viewLifecycleOwner, Observer {
            it.let {
                if(it){
                    binding.likeButton.drawable.setTint(resources.getColor(R.color.fourteen))
                }
                else
                {
                    binding.likeButton.drawable.setTint(resources.getColor(R.color.three))
                }
            }
        })


    }




}