package com.example.recipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.recipes.api.RequestManager
import com.example.recipes.callbacks.NutritionCallback
import com.example.recipes.model.Nutrition

class NutritionActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var manager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)
        manager = RequestManager(baseContext)
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
        val recipeId: String? = intent.getStringExtra("recipeId")
        if (recipeId != null) {
            manager.getNutritionForRecipe(recipeId, object : NutritionCallback {
                override fun onSuccess(result: Nutrition) {
                    val caloriesTextView: TextView = findViewById(R.id.caloriesTextView)
                    val carbsTextView: TextView = findViewById(R.id.carbsTextView)
                    val fatTextView: TextView = findViewById(R.id.fatTextView)
                    val proteinTextView: TextView = findViewById(R.id.proteinTextView)
                    caloriesTextView.setText(result.calories)
                    carbsTextView.setText(result.carbs)
                    fatTextView.setText(result.fat)
                    proteinTextView.setText(result.protein)
                }
            })
        }
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