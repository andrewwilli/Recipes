package com.example.recipes.model
import kotlinx.serialization.Serializable

@Serializable
data class RandomRecipe(val title: String, val readyInMinutes: String)