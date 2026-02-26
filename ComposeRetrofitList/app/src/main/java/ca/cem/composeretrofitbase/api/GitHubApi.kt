package ca.cem.composeretrofitbase.api

import retrofit2.Call
import retrofit2.http.GET

interface GitHubApi {
    @GET("/exos/long/list")
    fun longList(): Call<List<Double>>

    @GET("/exos/truc/list")
    fun trucList(): Call<List<Truc>>
}
