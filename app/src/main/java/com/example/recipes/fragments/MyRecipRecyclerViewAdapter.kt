package com.example.recipes.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.databinding.FragmentItemCardviewBinding
import com.example.recipes.fragments.placeholder.PlaceholderContent.PlaceholderItem
import com.example.recipes.model.RandomRecipe
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyRecipRecyclerViewAdapter(
    private var values: List<RandomRecipe>
) : RecyclerView.Adapter<MyRecipRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.title
        holder.readyInMinutes.text = item.readyInMinutes + " Min"
        Picasso.get().load(item.image).into(holder.imageView)
    }

    override fun getItemCount(): Int = values.size

    fun setItems(values: List<RandomRecipe>){
        this.values = values
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: FragmentItemCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
       // val idView: TextView = binding.itemNumber
        val nameView: TextView = binding.itemName
        val readyInMinutes: TextView = binding.itemDuration
        val imageView: ImageView = binding.itemImage

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

}