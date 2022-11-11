package com.example.recipes

import com.example.recipes.model.Recipe

interface VolleyCallback {
    fun onSuccess(result: MutableList<Recipe>);
}