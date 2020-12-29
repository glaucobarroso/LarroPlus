package com.larro.plus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.larro.plus.callbacks.ProfileCallback
import com.larro.plus.net.facebook.ProfileRequest

class LoginActivity : AppCompatActivity() {

    private val fbCallbackManager : CallbackManager = CallbackManager.Factory.create()
    companion object {
        val LOGIN_SUCESS: Int = 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        val loginButton = findViewById<LoginButton>(R.id.fb_login_button)
        loginButton.registerCallback(fbCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    Toast.makeText(
                        applicationContext, "FacebookCallback<LoginResult> onSuccess",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }

                override fun onCancel() {
                    Toast.makeText(
                        applicationContext, "FacebookCallback<LoginResult> onCancel",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(
                        applicationContext, "FacebookCallback<LoginResult> onError",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

    }
}