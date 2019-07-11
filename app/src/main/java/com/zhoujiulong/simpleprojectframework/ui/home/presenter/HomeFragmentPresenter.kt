package com.zhoujiulong.simpleprojectframework.ui.home.presenter

import com.zhoujiulong.baselib.http.listener.RequestListener
import com.zhoujiulong.baselib.http.other.RequestErrorType
import com.zhoujiulong.baselib.http.response.DataResponse
import com.zhoujiulong.simpleprojectframework.base.BasePresenter
import com.zhoujiulong.simpleprojectframework.ui.home.contract.HomeFragmentContract
import com.zhoujiulong.simpleprojectframework.ui.home.model.HomeFragmentModel

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:34
 */
class HomeFragmentPresenter : BasePresenter<HomeFragmentModel, HomeFragmentContract.IView>(),
    HomeFragmentContract.IPresenter {

    override fun initModel() {
        mModel = HomeFragmentModel()
    }

    override fun login() {
        mView.showLoading()
        mModel.login("15270949160", "123465", object : RequestListener<DataResponse<String>>() {
            override fun requestSuccess(data: DataResponse<String>) {
                mView.hideLoading()
                mView.loginSuccess()
            }

            override fun requestError(
                data: DataResponse<String>?,
                errorType: RequestErrorType,
                errorMsg: String,
                errorCode: Int
            ) {
                mView.hideLoading()
                mView.loginFaild(errorMsg)
            }
        })
    }
}
