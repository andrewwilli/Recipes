package com.example.recipes.fragments

import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.databinding.FragmentItemBinding
import com.example.recipes.model.Recipe
import com.squareup.picasso.Picasso
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * TODO: Replace the implementation with code for your data type.
 */
class MyRecipeRecyclerViewAdapter(
    private var values: MutableList<Recipe>
) : RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemBinding.inflate(
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
        holder.itemView.setOnClickListener {
            //todo: open new activity of recipe
            //println("was clicked")
           // println(item.id)
        }
        Picasso.get().load(item.image).into(holder.imageView)
        holder.favorites.setOnClickListener{
            //todo: save in favorites
            //Toast.makeText(, "Saved to Favorites", Toast.LENGTH_SHORT)
            //println(item.id)
            saveFavorites(item.id)
        }
    }

    override fun getItemCount(): Int = values.size

    fun setItems(values: MutableList<Recipe>) {
        this.values = values
        notifyDataSetChanged()
    }


    fun appendItems(values: List<Recipe>) {
        this.values.addAll(values)
        notifyDataSetChanged()
    }

    fun getItems(): List<Recipe> {
        return values
    }
    fun saveFavorites(imageId:String){
        try {

            if(Environment.getExternalStorageDirectory().exists()){
            val root = File(Environment.getExternalStorageDirectory().toString()+"/Favorites.txt")

            if (!root.exists()){
            root.createNewFile()
            }
            val fileWriter = FileWriter(root, true)

            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(imageId + "\n")
            bufferedWriter.close()

           }
        } catch(e: IOException){

        }



    }
    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.itemName
        val readyInMinutes: TextView = binding.itemDuration
        val imageView: ImageView = binding.itemImage
        val favorites : ImageButton = binding.itemFavorites

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

}