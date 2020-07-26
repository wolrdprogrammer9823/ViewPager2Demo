package com.example.viewpager2demo.model

import android.util.Log

class LoginModelImpl : ILoginModel {

    override fun login(username: String, userPassword: String): Boolean {

        Log.d("model", "correct_name:${username == "heart"}")
        Log.d("model", "correct_password:${userPassword == "123456"}")
        return username == "heart" && userPassword == "123456"
    }
}