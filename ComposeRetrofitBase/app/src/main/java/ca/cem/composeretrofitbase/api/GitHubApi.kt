package ca.cem.composeretrofitbase.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("/exos/truc/complexe")
    fun complexe(@Query("name") prénom: String): Call<Complexe>
}
