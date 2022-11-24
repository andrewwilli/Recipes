package com.example.recipes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.recipes.fragments.RecipeFragment


class MainActivity : AppCompatActivity() {

    var searchView: SearchView? = null
    var checkedItem: MenuItem? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createButtonListener()
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

        return super.onOptionsItemSelected(item)
    }

}
