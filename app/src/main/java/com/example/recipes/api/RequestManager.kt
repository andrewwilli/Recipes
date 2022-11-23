package com.example.recipes.api

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recipes.callbacks.FavoriteCallback
import com.example.recipes.callbacks.RecipeCallback
import com.example.recipes.callbacks.RecipeDetailCallback
import com.example.recipes.model.Recipe
import com.example.recipes.model.RecipeDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException


class RequestManager(context: Context) {

    var baseURL = "https://api.spoonacular.com/recipes/"
    var apiKey = "68641ff6c90e4d91ac418e66cdc4533d"
    var context: Context
    val requestQueue = Volley.newRequestQueue(context)

    init {
        this.context = context
    }

    fun getRandomRecipes(callback: RecipeCallback) {
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
        callback: RecipeCallback
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

    fun getDetailsForRecipe(recipeId: String, callback: RecipeDetailCallback){
        requestQueue.cancelAll("All")
        val url =
            baseURL + "$recipeId/information?includeNutrition=false&apiKey=$apiKey"
        val jsonObjectRequest = JsonObjectRequest(url,
            { response ->
                try {
                    val typeToken = object : TypeToken<RecipeDetail>() {}.type
                    val detailedRecipe = Gson().fromJson<RecipeDetail>(
                        response.toString(),
                        typeToken
                    )
                    callback.onSuccess(detailedRecipe)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.e("LOG", error.toString()) }
        requestQueue.add(jsonObjectRequest)

    }
    fun getFavoriteRecipe(recipeId: String, callback: FavoriteCallback) {
        val list: List<String> = recipeId.split(",")
        val listM : MutableList<Recipe> = emptyList<Recipe>().toMutableList()
        var intcounter = 0
        for(item in list){

             getDetailsForRecipe(item, object : RecipeDetailCallback {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onSuccess(result: RecipeDetail) {
                    val recipe = Recipe(result.id, result.title, result.readyInMinutes, result.image)
                    listM.add(recipe)
                    intcounter++
                    if (intcounter==list.size){
                        callback.onSuccess(listM)
                    }
                }
            })
        }
/*
        val bulk = "informationBulk?ids=664288,632593"
        val url = baseURL +bulk+ "$&apiKey=$apiKey"
        val jsonObjectRequest = JsonObjectRequest(url,
            { response ->
                try {
                    println(response)
                    val typeToken = object : TypeToken<MutableList<RecipeDetail>>() {}.type
                    val recipes = Gson().fromJson<MutableList<RecipeDetail>>(
                        response.toString(),
                        typeToken
                    )

                    //callback.onSuccess(recipes)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.e("LOG", error.toString()) }
        requestQueue.add(jsonObjectRequest)
*/
        /*
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            {response ->
                try {
                    for (i in 0 until response.length()) {
                        val typeToken = object : TypeToken<MutableList<Recipe>>() {}.type
                        val recipes = Gson().fromJson<MutableList<Recipe>>(
                            response.getString(i),
                            typeToken
                        )
                    }
                }catch (e:JSONException){
                    e.printStackTrace()
                }
            }) { error -> Log.e("LOG", error.toString()) }
        requestQueue.add(jsonArrayRequest)
*/


    }

}

