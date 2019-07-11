package com.zhoujiulong.simpleprojectframework.ui.home.model

import com.zhoujiulong.baselib.http.HttpUtil
import com.zhoujiulong.baselib.http.listener.RequestListener
import com.zhoujiulong.baselib.http.response.DataResponse
import com.zhoujiulong.simpleprojectframework.base.BaseModel
import com.zhoujiulong.simpleprojectframework.ui.home.service.HomeFragmentService

import java.util.HashMap

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:32
 */
class HomeFragmentModel : BaseModel<HomeFragmentService>() {

    override fun initService() {
        mService = HttpUtil.getService(HomeFragmentService::class.java)
    }

    fun login(username: String, password: String, listener: RequestListener<DataResponse<String>>) {
        val params2 = HashMap<String, String>()
        params2["userName"] = "15270949160"
        params2["password"] = "123465"
        HttpUtil.sendRequest(ReTag, mService.login2(params2), listener)
    }
}
