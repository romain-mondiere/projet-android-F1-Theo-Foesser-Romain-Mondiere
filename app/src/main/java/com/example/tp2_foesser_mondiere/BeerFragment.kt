package com.example.tp2_foesser_mondiere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.load
import com.example.tp2_foesser_mondiere.Model.Beer
import com.example.tp2_foesser_mondiere.Model.BeerViewModel
import com.google.android.material.textview.MaterialTextView
import kotlin.text.StringBuilder

class BeerFragment(val beerPos : Int = -1) : Fragment(R.layout.beer_view) {
    private val bs : BeerViewModel by activityViewModels()
    private var beer: LiveData<Beer>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.beer_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title:MaterialTextView = view.findViewById(R.id.beer_view_beer_name)
        val abv:MaterialTextView = view.findViewById(R.id.beer_view_beer_abv)
        val tags:MaterialTextView = view.findViewById(R.id.beer_view_beer_tagline)
        val description:MaterialTextView = view.findViewById(R.id.beer_view_beer_description)
        val foods:MaterialTextView = view.findViewById(R.id.beer_view_beer_foods)
        val image:ImageView = view.findViewById(R.id.beer_view_beer_image)
        if(beerPos == -1)
            beer = bs.getBeerSelected()
        else
            beer = bs.getBeerId(beerPos)
        beer!!.observe(viewLifecycleOwner,{
            val sb:StringBuilder = StringBuilder(10) //Utilisation d'un string builder pour
            //l'immuabilité de la string
            sb.append(it.abv)
            sb.append("%") //Degré d'alcool de la bière
            title.text = it.name
            abv.text = sb.toString()
            tags.text = it.tagline
            description.text = it.description
            image.load(it.image_url)
            foods.text="" //les aliments qui vont bien avec cette bière
            val buildString:StringBuilder = StringBuilder()
            for (food in it.food_pairing) {
                buildString.append("- ")
                buildString.append(food)
                buildString.append("\n")
            }
            foods.text = buildString.toString()
        })
    }
}