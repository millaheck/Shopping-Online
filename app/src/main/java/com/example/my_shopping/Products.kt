package com.example.my_shopping

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


private val <E> ArrayList<E>.id: Any
    get() {
        TODO("Not yet implemented")
    }

class Products : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information_product)

        supportActionBar?.setTitle("Information of Product")

        val productData = intent.getParcelableExtra<ItemProduct>("products")
        if (productData != null) {
            val productImageView: ImageView = findViewById(R.id.product_Image)
            val productTitleTextView: TextView = findViewById(R.id.product_title)
            val productPriceTextView: TextView = findViewById(R.id.price)
            val productCategoryTextView: TextView = findViewById(R.id.category)
            val productDescriptionTextView: TextView = findViewById(R.id.description)
            val btnAddToCartTextView: TextView = findViewById(R.id.add_to_cart_btn)


            val imageURL = productData.image
            Glide.with(this)
                .load(imageURL)
                .into(productImageView)

            productTitleTextView.text = productData.name
            productPriceTextView.text = "$" + productData.price
            productCategoryTextView.text = productData.category
            productDescriptionTextView.text = productData.description

            btnAddToCartTextView.setOnClickListener { view ->
                for (itemData in MainActivity.orderArrayList) {
                    if (itemData.id == productData.id) {
                        Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
            }
        }
    }
}
