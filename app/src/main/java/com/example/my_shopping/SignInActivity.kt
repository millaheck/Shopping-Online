package com.example.my_shopping

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

private val String.password: Any
    get() {
        TODO("Not yet implemented")
    }

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    var usrName: String? = null
    var pwd: String? = null
    var usrNameField: EditText? = null
    var pwdField: EditText? = null
    var showPassword: ImageView? = null
    var loginBtn: Button? = null
    var userObject: UserProfile? = null
    var currentActivity: Activity = this

    companion object {
        var userObject: ItemProduct? = null
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar!!.title = "Login"

        val button = findViewById<View>(R.id.signInButton) as Button
        loginBtn = button
        button.setOnClickListener(this)
        val editText = findViewById<View>(R.id.name) as EditText
        usrNameField = editText
        editText.setOnClickListener(this)
        usrName = usrNameField!!.text.toString()
        val editText2 = findViewById<View>(R.id.password) as EditText
        pwdField = editText2
        editText2.setOnClickListener(this)
        pwd = pwdField!!.text.toString()

        val parentLayout = findViewById<View>(android.R.id.content)
        parentLayout.setOnTouchListener { v, event ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            parentLayout.requestFocus()
            false
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.signInButton) {
            if (usrNameField!!.text.toString().isEmpty()) {
                usrNameField!!.error = "Username required"
                usrNameField!!.requestFocus()
                return
            } else if (pwdField!!.text.toString().isEmpty()) {
                pwdField!!.error = "Password required"
                pwdField!!.requestFocus()
                return
            } else if (!isEmailAndPasswordValid(usrNameField!!.text.toString())) {
                usrNameField!!.error = "Invalid password or username"
                usrNameField!!.requestFocus()
                return
            } else if (isEmailAndPasswordValid(usrNameField!!.text.toString())) {

                val db = Database(this@SignInActivity)
                db.updateLoggedInStatus("status", userObject!!.id, true)

                MainActivity.currentUserId = userObject!!.id
                MainActivity.isLoggedIn = true

                Toast.makeText(this, "Welcome to My_Shopping", Toast.LENGTH_SHORT).show()
                currentActivity.finish()

            }
        } else if (view.id == R.id.signInButton) {
            startActivity(Intent(this, SignUpActivity::class.java))
        } else if (view.id == R.id.password) {
            if (pwdField!!.transformationMethod != HideReturnsTransformationMethod.getInstance()) {
                pwdField!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showPassword!!.setImageResource(R.drawable.hide)
            } else {
                pwdField!!.transformationMethod = PasswordTransformationMethod.getInstance()
                showPassword!!.setImageResource(R.drawable.unhide)
            }
        }
    }

    fun isEmailAndPasswordValid(userName: String): Boolean {
        for (userMain in MainActivity.userArrayList) {
            if (userMain.username == userName) {

                this.userObject!!.id = userMain.id
                this.userObject!!.email = userMain.email
                this.userObject!!.name = userMain.name
                this.userObject!!.username = userMain.username!!
                this.userObject!!.password = userMain.password
                this.userObject!!.phone = userMain.phone
                this.userObject!!.address = userMain.address

                return isPasswordValid(pwdField!!.text.toString())
            }
        }
        return false
    }

    fun isPasswordValid(password: String?): Boolean {
        return usrName!!.password == pwdField!!.text.toString()
    }
}
