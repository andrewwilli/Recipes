package com.example.recipes

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recipes.Listeners.RandomAPIResponseListener
import com.example.recipes.fragments.RandomMealAdapter
import com.example.recipes.model.RandomRecipe
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var manager : RequestManager
    lateinit var toggle : ActionBarDrawerToggle
    var recyclerView : RecyclerView = findViewById(R.id.fragmentContainerView4)
    var tags: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        manager =  RequestManager(this)
        manager.getRandomRecipes(listener,tags)


        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){


                R.id.nav_setting -> Toast.makeText(applicationContext,"Clicked Settings",Toast.LENGTH_SHORT).show()
                R.id.nav_randomize -> Toast.makeText(applicationContext,"Clicked Randomize",Toast.LENGTH_SHORT).show()
            }

            true


        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){

            return true


        }
        return super.onOptionsItemSelected(item)
    }

    private val listener: RandomAPIResponseListener = object : RandomAPIResponseListener {
        override fun didFetch(response: List<RandomRecipe?>?, message: String?) {
            recyclerView.setHasFixedSize(true)
            recyclerView.setLayoutManager(
                StaggeredGridLayoutManager(
                    1,
                    LinearLayoutManager.VERTICAL
                )
            )
           var adapter = RandomMealAdapter(this@MainActivity,
                response as List<RandomRecipe>
           )
            recyclerView.setAdapter(adapter)

        }

        override fun didError(message: String?) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

}

