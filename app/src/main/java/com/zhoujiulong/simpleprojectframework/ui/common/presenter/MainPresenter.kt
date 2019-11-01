package com.zhoujiulong.simpleprojectframework.ui.common.presenter

import com.zhoujiulong.simpleprojectframework.base.BasePresenter
import com.zhoujiulong.simpleprojectframework.ui.common.contract.MainContract
import com.zhoujiulong.simpleprojectframework.ui.common.model.MainModel

class MainPresenter : BasePresenter<MainModel, MainContract.IView>(), MainContract.IPresenter {

    override fun initModel(): MainModel {
        return MainModel()
    }


}















