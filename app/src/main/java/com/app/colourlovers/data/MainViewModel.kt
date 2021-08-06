package com.app.colourlovers.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val masterData = MutableLiveData<ArrayList<ColourResponseItem>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllColors(keywords: String, format: String, numResult: Int, offset: Int) {

        val response = repository.getAllColours(keywords, format, numResult, offset)
        response.enqueue(object : Callback<List<ColourResponseItem>> {
            override fun onResponse(call: Call<List<ColourResponseItem>>, response: Response<List<ColourResponseItem>>) {
                masterData.postValue(response.body() as ArrayList<ColourResponseItem>?)
            }

            override fun onFailure(call: Call<List<ColourResponseItem>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })

    }
}