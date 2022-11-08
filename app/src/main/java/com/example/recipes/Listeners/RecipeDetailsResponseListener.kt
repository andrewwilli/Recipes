package com.example.recipes.Listeners

import com.example.recipes.model.RecipeDetailsResponse

interface RecipeDetailsResponseListener {
    fun didFetch(response: RecipeDetailsResponse?, message: String?)
    fun didError(message: String?)
}