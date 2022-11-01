package com.example.recipes.activities

import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import com.example.recipes.fragments.HomeSearchBarFragment
import android.os.Bundle


import com.example.recipes.R

class HomeActivity : AppCompatActivity() {
    private var homeSearchBarFragment: HomeSearchBarFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeSearchBarFragment = HomeSearchBarFragment.create()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.home_search_bar_container, homeSearchBarFragment!!)
            .commit()
    }
}