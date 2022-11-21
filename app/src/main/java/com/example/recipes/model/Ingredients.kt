package com.example.recipes.model

import kotlinx.serialization.Serializable

@Serializable
data class Ingredients(val id:String, val instructions: String, val readyInMinutes: String, val image:String)