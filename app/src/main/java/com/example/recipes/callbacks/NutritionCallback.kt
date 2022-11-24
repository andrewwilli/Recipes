package com.example.recipes.callbacks

import com.example.recipes.model.Nutrition

interface NutritionCallback {
    fun onSuccess(result: Nutrition);
}