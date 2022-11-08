package com.example.recipes.Listeners

import com.example.recipes.model.SimilarRecipeResponse

interface SimilarRecipeListener {
    fun didFetch(response: List<SimilarRecipeResponse?>?, message: String?)
    fun didError(message: String?)
}