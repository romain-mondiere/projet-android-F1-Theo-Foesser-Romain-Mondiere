package com.example.tp2_foesser_mondiere

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.Debug
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tp2_foesser_mondiere.Model.Beer
import com.example.tp2_foesser_mondiere.Model.BeerViewModel
import com.google.android.material.card.MaterialCardView

interface BeerClickListener {
    fun onItemClick(position: Int)
}
//Beer Adapter
class BeerAdapter(val beerList:List<Beer>,val beerClickListener: BeerClickListener) : RecyclerView.Adapter<Cellule>() {
    private val nbLines : Int = 2 //Nombre de ligne maximum pour la description
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cellule {//Construire les cellules
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.beer_cell,parent, false)
        return Cellule(view)
    }

    override fun onBindViewHolder(holder: Cellule, position: Int) { //Bind du contenu
        holder.descriptionTextView.maxLines = nbLines
        holder.titleTextView.text = beerList[position].name
        holder.descriptionTextView.text = beerList[position].description
        holder.beerImageView.load(beerList[position].image_url)
        holder.beerCardView.setOnClickListener {
            beerClickListener.onItemClick(position) // Ajouter un listener sur les clicks de la case
        }

    }

    override fun getItemCount(): Int {
        return beerList.size //retourne la liste des éléments de la liste
    }
}
class Cellule(view: View) : RecyclerView.ViewHolder(view){//Récupère les champs de la vue
    val titleTextView : TextView  = view.findViewById(R.id.title_textview)
    val descriptionTextView : TextView  = view.findViewById(R.id.description_textview)
    val beerImageView : ImageView = view.findViewById(R.id.beer_imageview)
    val beerCardView : MaterialCardView = view.findViewById(R.id.beer_materialCardView)

}