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
import com.example.recipes.VolleyCallback
import com.example.recipes.api.RequestManager
import com.example.recipes.model.Recipe


/**
 * A fragment representing a list of Items.
 */
class RecipeFragment : Fragment(){

    private var columnCount = 1
    lateinit var manager: RequestManager
    lateinit var adapter: MyRecipeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    val x = object : VolleyCallback {
        override fun onSuccess(result: MutableList<Recipe>) {
            adapter.setItems(result)
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
                adapter = MyRecipeRecyclerViewAdapter(mutableListOf())
                createRequestManager(container)
                addInfiniteScrollListenerToView(view, adapter as MyRecipeRecyclerViewAdapter)
                loadInitialData(adapter as MyRecipeRecyclerViewAdapter)
                setAdapterReference(adapter as MyRecipeRecyclerViewAdapter)
            }
        }
        return view
    }

    fun setAdapterReference(adapter: MyRecipeRecyclerViewAdapter) {
        this.adapter=adapter
    }

    fun loadInitialData(adapter: MyRecipeRecyclerViewAdapter) {
        manager.getRandomRecipes(object : VolleyCallback {
            override fun onSuccess(result: MutableList<Recipe>) {
                adapter.setItems(result)
            }
        })
    }

    fun loadRecipesBySearchString(text: String) {
        manager.loadRecipesFilteredByTitle(text, object : VolleyCallback {
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
        adapter: MyRecipeRecyclerViewAdapter
    ) {
        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItems().size - 1) {
                    manager.getRandomRecipes(object : VolleyCallback {
                        override fun onSuccess(result: MutableList<Recipe>) {
                            adapter.appendItems(result)
                        }
                    })
                }
            }
        })
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