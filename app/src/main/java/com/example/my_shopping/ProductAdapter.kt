package com.example.my_shopping

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private fun ItemProduct.count() {

}

private operator fun Any.compareTo(i: Int): Int {
    TODO("Not yet implemented")
}

private operator fun Any.inc() {

}

private operator fun Any.dec() {

}

class ProductAdapter(private val Products: ArrayList<ItemProduct>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var onClickItem: ((ItemProduct) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.product_title)
        val category: TextView = itemView.findViewById(R.id.category)
        val price: TextView = itemView.findViewById(R.id.price)
        val image: ImageView = itemView.findViewById(R.id.product_Image)
        val countButton: TextView = itemView.findViewById(R.id.countButton)
        val plusButton: ImageView = itemView.findViewById(R.id.plusButton)
        val minusButton: ImageView = itemView.findViewById(R.id.minusButton)
        val removeButton: TextView = itemView.findViewById(R.id.removeButton)
        val mainLayout: ConstraintLayout = itemView.findViewById(R.id.item_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.information_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = Products[position]
        holder.title.text = product.name
        holder.category.text = product.category
        holder.price.text = product.price.toString() + "$"

        if (product.count() > 0) {
            holder.countButton.visibility = View.VISIBLE
            holder.plusButton.visibility = View.VISIBLE
            holder.minusButton.visibility = View.VISIBLE
            holder.removeButton.visibility = View.VISIBLE
            holder.countButton.text = product.count().toString()
        } else if (product.count() <= 0) {
            holder.countButton.visibility = View.INVISIBLE
            holder.plusButton.visibility = View.INVISIBLE
            holder.minusButton.visibility = View.INVISIBLE
            holder.removeButton.visibility = View.INVISIBLE
            holder.countButton.text = product.count().toString()
        }

        val url = product.image
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.image)

        holder.plusButton.setOnClickListener {
            holder.countButton.text = product.count().toString()

            val context = holder.itemView.context
            if (context is Activity) {
                context.finish()
                context.overridePendingTransition(0, 0)
            }
            val intent = Intent(context, Cart::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.minusButton.setOnClickListener {

            if (product.count() <= 0) {
                holder.countButton.visibility = View.INVISIBLE
                holder.plusButton.visibility = View.INVISIBLE
                holder.minusButton.visibility = View.INVISIBLE
                holder.removeButton.visibility = View.INVISIBLE

                val context = holder.itemView.context
                if (context is Activity) {
                    context.finish()
                    context.overridePendingTransition(0, 0)
                }
                val intent = Intent(context, Cart::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            }
            holder.countButton.text = product.count().toString()

            val context = holder.itemView.context
            if (context is Activity) {
                context.finish()
                context.overridePendingTransition(0, 0)
            }
            val intent = Intent(context, Cart::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        holder.removeButton.setOnClickListener() {
            holder.countButton.visibility = View.INVISIBLE
            holder.plusButton.visibility = View.INVISIBLE
            holder.minusButton.visibility = View.INVISIBLE
            holder.removeButton.visibility = View.INVISIBLE
            holder.countButton.text = product.count().toString()

            val context = holder.itemView.context
            if (context is Activity) {
                context.finish()
                context.overridePendingTransition(0, 0)
            }
            val intent = Intent(context, Cart::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(product)
        }
    }
}


