package github.vege19.githublibrary.api

import github.vege19.githublibrary.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {

    @GET("user")
    fun getUser(@Header("Authorization") basicAuth: String): Call<UserModel>

}