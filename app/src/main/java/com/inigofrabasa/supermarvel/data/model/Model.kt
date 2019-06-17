package com.inigofrabasa.supermarvel.data.model

import com.google.gson.annotations.SerializedName

object Model {
    data class SuperHeroesFetchInfo (

        @SerializedName("superheroes") val superheroes : List<Superheroe>
    )
    data class Superheroe(
        @SerializedName("name") val name : String,
        @SerializedName("photo") val photo : String,
        @SerializedName("realName") val realName : String,
        @SerializedName("height") val height : String,
        @SerializedName("power") val power : String,
        @SerializedName("abilities") val abilities : String,
        @SerializedName("groups") val groups : String
    )
}