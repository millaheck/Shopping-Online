package com.example.my_shopping

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OrderAdapter(private val productList: ArrayList<ItemProduct>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var onClickItem: ((Products) -> Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.name)
        val categoryView: TextView = view.findViewById(R.id.category)
        val priceView: TextView = view.findViewById(R.id.price)
        val imageView: ImageView = view.findViewById(R.id.product_Image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.information_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.titleView.text = product.name
        holder.categoryView.text = product.category
        holder.priceView.text = product.price.toString() + " $"

        val imageUrl = product.image
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(product)
        }
    }
}

private fun Any?.invoke(product: ItemProduct) {
    TODO("Not yet implemented")
}
