package ca.cem.composeretrofitbase.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("exos/long/double/{nombre}")
    fun doubler(@Path("nombre") nombre: Long): Call<String>
}
