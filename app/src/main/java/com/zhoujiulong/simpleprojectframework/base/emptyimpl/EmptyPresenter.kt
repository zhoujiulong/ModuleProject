package com.zhoujiulong.simpleprojectframework.base.emptyimpl

import com.zhoujiulong.simpleprojectframework.base.BasePresenter

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:34
 * 空类，占位用
 */
class EmptyPresenter : BasePresenter<EmptyModel, EmptyContract.IView>(), EmptyContract.IPresenter {

    override fun initModel(): EmptyModel {
        return EmptyModel()
    }

}
