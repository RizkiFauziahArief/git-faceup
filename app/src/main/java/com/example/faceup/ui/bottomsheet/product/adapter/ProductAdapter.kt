package com.example.faceup.ui.bottomsheet.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.faceup.R
import com.example.faceup.utils.Product

class ProductAdapter(private val listproduct: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgPhoto: ImageView = itemView.findViewById(R.id.img_product)
        val tvName: TextView = itemView.findViewById(R.id.tv_ProductName)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_productDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_product, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listproduct[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvDescription.text = description

    }

    override fun getItemCount(): Int = listproduct.size
}