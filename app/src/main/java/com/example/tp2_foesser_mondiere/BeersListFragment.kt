package com.example.tp2_foesser_mondiere

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2_foesser_mondiere.Model.Beer
import com.example.tp2_foesser_mondiere.Model.BeerViewModel

class BeersListFragment() : Fragment(R.layout.fragment_beers_list),BeerClickListener {
    private val bs : BeerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beers_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val beers = bs.getBeers().observe(viewLifecycleOwner,Observer<List<Beer>>{

            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = BeerAdapter(it,this)
        }
        )
        recyclerView.post {
            if (bs.getBeerIdSelected() != -1) {
                var llm =
                    recyclerView.layoutManager as LinearLayoutManager//.scrollToPosition(bs.getBeerIdSelected())
                llm.scrollToPositionWithOffset(bs.getBeerIdSelected(),0)
                //recyclerView.smoothScrollToPosition(bs.getBeerIdSelected())
            }
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onItemClick(position: Int) {
        bs.selectBeer(position)
        findNavController().navigate(
            R.id.action_beersListFragment_to_beerViewPagerFragment,
        )
    }

}