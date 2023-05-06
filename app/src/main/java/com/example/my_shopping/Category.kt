package com.example.my_shopping

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private val <E> java.util.ArrayList<E>.category: Any
    get() {
        TODO("Not yet implemented")
    }

class Category : AppCompatActivity() {

    public var products: ArrayList<ItemProduct> = ArrayList()
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var categoryHeader: TextView
    private lateinit var productAdapter: ProductAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category)
        supportActionBar?.setTitle("My_Shopping")

        val selected = intent.getStringExtra("Selected")

        categoryHeader = findViewById(R.id.category)
        categoryHeader.text = "Category: $selected"

        for (i in 0 until MainActivity.orderArrayList.size) {
            if (MainActivity.orderArrayList[i].category == categoryHeader) {
                products.add(MainActivity.orderArrayList[i])
            }
        }

        itemsRecyclerView = findViewById(R.id.category_list)
        itemsRecyclerView.setHasFixedSize(true)
        itemsRecyclerView.layoutManager = GridLayoutManager(this@Category, 2)
        productAdapter = ProductAdapter(products)
        itemsRecyclerView.adapter = productAdapter

        productAdapter.onClickItem = {
            val intent = Intent(this@Category, ItemProduct::class.java)
            intent.putExtra("product", it)
            startActivity(intent)
        }
    }
}
