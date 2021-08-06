package com.app.colourlovers.data

import com.app.colourlovers.retrofit.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllColours(keywords: String, format: String, numResult: Int, offset: Int) = retrofitService.getAllColours(keywords, format, numResult, offset)
}