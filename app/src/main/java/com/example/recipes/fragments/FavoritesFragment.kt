package com.example.recipes.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.api.RequestManager
import com.example.recipes.callbacks.FavoriteCallback
import com.example.recipes.model.Recipe
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * A fragment representing a list of Items.
 */
class FavoritesFragment : Fragment() {

    private var columnCount = 1
    lateinit var manager : RequestManager
    lateinit var adapter: MyRecipeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyRecipeRecyclerViewAdapter(mutableListOf())

                if (container != null) {
                    manager = RequestManager(
                        container.getContext()

                    )

                    loadFavorites()
                }
            }
        }
        return view
    }

    private fun loadFavorites() {

       val favoritesID = fileToString()
        println(favoritesID)
        manager.getFavoriteRecipe(favoritesID,object: FavoriteCallback{
            override fun onSuccess(result: MutableList<Recipe>) {
                adapter.setItems(result)
            }
        })
        /*
        manager.getRandomRecipes(object : RecipeCallback {
            override fun onSuccess(result: MutableList<Recipe>) {
                adapter.setItems(result)
            }
        })
        */

    }
    fun fileToString(): String {
        val root = File(Environment.getExternalStorageDirectory().toString()+"/Favorites.txt")
        if (!root.exists()){
            root.createNewFile()
        }
        val fileReader = FileReader(root)
        val  reader = BufferedReader(fileReader)
        val stringBuilder = StringBuilder()
        var buffer = CharArray(10)
        while (reader.read(buffer) !== -1) {
            stringBuilder.append(String(buffer))
            buffer = CharArray(10)
        }
        val content = stringBuilder.toString()
        println(content)
        reader.close()


        return content
    }
    private fun setAdapterReference(adapter: MyRecipeRecyclerViewAdapter) {
        this.adapter = adapter
    }
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}