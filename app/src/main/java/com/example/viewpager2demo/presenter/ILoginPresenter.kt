package com.example.viewpager2demo.presenter

interface ILoginPresenter<T> : IBasePresenter<T> {

    fun login(username: String, userPassword: String)
}