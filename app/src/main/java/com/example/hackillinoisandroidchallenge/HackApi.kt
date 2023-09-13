package com.example.hackillinoisandroidchallenge

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface HackApi {
    @GET("event")
    fun getEvents() : Call<Events>
}