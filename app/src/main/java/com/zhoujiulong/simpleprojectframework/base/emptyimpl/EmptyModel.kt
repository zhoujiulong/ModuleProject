package com.zhoujiulong.simpleprojectframework.base.emptyimpl

import com.zhoujiulong.baselib.http.HttpUtil
import com.zhoujiulong.simpleprojectframework.base.BaseModel

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:32
 * 空类，占位用
 */
class EmptyModel : BaseModel<EmptyService>() {

    override fun initService(): EmptyService {
        return HttpUtil.getService(EmptyService::class.java)
    }
}
