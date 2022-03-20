package com.example.tp2_foesser_mondiere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.tp2_foesser_mondiere.Model.Beer
import com.example.tp2_foesser_mondiere.Model.BeerViewModel

class BeerViewPagerFragment: Fragment(R.layout.beer_viewpager) {
    private val bs : BeerViewModel by activityViewModels()
    private var callbackPageChanged : ViewPager2.OnPageChangeCallback? = null
    private var viewPager : ViewPager2? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.beer_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById<ViewPager2>(R.id.ViewPagerBeer)
        val pageAdapter = ScreenSlidePagerAdapter(this) //Ajout de la possibilité de Swipe entre les bières
        viewPager?.adapter = pageAdapter
        viewPager?.setCurrentItem(bs.getBeerIdSelected(),false) //Sélectionne la bière
        callbackPageChanged = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bs.selectBeer(position)
            }
        }
        viewPager?.registerOnPageChangeCallback(callbackPageChanged!!)
    }
    override fun onDestroyView(){
        if(callbackPageChanged != null) {
            viewPager?.unregisterOnPageChangeCallback(callbackPageChanged!!)
        }
        super.onDestroyView()
    }

    private inner class ScreenSlidePagerAdapter(fa: BeerViewPagerFragment) : FragmentStateAdapter(fa) {
        private val bs : BeerViewModel by activityViewModels()
        override fun getItemCount(): Int{
            return bs.getBeers().value!!.size
        }

        override fun createFragment(position: Int):Fragment {
            return BeerFragment(position)
        }

        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }
    }
}
