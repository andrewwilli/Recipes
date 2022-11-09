package com.example.recipes

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recipes.fragments.MyRecipRecyclerViewAdapter
import com.example.recipes.model.RandomRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException


class RequestManager(context: Context, adapter: MyRecipRecyclerViewAdapter) {


    var context: Context
    var adapter: MyRecipRecyclerViewAdapter

    init {
        this.context = context
        this.adapter = adapter
    }

    fun getRandomRecipes() {
//        val myUrl =
//            "https://api.spoonacular.com/recipes/random?number=1&tags=vegetarian,dessert&apiKey=80e4276aeedc4c0c963addb5d27af1e0"
//        val myRequest = StringRequest(
//            Request.Method.GET, myUrl,
//            { response: String? ->
//                try {
//
//                    val jsonResponse =
//                        JSONObject(response) // String instance holding the above json
//                    println("SKYAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
//                    val json = Json { ignoreUnknownKeys = true }
//                    val gson = GsonBuilder().create()
//                    val theList = gson.fromJson<ArrayList<String>>(response, object :
//                        TypeToken<ArrayList<String>>() {}.type)
//                    if (response != null) {
////                        val listTest: RecipeList = json.decodeFromString<RecipeList>(response)
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//        ) { volleyError: VolleyError ->
//            println(volleyError)
//        }
//
//        val requestQueue = Volley.newRequestQueue(context)
//        requestQueue.add(myRequest)
    }

    fun getRandomRecipes2(){

        val url = "https://api.spoonacular.com/recipes/random?number=4&tags=vegetarian,dessert&apiKey=80e4276aeedc4c0c963addb5d27af1e0"

        val jsonObjectRequest = JsonObjectRequest(url,
            { response ->
                try {
                    val typeToken = object : TypeToken<List<RandomRecipe>>() {}.type
                    val recipes = Gson().fromJson<List<RandomRecipe>>(response.getString("recipes"), typeToken)
                    adapter.setItems(recipes)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.e("LOG", error.toString()) }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)

    }



    //    fun getRandomRecipes(listener: RandomAPIResponseListener, tags: List<String?>?) {
//        val callRandomRecipe = retrofit.create(CallRandomRecipe::class.java)
//        val call: Call<RandomRecipeResponse> =
//            callRandomRecipe.callRandomRecipe(context.getString(R.string.api_key), "80", tags)
//        call.enqueue(object : Callback<RandomRecipeResponse> {
//            override fun onResponse(
//                call: Call<RandomRecipeResponse>,
//                response: Response<RandomRecipeResponse>
//            ) {
//                if (!response.isSuccessful) {
//                    listener.didError(response.message())
//                    return
//                }
//                listener.didFetch(response.body()?.recipes, response.message())
//            }
//
//            override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
//                listener.didError(t.message)
//            }
//        })
//    }
/*
    fun getRecipeDetails(listener: RecipeDetailsResponseListener, id: Int) {
        val callRecipeDetails = retrofit.create(CallRecipeDetails::class.java)
        val call: Call<RecipeDetailsResponse> =
            callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key))
        call.enqueue(object : Callback<RecipeDetailsResponse?> {
            override fun onResponse(
                call: Call<RecipeDetailsResponse?>,
                response: Response<RecipeDetailsResponse?>
            ) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch(response.body(), response.message())
            }

            override fun onFailure(call: Call<RecipeDetailsResponse?>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }

    fun getSimilarRecipe(listener: SimilarRecipeListener, id: Int) {
        val callSimilarRecipes = retrofit.create(CallSimilarRecipes::class.java)
        val call: Call<List<SimilarRecipeResponse>> =
            callSimilarRecipes.callSimilarRecipe(id, context.getString(R.string.api_key))
        call.enqueue(object : Callback<List<SimilarRecipeResponse?>?> {
            override fun onResponse(
                call: Call<List<SimilarRecipeResponse?>?>,
                response: Response<List<SimilarRecipeResponse?>?>
            ) {
                if (!response.isSuccessful) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch(response.body(), response.message())
            }

            override fun onFailure(call: Call<List<SimilarRecipeResponse?>?>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }

    fun getInstructions(listener: InstructionsListener, id: Int) {
        val callInstructions = retrofit.create(CallInstructions::class.java)
        val call: Call<List<InstructionsResponse>> =
            callInstructions.callInstructions(id, context.getString(R.string.api_key))
        call.enqueue(object : Callback<List<InstructionsResponse?>?> {
            override fun onResponse(
                call: Call<List<InstructionsResponse?>?>,
                response: Response<List<InstructionsResponse?>?>
            ) {
                if (!response.isSuccessful) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch(response.body(), response.message())
            }

            override fun onFailure(call: Call<List<InstructionsResponse?>?>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }
*/
//    private interface CallRandomRecipe {
//        @GET("recipes/random")
//        fun callRandomRecipe(
//            @Query("apiKey") apiKey: String?,
//            @Query("number") number: String?,
//            @Query("tags") tags: List<String?>?
//        ): Call<RandomRecipeResponse>
//    }
/*
    private interface CallRecipeDetails {
        @GET("recipes/{id}/information")
        fun callRecipeDetails(
            @Path("id") id: Int,
            @Query("apiKey") apiKey: String?
        ): Call<RecipeDetailsResponse>
    }

    private interface CallSimilarRecipes {
        @GET("recipes/{id}/similar")
        fun callSimilarRecipe(
            @Path("id") id: Int,
            @Query("apiKey") apiKey: String?
        ): Call<List<SimilarRecipeResponse>>
    }

    private interface CallInstructions {
        @GET("recipes/{id}/analyzedInstructions")
        fun callInstructions(
            @Path("id") id: Int,
            @Query("apiKey") apiKey: String?
        ): Call<List<InstructionsResponse>>
    }
    */

}