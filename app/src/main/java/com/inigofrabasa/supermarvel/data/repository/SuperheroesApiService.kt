package com.inigofrabasa.supermarvel.data.repository

import com.inigofrabasa.supermarvel.data.model.Model
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SuperheroesApiService {

    @GET("bvyob")
    fun obtainSuperheroes(@Query("language") language: String):
            Observable<Model.SuperHeroesFetchInfo>

    companion object {
        fun create(): SuperheroesApiService {
            val retrofit =
                Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.myjson.com/bins/")
                    .build()

            return retrofit.create(SuperheroesApiService::class.java)
        }
    }
}