package com.zhoujiulong.simpleprojectframework.ui.account.model

import com.zhoujiulong.baselib.http.HttpUtil
import com.zhoujiulong.simpleprojectframework.base.BaseModel
import com.zhoujiulong.simpleprojectframework.ui.home.service.HomeFragmentService

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:32
 */
class AccountFragmentModel : BaseModel<HomeFragmentService>() {

    override fun initService() :HomeFragmentService {
        return HttpUtil.getService(HomeFragmentService::class.java)
    }

}
