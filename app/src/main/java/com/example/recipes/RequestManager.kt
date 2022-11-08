package com.example.recipes

import android.content.Context
import com.example.recipes.Listeners.RandomAPIResponseListener
import com.example.recipes.model.RandomRecipeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class RequestManager(context: Context) {
    var context: Context
    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        this.context = context
    }

    fun getRandomRecipes(listener: RandomAPIResponseListener, tags: List<String?>?) {
        val callRandomRecipe = retrofit.create(CallRandomRecipe::class.java)
        val call: Call<RandomRecipeResponse> =
            callRandomRecipe.callRandomRecipe(context.getString(R.string.api_key), "80", tags)
        call.enqueue(object : Callback<RandomRecipeResponse> {
            override fun onResponse(
                call: Call<RandomRecipeResponse>,
                response: Response<RandomRecipeResponse>
            ) {
                if (!response.isSuccessful) {
                    listener.didError(response.message())
                    return
                }
                listener.didFetch(response.body()?.recipes, response.message())
            }

            override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
                listener.didError(t.message)
            }
        })
    }
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
    private interface CallRandomRecipe {
        @GET("recipes/random")
        fun callRandomRecipe(
            @Query("apiKey") apiKey: String?,
            @Query("number") number: String?,
            @Query("tags") tags: List<String?>?
        ): Call<RandomRecipeResponse>
    }
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