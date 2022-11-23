package com.example.recipes.callbacks

import com.example.recipes.model.Nutrition
import com.example.recipes.model.Recipe

interface NutritionCallback {
    fun onSuccess(result: Nutrition);
}