package com.example.recipes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.recipes.api.RequestManager
import com.example.recipes.callbacks.RecipeDetailCallback
import com.example.recipes.fragments.FavoritesFragment
import com.example.recipes.model.RecipeDetail
import com.google.android.material.navigation.NavigationView

class FavoritesActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var manager: RequestManager
    val recipeactivity = RecipeActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        manager = RequestManager(baseContext)
        val intent: Intent = intent
        val recipeId: String? = intent.getStringExtra("recipeId")


        if (recipeId != null) {
            manager.getDetailsForRecipe(recipeId, object : RecipeDetailCallback {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onSuccess(result: RecipeDetail) {
                    recipeactivity.setRecipeDetailsOnView(result)
                    //todo: add listener for nutrition
                }
            })
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val sFM = supportFragmentManager
        sFM.beginTransaction().add(R.id.fragmentContainerFavorites, FavoritesFragment()).commit()
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

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
}