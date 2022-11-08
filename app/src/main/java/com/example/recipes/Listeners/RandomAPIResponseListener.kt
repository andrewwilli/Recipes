package com.example.recipes.Listeners

import com.example.recipes.model.RandomRecipe

interface RandomAPIResponseListener {
    fun didFetch(responses: List<RandomRecipe?>?, message: String?)
    fun didError(message: String?)
}