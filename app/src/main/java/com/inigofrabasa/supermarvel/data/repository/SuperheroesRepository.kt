package com.inigofrabasa.supermarvel.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.inigofrabasa.supermarvel.data.model.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SuperheroesRepository {
    private lateinit var disposable: Disposable

    var superheroes: MutableLiveData<List<Model.Superheroe>> = MutableLiveData()
    var searchSuperheroes: MutableLiveData<List<Model.Superheroe>> = MutableLiveData()

    private val superheroresApiService by lazy {
        SuperheroesApiService.create()
    }

    fun fetchSuperheroes() {
        disposable =
            superheroresApiService.obtainSuperheroes("en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        superheroes.value = result.superheroes
                    },
                    { error ->
                        Log.v("Error fetching heroes",error.message)
                    }
                )
    }

    companion object {
        // For Singleton
        @Volatile private var instance: SuperheroesRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: SuperheroesRepository().also { instance = it }
            }
    }
}