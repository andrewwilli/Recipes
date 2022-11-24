package com.example.recipes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.api.RequestManager
import com.example.recipes.callbacks.RecipeCallback
import com.example.recipes.model.Recipe


class RecipeFragment : Fragment() {

    private var columnCount = 1
    lateinit var manager: RequestManager
    lateinit var adapter: RecipeRecyclerViewAdapter
    var searchString: String = ""
    var searchOffset: Int = 0
    var pageSize: Int = 5
    var cuisine: String = "All"

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
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = RecipeRecyclerViewAdapter(mutableListOf())
                setAdapterReference(adapter as RecipeRecyclerViewAdapter)
                createRequestManager(container)
                addInfiniteScrollListenerToView(view, adapter as RecipeRecyclerViewAdapter)
                loadInitialData()
            }
        }
        return view
    }

    private fun setAdapterReference(adapter: RecipeRecyclerViewAdapter) {
        this.adapter = adapter
    }

    private fun loadInitialData() {
        manager.getRandomRecipes(object : RecipeCallback {
            override fun onSuccess(result: MutableList<Recipe>) {
                adapter.setItems(result)
            }
        })
    }

    private fun createRequestManager(container: ViewGroup?) {
        if (container != null) {
            manager = RequestManager(container.getContext())
        }
    }

    private fun addInfiniteScrollListenerToView(
        view: RecyclerView,
        adapter: RecipeRecyclerViewAdapter
    ) {
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItems().size - 1) {
                    if (searchString.isEmpty() && cuisine === "All") {
                        manager.getRandomRecipes(object : RecipeCallback {
                            override fun onSuccess(result: MutableList<Recipe>) {
                                adapter.appendItems(result)
                            }
                        })
                    } else {
                        searchOffset += pageSize
                        manager.loadRecipesFiltered(
                            searchString,
                            cuisine,
                            pageSize,
                            searchOffset,
                            object : RecipeCallback {
                                override fun onSuccess(result: MutableList<Recipe>) {
                                    adapter.appendItems(result)
                                }
                            });
                    }
                }
            }
        })
    }

    fun randomize(){
        searchString = ""
        cuisine = "All"
        loadInitialData()
    }

    fun setCurrentSearchString(text: String) {
        if(text!=searchString) {
            searchString = text
            searchOffset = 0
            loadFilteredRecipes()
        }
    }

    fun setCurrentCuisine(cuisine: String) {
        this.cuisine = cuisine
        loadFilteredRecipes()
    }

    fun loadFilteredRecipes() {
        if (searchString.length == 0 && cuisine == "All") {
            loadInitialData()
        } else {
            manager.loadRecipesFiltered(
                searchString,
                cuisine,
                pageSize,
                0,
                object : RecipeCallback {
                    override fun onSuccess(result: MutableList<Recipe>) {
                        adapter.setItems(result)
                    }
                })
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            RecipeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

}
