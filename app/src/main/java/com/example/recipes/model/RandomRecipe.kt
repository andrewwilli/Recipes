package com.example.recipes.model
import kotlinx.serialization.Serializable

@Serializable
data class RandomRecipe(val id:String, val title: String, val readyInMinutes: String, val image:String)