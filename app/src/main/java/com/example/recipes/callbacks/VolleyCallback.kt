package com.example.recipes.callbacks

import com.example.recipes.model.Recipe

interface VolleyCallback {
    fun onSuccess(result: MutableList<Recipe>);
}