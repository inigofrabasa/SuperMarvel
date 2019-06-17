package com.inigofrabasa.supermarvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inigofrabasa.supermarvel.data.model.Model
import com.inigofrabasa.supermarvel.data.repository.SuperheroesRepository

class SuperheroesListViewModel : ViewModel() {
    private val superheroesLiveData = SuperheroesRepository.getInstance().superheroes
    private val searchSuperheroesLiveData = SuperheroesRepository.getInstance().searchSuperheroes

    val superheroes: LiveData<List<Model.Superheroe>>
        get() = superheroesLiveData

    val searchSuperheroes: LiveData<List<Model.Superheroe>>
        get() = searchSuperheroesLiveData

    fun getSuperheroes(){
        SuperheroesRepository.getInstance().fetchSuperheroes()
    }

    fun searchSuperheroes(request:String){
        val searchResult = arrayListOf<Model.Superheroe>()
        superheroes.value?.filter { superheroe -> superheroe.name.toLowerCase().contains(request) }?.let { searchResult.addAll(it) }
        searchSuperheroesLiveData.value = searchResult
    }
}