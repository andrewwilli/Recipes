package com.example.recipes.callbacks

import com.example.recipes.model.Recipe

interface RecipeCallback {
    fun onSuccess(result: MutableList<Recipe>);
}