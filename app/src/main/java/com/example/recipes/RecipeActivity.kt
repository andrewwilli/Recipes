package com.example.recipes


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.recipes.fragments.RecipeHeaderFragment
import com.example.recipes.fragments.RecipeIngredientsFragment
import com.example.recipes.fragments.RecipeInstructionsFragment
import com.google.android.material.navigation.NavigationView


class RecipeActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        val ft = supportFragmentManager

        ft.beginTransaction().add(R.id.fragmentContainerRecipeHeader, RecipeHeaderFragment()).commit()
        ft.beginTransaction().add(R.id.fragmentContainerRecipeIngredients, RecipeIngredientsFragment()).commit()
        ft.beginTransaction().add(R.id.fragmentContainerRecipeInstructions, RecipeInstructionsFragment()).commit()

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){


                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Settings", Toast.LENGTH_SHORT).show()
                R.id.nav_randomize -> Toast.makeText(applicationContext,"Clicked Randomize", Toast.LENGTH_SHORT).show()
            }

            true


        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){

            return true


        }
        return super.onOptionsItemSelected(item)
    }
}