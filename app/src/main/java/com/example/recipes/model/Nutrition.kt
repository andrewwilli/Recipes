package com.example.recipes.model

import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(val calories:String, val carbs: String, val fat: String, val protein:String)