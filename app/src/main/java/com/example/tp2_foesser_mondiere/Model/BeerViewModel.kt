package com.example.tp2_foesser_mondiere.Model

import androidx.constraintlayout.motion.widget.Debug
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.Console

class BeerViewModel:ViewModel() {
    private var beerIdSelected : Int = -1
    private var beerSelected : MutableLiveData<Beer> = MutableLiveData<Beer>()
    private val beers : MutableLiveData<List<Beer>> by lazy {
        MutableLiveData<List<Beer>>().also {
            this.loadBeers()
        }
    }
    private fun loadBeers(){ //Méthode permettant de récupérer les bières

        val client = HttpClient(CIO) {
            expectSuccess = false
        }
        viewModelScope.launch {
            var byteArrayBody: String
            try {
                val httpResponse: HttpResponse = client.get("https://api.punkapi.com/v2/beers")
                byteArrayBody = httpResponse.receive()
                val json = Json{ ignoreUnknownKeys = true}
                beers.value = json.decodeFromString<List<Beer>>(byteArrayBody)
            } catch (e: Exception) {
                Debug.logStack("test", "erreur", 0)
                byteArrayBody = "{}"
                beers.value = listOf()
            }
            finally {
                client.close()
            }
        }
    }
    fun  getBeers(): LiveData<List<Beer>> {
        return beers
    }
    fun selectBeer(position : Int){
        beerIdSelected = position
        beerSelected.value = beers.value!![position]
    }
    fun getBeerId(position : Int): LiveData<Beer>{
        return MutableLiveData<Beer>(beers.value!![position])
    }
    fun getBeerSelected():LiveData<Beer>{
        return beerSelected
    }
    fun getBeerIdSelected():Int{
        return beerIdSelected
    }
}