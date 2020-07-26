package com.example.viewpager2demo.presenter

abstract class BasePresenterImpl<T> {

    var mView :T? = null

    open fun attach(view: T) {
        mView = view
    }

    open fun detach() {
        mView = null
    }

    open fun onResume() {}
}