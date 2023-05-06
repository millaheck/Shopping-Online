package com.example.my_shopping

//KOTLIN SHOPPING ONLINE PROJECT

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var electronicsButton: Button
    private lateinit var mensButton: Button
    private lateinit var womensButton: Button
    private lateinit var jeweleryButton: Button

    var currentActivity: Activity = this

    companion object {
        var itemObject: ItemProduct? = null

        // Declare arrayList as public
        @JvmStatic
        public var itemProductList: ArrayList<ItemProduct> = ArrayList()

        @JvmStatic
        public var userArrayList: ArrayList<UserInformation> = ArrayList()

        @JvmStatic
        public var orderArrayList: ArrayList<ArrayList<ItemProduct>> = ArrayList()

        @JvmStatic
        public var currentUserId: Int = -1

        @JvmStatic
        public var isLoggedIn: Boolean = false
    }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setTitle("Camilla Abreu")

        val db = Database(this@MainActivity)
        val cursor = db.readableDatabase.rawQuery("SELECT COUNT(*) FROM mytable", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        val isTableEmpty = count == 0
        db.close()

        if (isTableEmpty) {
            db.insertLoggedInStatus(false, -1)
            Intent(this, SignInActivity::class.java).also {
                Toast.makeText(this@MainActivity, "Please sign in", Toast.LENGTH_LONG).show()
                startActivity(it)
            }
        }

        val db1 = Database(this@MainActivity)
        val loggedInStatus = db1.getLoggedInStatus("status")
        if (loggedInStatus != null) {
            currentUserId = loggedInStatus.first
            isLoggedIn = loggedInStatus.second

            if (!isLoggedIn && currentUserId == -1) {
                Intent(this, SignInActivity::class.java).also {
                    Toast.makeText(this@MainActivity, "Please sign in", Toast.LENGTH_LONG).show()
                    startActivity(it)
                }
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://fakestoreapi.com/products")
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
                val json = response.body?.string()

                val gson = Gson()
                val itemType = object : TypeToken<List<ItemProduct>>() {}.type
                val itemProductList: List<ItemProduct> = gson.fromJson(json, itemType)

                MainActivity.itemProductList = ArrayList(itemProductList)

                recycler = findViewById(R.id.product_list)
                recycler.setHasFixedSize(true)
                recycler.layoutManager = GridLayoutManager(this@MainActivity, 2)

                adapter.onClickItem = {
                    val intent = Intent(this@MainActivity, ItemProduct::class.java)
                    intent.putExtra("Product", it)
                    startActivity(intent)
                }

                electronicsButton = findViewById(R.id.category_electronics)
                electronicsButton.setOnClickListener {
                    val intent = Intent(this@MainActivity, Category::class.java)
                    intent.putExtra("electronics")
                    startActivity(intent)
                }

                mensButton = findViewById(R.id.category_mens)
                mensButton.setOnClickListener {
                    val intent = Intent(this@MainActivity, Category::class.java)
                    intent.putExtra("men's clothing")
                    startActivity(intent)
                }

                womensButton = findViewById(R.id.category_womens)
                womensButton.setOnClickListener {
                    val intent = Intent(this@MainActivity, Category::class.java)
                    intent.putExtra("women's clothing")
                    startActivity(intent)
                }

                jeweleryButton = findViewById(R.id.category_womens)
                jeweleryButton.setOnClickListener {
                    val intent = Intent(this@MainActivity, Category::class.java)
                    intent.putExtra("jewelery")
                    startActivity(intent)
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "No connection, internet failure.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://fakestoreapi.com/users")
                    .build()

                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
                val json = response.body?.string()

                val gson = Gson()
                val itemType = object : TypeToken<List<UserInformation>>() {}.type
                val userProductList: List<UserInformation> = gson.fromJson(json, itemType)

                userArrayList = ArrayList(userProductList)
                userArrayList.add(
                    UserInformation(
                        userArrayList[0].id,
                        userArrayList[0].email,
                        "admin",
                        "admin",
                        userArrayList[0].name,
                        userArrayList[0].address,
                        userArrayList[0].phone,
                    )
                )

            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "No connection, internet failure.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        try {
            super.onCreateOptionsMenu(menu)
            menuInflater.inflate(R.menu.main_menu, menu)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selected = "";
        when (item.itemId) {
            R.id.option_view_profile -> selected = "profile"
            R.id.option_view_cart -> selected = "cart"
            R.id.option_view_history -> selected = "summary"
            R.id.option_logout -> selected = "logout"
        }
        if (selected == ("profile")) {
            val intent = Intent(this@MainActivity, UserProfile::class.java)
            startActivity(intent)
        } else if (selected == ("cart")) {
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        } else if (selected == ("summary")) {
            val intent = Intent(this@MainActivity, OrderSummary::class.java)
            startActivity(intent)
        } else if (selected == ("logout")) {
            val db = Database(this@MainActivity)
            db.updateLoggedInStatus("status", -1, false)
            Intent(this, SignInActivity::class.java).also {
                Toast.makeText(this@MainActivity, "Sign In", Toast.LENGTH_LONG).show()
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

private fun Intent.putExtra(s: String) {
    TODO("Not yet implemented")
}
