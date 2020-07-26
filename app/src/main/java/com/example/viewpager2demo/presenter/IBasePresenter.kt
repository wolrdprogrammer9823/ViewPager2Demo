package com.example.viewpager2demo.presenter

interface IBasePresenter<T> {

    fun attach(mView: T)

    fun detach()

    fun onResume()
}