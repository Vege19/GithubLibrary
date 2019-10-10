package github.vege19.githublibrary.api

import github.vege19.githublibrary.models.DefaultModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("pokemon")
    fun getPokemons(): Call<DefaultModel>

}