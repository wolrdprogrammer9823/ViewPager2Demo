package com.example.viewpager2demo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.viewpager2demo.presenter.IBasePresenter

abstract class BaseMvpActivity<V, P : IBasePresenter<V>> : AppCompatActivity() {

    var presenter : P? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        presenter = initPresenter()
        presenter?.attach(this as V)
        initActivity(savedInstanceState)
    }

    override fun onResume() {

        presenter?.onResume()
        super.onResume()
    }

    override fun onDestroy() {

        presenter?.detach()
        super.onDestroy()
    }

    abstract fun initPresenter() : P

    abstract fun initActivity(savedInstanceState: Bundle?)
}