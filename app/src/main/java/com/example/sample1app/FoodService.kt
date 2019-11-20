package com.example.sample1app

import com.example.sample1app.db.ApiClient

class FoodService(private val apiClient: ApiClient)  {
    fun getFoodList() = apiClient.apiDemo(lng = 1,lat = 1,range = 3)
}