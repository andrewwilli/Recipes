package com.example.recipes


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.recipes.api.RequestManager
import com.example.recipes.callbacks.RecipeDetailCallback
import com.example.recipes.fragments.RecipeHeaderFragment
import com.example.recipes.fragments.RecipeIngredientsFragment
import com.example.recipes.fragments.RecipeInstructionsFragment
import com.example.recipes.model.RecipeDetail
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import java.util.stream.Collectors.toList


class RecipeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var manager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        manager = RequestManager(baseContext)
        val intent: Intent = intent
        val recipeId: String? = intent.getStringExtra("recipeId")
        if (recipeId != null) {
            manager.getDetailsForRecipe(recipeId, object : RecipeDetailCallback {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onSuccess(result: RecipeDetail) {
                    setRecipeDetailsOnView(result)
                    //todo: add listener for nutrition
                }
            })
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val ft = supportFragmentManager

        ft.beginTransaction().add(R.id.fragmentContainerRecipeHeader, RecipeHeaderFragment())
            .commit()
        ft.beginTransaction()
            .add(R.id.fragmentContainerRecipeIngredients, RecipeIngredientsFragment()).commit()
        ft.beginTransaction()
            .add(R.id.fragmentContainerRecipeInstructions, RecipeInstructionsFragment()).commit()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_setting -> Toast.makeText(
                    applicationContext,
                    "Clicked Settings",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_randomize -> Toast.makeText(
                    applicationContext,
                    "Clicked Randomize",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setIngredientsText(result: RecipeDetail) {
        val ingredientsTextView: TextView = findViewById(R.id.fragment_recipe_ingredients_text_view)
        val ingredients: MutableList<String> =
            result.extendedIngredients.stream()
                .map { e ->
                    e.original
                }.collect(toList())
        ingredients.set(0, "* " + ingredients.get(0))
        val ingredientsText: String = ingredients.joinToString("\n* ")
        ingredientsTextView.setText(ingredientsText)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setInstructionsText(result: RecipeDetail) {
        val instructionsTextView: TextView =
            findViewById(R.id.fragment_recipe_instructions_text_view)
        var instructionsText = result.instructions
        var instructionsList = instructionsText.split("\n")

        var stepCounter = 0;
        val instructionsTextSeperated: MutableList<String> =
            instructionsList.stream()
                .map { e ->
                    stepCounter++
                    return@map "Step $stepCounter \n$e";
                }.collect(toList())

        val instructionsTextJoined: String = instructionsTextSeperated.joinToString("\n")
        instructionsTextView.setText(instructionsTextJoined)
    }

    fun setReadInMinutesText(result: RecipeDetail) {
        val readyInMinutesText: TextView =
            findViewById(R.id.fragment_recipe_ready_in_minutes_text_view)
        val readyInMinutes: String = result.readyInMinutes
        readyInMinutesText.setText(readyInMinutes)
    }

    fun setRecipeImage(result: RecipeDetail) {
        val recipeImageView: ImageView = findViewById(R.id.fragment_recipe_image_View)
        val imageUrl: String = result.image
        Picasso.get().load(imageUrl).into(recipeImageView)
    }

    fun setRecipeTitle(result: RecipeDetail) {
        val recipeTitle: TextView = findViewById(R.id.fragment_recipe_title_text_view)
        val recipeTitleText: String = result.title
        recipeTitle.setText(recipeTitleText)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setRecipeDetailsOnView(result: RecipeDetail) {
        setIngredientsText(result)
        setInstructionsText(result)
        setReadInMinutesText(result)
        setRecipeImage(result)
        setRecipeTitle(result)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}