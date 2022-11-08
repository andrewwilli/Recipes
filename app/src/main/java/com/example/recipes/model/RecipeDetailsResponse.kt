package com.example.recipes.model

class RecipeDetailsResponse {
    var vegetarian = false
    var vegan = false
    var glutenFree = false
    var dairyFree = false
    var veryHealthy = false
    var cheap = false
    var veryPopular = false
    var sustainable = false
    var weightWatcherSmartPoints = 0
    var gaps: String? = null
    var lowFodmap = false
    var aggregateLikes = 0
    var spoonacularScore = 0
    var healthScore = 0
    var creditsText: String? = null
    var license: String? = null
    var sourceName: String? = null
    var pricePerServing = 0.0
    var extendedIngredients: List<ExtendedIngredient>? = null
    var id = 0
    var title: String? = null
    var readyInMinutes = 0
    var servings = 0
    var sourceUrl: String? = null
    var image: String? = null
    var imageType: String? = null
    var summary: String? = null
    var cuisines: List<Any>? = null
    var dishTypes: List<String>? = null
    var diets: List<Any>? = null
    var occasions: List<Any>? = null
    var winePairing: WinePairing? = null
    var instructions: String? = null
    var analyzedInstructions: List<Any>? = null
    var originalId: Any? = null
    var spoonacularSourceUrl: String? = null
}