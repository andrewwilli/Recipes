package com.example.recipes

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recipes.fragments.MyRecipeRecyclerViewAdapter
import com.example.recipes.model.RandomRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException


class RequestManager(context: Context, adapter: MyRecipeRecyclerViewAdapter) {

    var context: Context
    var adapter: MyRecipeRecyclerViewAdapter

    init {
        this.context = context
        this.adapter = adapter
    }

    fun getRandomRecipes() {
        val url =
            "https://api.spoonacular.com/recipes/random?number=4&tags=vegetarian,dessert&apiKey=80e4276aeedc4c0c963addb5d27af1e0"
        val jsonObjectRequest = JsonObjectRequest(url,
            { response ->
                try {
                    val typeToken = object : TypeToken<MutableList<RandomRecipe>>() {}.type
                    val recipes = Gson().fromJson<MutableList<RandomRecipe>>(
                        response.getString("recipes"),
                        typeToken
                    )
                    adapter.setItems(recipes)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.e("LOG", error.toString()) }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)

    }

    fun loadMoreRecipes() {
        val url =
            "https://api.spoonacular.com/recipes/random?number=4&tags=vegetarian,dessert&apiKey=80e4276aeedc4c0c963addb5d27af1e0"
        val jsonObjectRequest = JsonObjectRequest(url,
            { response ->
                try {
                    val typeToken = object : TypeToken<MutableList<RandomRecipe>>() {}.type
                    val recipes = Gson().fromJson<MutableList<RandomRecipe>>(
                        response.getString("recipes"),
                        typeToken
                    )
                    adapter.appendItems(recipes)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.e("LOG", error.toString()) }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)
    }

}