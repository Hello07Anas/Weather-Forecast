package com.example.weatherscope.view.viewFavorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherscope.R

class FavAdapter(
    private val context: Context,
    var favoritesLocation: MutableList<FavoritesLocation>,
    private val onDeleteClickListener: OnDeleteClickListener
): RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var txtFavName: TextView = itemView.findViewById(R.id.txtFavName)
        var imgDeleteFav: ImageView = itemView.findViewById(R.id.imgDeleteFav)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapter.ViewHolder {
        val layOurInflater = LayoutInflater.from(parent.context)
        val v = layOurInflater.inflate(R.layout.fav_item, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cuurentFav = favoritesLocation[position]
        holder.txtFavName.text = cuurentFav.cityName
        holder.imgDeleteFav.setOnClickListener {
            onDeleteClickListener.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return favoritesLocation.size
    }

    // TODO implement fun to delete item from file
}