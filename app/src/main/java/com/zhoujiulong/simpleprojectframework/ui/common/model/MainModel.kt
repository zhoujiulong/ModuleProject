package com.zhoujiulong.simpleprojectframework.ui.common.model

import com.zhoujiulong.baselib.http.HttpUtil
import com.zhoujiulong.simpleprojectframework.base.BaseModel
import com.zhoujiulong.simpleprojectframework.ui.common.service.MainService

class MainModel : BaseModel<MainService>() {

    override fun initService() {
        mService = HttpUtil.getService(MainService::class.java)
    }

}















