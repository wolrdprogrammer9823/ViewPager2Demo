package com.example.viewpager2demo.view

interface ILoginView {

    fun showLoadingView()

    fun loginSuccess()

    fun loginFailure(errorCode: Int)
}