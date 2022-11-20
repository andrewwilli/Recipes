package com.example.recipes.api

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recipes.callbacks.VolleyCallback
import com.example.recipes.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException


class RequestManager(context: Context) {

    var baseURL = "https://api.spoonacular.com/recipes/"
    //    var apiKey = "80e4276aeedc4c0c963addb5d27af1e0"
    var apiKey = "68641ff6c90e4d91ac418e66cdc4533d"
    var context: Context
    val requestQueue = Volley.newRequestQueue(context)



    init {
        this.context = context
    }

    fun getRandomRecipes(callback: VolleyCallback) {
        requestQueue.cancelAll("All")
        val url =
            baseURL + "random?number=2&tags=vegetarian,dessert&apiKey=$apiKey"
        val jsonObjectRequest = JsonObjectRequest(url,
            { response ->
                try {
                    val typeToken = object : TypeToken<MutableList<Recipe>>() {}.type
                    val recipes = Gson().fromJson<MutableList<Recipe>>(
                        response.getString("recipes"),
                        typeToken
                    )
                    callback.onSuccess(recipes)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.e("LOG", error.toString()) }
        requestQueue.add(jsonObjectRequest)
    }

    fun loadRecipesFiltered(
        searchString: String,
        cuisine: String,
        limit: Int,
        offset: Int,
        callback: VolleyCallback
    ) {
        requestQueue.cancelAll("All")
        val url =
            baseURL + "complexSearch?query=$searchString&apiKey=$apiKey&number=$limit&offset=$offset&cuisine=$cuisine"
        val jsonObjectRequest = JsonObjectRequest(url,
            { response ->
                try {
                    val typeToken = object : TypeToken<MutableList<Recipe>>() {}.type
                    val recipes = Gson().fromJson<MutableList<Recipe>>(
                        response.getString("results"),
                        typeToken
                    )
                    callback.onSuccess(recipes)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.e("LOG", error.toString()) }

        requestQueue.add(jsonObjectRequest)
    }

}

