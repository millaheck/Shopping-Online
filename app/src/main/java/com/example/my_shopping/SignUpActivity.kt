package com.example.my_shopping

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    var user_email: String? = null
    var user_name: String? = null
    var user_password: String? = null
    var user_firstName: String? = null
    var user_lastName: String? = null
    var email_field: EditText? = null
    var name_field: EditText? = null
    var password_field: EditText? = null
    var firstName_field: EditText? = null
    var lastName_field: EditText? = null
    var show_password: ImageView? = null
    var sign_in: TextView? = null
    var signUpButton: Button? = null
    var currentActivity: Activity = this
    var new_user: UserInformation? = null

    @SuppressLint("MissingInflatedId")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)
        supportActionBar!!.title = "Register"

        val login_signup = findViewById<View>(R.id.titleDetail) as TextView
        sign_in = login_signup
        login_signup.setOnClickListener(this)

        val signUpButton = findViewById<View>(R.id.signUpButton) as Button
        signUpButton.setOnClickListener(this)

        val email_field_SignUp = findViewById<View>(R.id.email) as EditText
        email_field = email_field_SignUp

        val password_field_SignUp = findViewById<View>(R.id.passwordInput) as EditText
        password_field = password_field_SignUp

        val user_name_field_SignUp = findViewById<View>(R.id.confirmPasswordInput) as EditText
        name_field = user_name_field_SignUp

        val firstName_field_SignUp = findViewById<View>(R.id.firstNameInput) as EditText
        firstName_field = firstName_field_SignUp

        val lastName_field_SignUp = findViewById<View>(R.id.lastNameInput) as EditText
        lastName_field = lastName_field_SignUp

        val show_password_SignUp = findViewById<View>(R.id.signup_image) as ImageView
        show_password = show_password_SignUp
        show_password!!.setOnClickListener(this)

        val parentLayout = findViewById<View>(android.R.id.content)
        parentLayout.setOnTouchListener { v, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            parentLayout.requestFocus()
            false
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.signUpButton) {
            user_lastName = lastName_field!!.text.toString()
            user_firstName = firstName_field!!.text.toString()
            user_name = name_field!!.text.toString()
            user_password = password_field!!.text.toString()
            user_email = email_field!!.text.toString()

            if (firstName_field!!.text.toString().isEmpty()) {
                firstName_field!!.error = "First Name needed"
                firstName_field!!.requestFocus()
                return
            } else if (lastName_field!!.text.toString().isEmpty()) {

                lastName_field!!.error = "Last Name needed"
                lastName_field!!.requestFocus()
                return
            } else if (email_field!!.text.toString().isEmpty()) {
                email_field!!.error = "Email needed"
                email_field!!.requestFocus()
                return
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email_field!!.text.toString()).matches()) {
                email_field!!.error = "Please enter a valid email"
                email_field!!.requestFocus()
                return
            } else if (password_field!!.text.toString().isEmpty()) {
                password_field!!.error = "Password is required"
                password_field!!.requestFocus()
                return
            }else {
                user_lastName = lastName_field!!.text.toString()
                user_firstName = firstName_field!!.text.toString()
                user_name = name_field!!.text.toString()
                user_password = password_field!!.text.toString()
                user_email = email_field!!.text.toString()

                MainActivity.currentUserId = MainActivity.userArrayList.size
                new_user = UserInformation(MainActivity.userArrayList.size,user_email!!, user_name!!, user_password!!, FullName(user_firstName!!, user_lastName!!), MainActivity.userArrayList[0].address, MainActivity.userArrayList[0].phone)
                MainActivity.userArrayList.add(new_user!!)

                GlobalScope.launch(Dispatchers.Main) {
                    try {

                        val client = OkHttpClient()
                        val gson = Gson()
                        val json = gson.toJson(new_user)
                        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

                        val request = Request.Builder()
                            .url("https://fakestoreapi.com/users")
                            .post(requestBody)
                            .build()

                        val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

                        if (response.isSuccessful) {
                            val jsonResponse = response.body?.string()
                            val newUser: UserInformation = gson.fromJson(jsonResponse, UserInformation::class.java)

                        } else {
                        }

                    } catch (e: Exception) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Error: Internet not connected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                currentActivity.finish()
            }
        } else if (view.id == R.id.titleDetail) {
            currentActivity.finish()
        } else if (view.id == R.id.passwordInput) {
            if (password_field!!.transformationMethod != HideReturnsTransformationMethod.getInstance()) {
                password_field!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show_password!!.setImageResource(R.drawable.hide)
            } else {
                password_field!!.transformationMethod = PasswordTransformationMethod.getInstance()
                show_password!!.setImageResource(R.drawable.unhide)
            }
        }
    }
}
