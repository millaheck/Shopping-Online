package com.example.my_shopping

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.my_shopping.MainActivity.Companion.currentUserId

class UserProfile : AppCompatActivity() {
    lateinit var address: UserAddress
    var phone: String? = null
    var password: String? = null
    lateinit var username: String
    lateinit var name: FullName
    var email: String? = null
    var id: Int = 0
    var currentUser: UserInformation? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_user)
        supportActionBar?.setTitle("My Profile")

        var userFound : Boolean = false

        for (user in MainActivity.userArrayList) {
            if (currentUser?.id == currentUserId) {
                this.currentUser = currentUser
                userFound = true
                break
            }
        }
        if (!userFound) {
            this.currentUser = MainActivity.userArrayList[0]
            currentUserId = 0
        }

        val nameTextView: TextView = findViewById(R.id.name)
        nameTextView.text = "Name: "+ currentUser?.name?.firstname.toString() + " " + currentUser?.name?.lastname.toString()

        val emailTextView: TextView = findViewById(R.id.email)
        emailTextView.text = "Email: " + currentUser?.email.toString()

        val usernameTextView: TextView = findViewById(R.id.name)
        usernameTextView.text = "Username: "+ currentUser?.username.toString()

        val phoneTextView: TextView = findViewById(R.id.phone)
        phoneTextView.text = "Phone: "+currentUser?.phone.toString()

        val addressTextView: TextView = findViewById(R.id.address)
        addressTextView.text = "Address: \nCity: "+ currentUser?.address?.city.toString() + "\nStreet: " + currentUser?.address?.street.toString() + ", St. no: " + currentUser?.address?.number.toString() + "\nZip code:" + currentUser?.address?.zipcode.toString() + "\nLat(" + currentUser?.address?.geolocation?.lat.toString() + ") , Long(" + currentUser?.address?.geolocation?.long.toString() + ")\n"

        val profileImageView: ImageView = findViewById(R.id.product_Image)
        val avatarUrl = "https://i.pravatar.cc/300" + currentUser?.id.toString()
        Glide.with(this)
            .load(avatarUrl)
            .into(profileImageView)

        val aboutAppButton: Button = findViewById(R.id.aboutButton)
        aboutAppButton.setOnClickListener { view ->
            Toast.makeText(this, "This app is created by Camilla Abreu.", Toast.LENGTH_LONG).show()
        }
    }
}
