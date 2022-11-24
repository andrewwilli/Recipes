package com.example.recipes.callbacks

import com.example.recipes.model.RecipeDetail

interface RecipeDetailCallback {
    fun onSuccess(result: RecipeDetail);
}