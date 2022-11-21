package com.example.recipes.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetail(val id:String, val readyInMinutes: String, val title: String, val extendedIngredients: List<Ingredients>, val instructions: String, val image:String)