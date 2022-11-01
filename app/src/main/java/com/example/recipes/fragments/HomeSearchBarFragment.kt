package com.example.recipes.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class HomeSearchBarFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        fun create(): HomeSearchBarFragment {
            return HomeSearchBarFragment()
        }
    }
}