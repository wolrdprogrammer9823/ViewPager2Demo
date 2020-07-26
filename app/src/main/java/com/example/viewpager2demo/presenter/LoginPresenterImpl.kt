package com.example.viewpager2demo.presenter
import com.example.viewpager2demo.model.LoginModelImpl
import com.example.viewpager2demo.view.ILoginView

class LoginPresenterImpl : BasePresenterImpl<ILoginView>(), ILoginPresenter<ILoginView> {

    private val loginModel by lazy {
        LoginModelImpl()
    }

    override fun login(username: String, userPassword: String) {

        //显示进度view
        mView?.showLoadingView()

        val result = loginModel.login(username, userPassword)

        if (result) {
            //登录成功
            mView?.loginSuccess()
        } else {
            //登录失败
            mView?.loginFailure(0x00)
        }

    }

    override fun attach(view: ILoginView) {
        super.attach(view)
        //做一些初始化操作

    }

    override fun onResume() {
        super.onResume()

    }

}