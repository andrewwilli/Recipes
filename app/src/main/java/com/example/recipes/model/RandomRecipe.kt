package com.example.recipes.model

class RandomRecipe {
    var isVegetarian = false
    var isVegan = false
    var isGlutenFree = false
    var isDairyFree = false
    var isVeryHealthy = false
    var isCheap = false
    var isVeryPopular = false
    var isSustainable = false
    var weightWatcherSmartPoints = 0
    var gaps: String? = null
    var isLowFodmap = false
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
    var cuisines: List<String>? = null
    var dishTypes: List<String>? = null
    var diets: List<String>? = null
    var occasions: List<String>? = null
    var instructions: String? = null
   // var analyzedInstructions: List<AnalyzedInstruction>? = null
    var originalId: Any? = null
    var spoonacularSourceUrl: String? = null
}