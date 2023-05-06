package com.example.my_shopping

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderSummary : AppCompatActivity() {

    var orderTextView: TextView? = null
    var searchEditText: EditText? = null
    var searchButton: Button? = null
    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders)
        supportActionBar?.setTitle("Camilla Abreu")

        orderTextView = findViewById<TextView>(R.id.title)
        orderTextView?.text = "Total number of orders: " + MainActivity.orderArrayList.size + "\n\n"

        searchEditText = findViewById<EditText>(R.id.search_order)

        searchButton = findViewById<Button>(R.id.btn_search_order)
        searchButton!!.setOnClickListener {
            if (MainActivity.orderArrayList.size == 0) {
                Toast.makeText(
                    this@OrderSummary,
                    "You did not make an order yet.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (searchEditText!!.text.toString().isEmpty()) {
                Toast.makeText(
                    this@OrderSummary,
                    "Please enter the order id: ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (searchEditText!!.text.toString()
                    .toInt() > MainActivity.orderArrayList.size || searchEditText!!.text.toString()
                    .toInt() <= 0
            ) {
                Toast.makeText(
                    this@OrderSummary,
                    "Please enter a order id between 1 and " + MainActivity.orderArrayList.size,
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                orderTextView?.visibility = TextView.GONE
                searchButton?.visibility = Button.GONE
                searchEditText?.visibility = EditText.GONE

                System.out.println("Viewing the main size " + MainActivity.orderArrayList.size)
                System.out.println("Viewing the inner size: " + MainActivity.orderArrayList[0].size)


                ordersRecyclerView = findViewById<RecyclerView>(R.id.order_list)
                ordersRecyclerView.setHasFixedSize(true)
                ordersRecyclerView.layoutManager = GridLayoutManager(this@OrderSummary,2)
                ordersAdapter = OrderAdapter(
                    MainActivity.orderArrayList[searchEditText!!.text.toString().toInt() - 1]
                )
                ordersRecyclerView.adapter = ordersAdapter

                ordersAdapter.onClickItem = {
                    val intent = Intent(this@OrderSummary, ItemProduct::class.java)
                    intent.putExtra("product", it)
                    startActivity(intent)
                }
            }
        }
    }
}

private fun Intent.putExtra(s: String, it: Products) {
    TODO("Not yet implemented")
}
