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
import com.example.recipes.model.Recipe


/**
 * A fragment representing a list of Items.
 */
class RecipeFragment : Fragment() {

    private var columnCount = 1
    lateinit var manager: RequestManager

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
                adapter = MyRecipeRecyclerViewAdapter(mutableListOf())
                createRequestManager(container, adapter as MyRecipeRecyclerViewAdapter)
                addInfiniteScrollListenerToView(view, adapter as MyRecipeRecyclerViewAdapter)
                loadInitialData()
            }
        }
        return view
    }

    private fun loadInitialData() {
        manager.getRandomRecipes()
    }

    private fun createRequestManager(container: ViewGroup?, adapter: MyRecipeRecyclerViewAdapter) {
        if (container != null) {
            manager = RequestManager(
                container.getContext(),
                adapter
            )
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
                    manager.loadMoreRecipes()
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