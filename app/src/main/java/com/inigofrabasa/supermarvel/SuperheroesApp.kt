package com.inigofrabasa.supermarvel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.inigofrabasa.supermarvel.data.model.Model

class SuperheroesApp : Application() {
    var superheroe: MutableLiveData<Model.Superheroe> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SuperheroesApp
            private set
    }
}