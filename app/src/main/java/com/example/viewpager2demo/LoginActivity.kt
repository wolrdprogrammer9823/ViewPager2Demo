package com.example.viewpager2demo
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.viewpager2demo.presenter.ILoginPresenter
import com.example.viewpager2demo.presenter.LoginPresenterImpl
import com.example.viewpager2demo.view.ILoginView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMvpActivity<ILoginView, ILoginPresenter<ILoginView>>(), ILoginView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onContentChanged() {

        super.onContentChanged()
        btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val username = user_name_et.text?.trim().toString()
                val userPassword = user_password_et.text?.trim().toString()
                presenter?.login(username, userPassword)
            }
            else -> {}
        }
    }

    override fun initPresenter(): ILoginPresenter<ILoginView> = LoginPresenterImpl()

    override fun initActivity(savedInstanceState: Bundle?) {

    }

    override fun showLoadingView() {

    }

    override fun loginSuccess() {

        Toast.makeText(this,"loginSuccess",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun loginFailure(errorCode: Int) {

        Toast.makeText(this, "loginFailure,errorCode:${errorCode}", Toast.LENGTH_SHORT).show()
    }

}