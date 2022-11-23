package com.example.recipes.callbacks

import com.example.recipes.model.Recipe

interface FavoriteCallback {
    fun onSuccess(result: MutableList<Recipe>);
}