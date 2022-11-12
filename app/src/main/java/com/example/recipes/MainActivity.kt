package com.example.recipes

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.recipes.fragments.RecipeFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val recipeFragment: RecipeFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as RecipeFragment
        menuInflater.inflate(R.menu.top_bar_menu, menu)

        val search: MenuItem? = menu?.findItem(R.id.top_bar_search)
        val searchView: SearchView = search?.actionView as SearchView
        searchView.queryHint = "Search Something!"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //todo: cant submit a empty text
            override fun onQueryTextSubmit(searchString: String?): Boolean {
                if (searchString != null) {
                    recipeFragment.setCurrentSearchString(searchString)
                }
                search.collapseActionView();
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}

