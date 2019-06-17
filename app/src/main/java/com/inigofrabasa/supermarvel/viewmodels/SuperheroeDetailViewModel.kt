package com.inigofrabasa.supermarvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inigofrabasa.supermarvel.SuperheroesApp
import com.inigofrabasa.supermarvel.data.model.Model

class SuperheroeDetailViewModel : ViewModel() {
    private val superheroeLiveData = SuperheroesApp.instance.superheroe

    val superheroe: LiveData<Model.Superheroe>
        get() = superheroeLiveData

    fun getSuperheroe(){
        SuperheroesApp.instance.superheroe
    }
}