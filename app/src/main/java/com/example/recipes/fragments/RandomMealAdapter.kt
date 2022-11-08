package com.example.recipes.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.fragments.placeholder.PlaceholderContent.PlaceholderItem
import com.example.recipes.model.RandomRecipe
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

class RandomMealAdapter(
    context:Context,
    recipes: List<RandomRecipe>) :
    RecyclerView.Adapter<RandomMealAdapter.RandomMealViewHolder>() {

    var context: Context
    var recipes: List<RandomRecipe>


    init {
        this.context = context
        this.recipes = recipes

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomMealViewHolder {

        return RandomMealViewHolder(
            LayoutInflater.from(context).inflate(R.layout.fragment_item_list, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RandomMealViewHolder, position: Int) {
        holder.textView_title.setText(recipes[position].title)
        holder.textView_title.isSelected = true
        holder.textView_ready_time.setText(recipes[position].readyInMinutes.toString() +  "Min")
        Picasso.get().load(recipes[position].image).into(holder.imageView_food)


    }

    override fun getItemCount(): Int = recipes.size



    class RandomMealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView_title: TextView
        var textView_ready_time: TextView
        var textView_number: TextView
        var imageView_food: ImageView


        init {
            textView_title = itemView.findViewById(R.id.item_name)
            textView_ready_time = itemView.findViewById(R.id.item_duration)
            textView_number = itemView.findViewById(R.id.item_number)
            imageView_food = itemView.findViewById(R.id.imageView)
        }
    }
}


