package com.example.my_shopping

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


private val <E> java.util.ArrayList<E>.product_price: Any
    get() {
        TODO("Not yet implemented")
    }

class Cart : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var headerTextView: TextView
    private lateinit var totalItemsTextView: TextView
    private lateinit var productAdapter: ProductAdapter
    private val cartProductList: ArrayList<ItemProduct> = ArrayList()

    @SuppressLint("MissingPermission", "ResourceType", "CutPasteId", "MissingInflatedId",
        "SetTextI18n"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_details)
        supportActionBar?.setTitle("My Shopping Cart")

        var totalPrice = 0.0;
        var totalItems = 0;
        for (i in 0 until MainActivity.orderArrayList.size) {
            if (MainActivity.orderArrayList[i].count() > 0) {
                cartProductList.add(MainActivity.orderArrayList[i])
                totalItems += MainActivity.orderArrayList[i].count()
                totalPrice += MainActivity.orderArrayList[i].product_price * MainActivity.orderArrayList[i].count()
            }
        }

        headerTextView = findViewById(R.id.totalItems)
        headerTextView.text = "My Shopping Cart"

        val formattedPrice = String.format("%.2f", totalPrice)

        totalItemsTextView = findViewById(R.id.totalItems)
        totalItemsTextView.text = "Total Items: $totalItems\nTotal Price: $formattedPrice"


        val CHANNEL_ID = "my_channel_id"
        val notificationId = 0


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }


        val notificationContent = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Order Placed")
            .setContentText("Your order has been placed successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


        var placeOrderButton = findViewById<TextView>(R.id.placeOrderButton)
        placeOrderButton.setOnClickListener {

            totalItemsTextView.text = "Total Items: 0\nTotal Price: 0.0"
            productAdapter.notifyDataSetChanged()
            Toast.makeText(this@Cart, "Thanks for your order", Toast.LENGTH_SHORT).show()

            MainActivity.orderArrayList.add(cartProductList)

            for (i in 0 until MainActivity.orderArrayList.size) {
            }

            val context = this@Cart
            if (context is Activity) {
                context.finish()
                context.overridePendingTransition(0, 0)
            }
            val intent = Intent(context, Cart::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(notificationId, notificationContent)
        }

        recyclerView = findViewById(R.id.cartViewCategory)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this@Cart, 2)
        productAdapter = ProductAdapter(cartProductList)
        recyclerView.adapter = productAdapter

        productAdapter.onClickItem = {
            val intent = Intent(this@Cart, ItemProduct::class.java)
            MainActivity.itemObject = it
            intent.putExtra("product", it)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        productAdapter?.notifyDataSetChanged()
    }
}

private operator fun Any.times(count: Int): Byte {
    TODO("Not yet implemented")
}

fun <E> ArrayList<E>.add(element: ArrayList<E>) {

}
