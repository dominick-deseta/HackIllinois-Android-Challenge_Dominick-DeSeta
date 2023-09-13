package com.example.hackillinoisandroidchallenge

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    val hackApi: HackApi = Retrofit.Builder()
            .baseUrl("https://adonix.hackillinois.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HackApi::class.java)
}