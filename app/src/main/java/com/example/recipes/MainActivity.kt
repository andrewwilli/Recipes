package com.example.recipes

import android.content.ClipData.Item
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.recipes.fragments.RecipeFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    var searchView: SearchView? = null
    var checkedItem: MenuItem? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        createButtonListener()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun createButtonListener() {
        val recipeFragment: RecipeFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as RecipeFragment
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                checkedItem?.setChecked(false)
                println(checkedItem)
                println(searchView)
                searchView?.setQuery("", false)
                searchView?.clearFocus()
                searchView?.setIconified(true)
                invalidateOptionsMenu()
                recipeFragment.randomize()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val recipeFragment: RecipeFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as RecipeFragment
        menuInflater.inflate(R.menu.top_bar_menu, menu)

        val search: MenuItem? = menu?.findItem(R.id.top_bar_search)
        val searchView = search?.actionView as SearchView
        this.searchView = searchView

        searchView.queryHint = "Search Something!"

        searchView.setOnSearchClickListener {
            searchView.setQuery(
                recipeFragment.searchString,
                false
            )
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchString: String?): Boolean {
                if (searchString != null) {
                    recipeFragment.setCurrentSearchString(searchString)
                }
                search.collapseActionView();
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.length == 0) {
                        recipeFragment.setCurrentSearchString("")
                    }
                }
                return true;
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    fun cuisineSelected(item: MenuItem, recipeFragment: RecipeFragment) {
        val cuisines = applicationContext.resources.getStringArray(R.array.cuisines).asList()
        if (cuisines.contains(item.title)) {
            item.setChecked(!item.isChecked)
            checkedItem = item
            recipeFragment.setCurrentCuisine(item.title as String)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val recipeFragment: RecipeFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as RecipeFragment

        cuisineSelected(item, recipeFragment)

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
