package com.zhoujiulong.simpleprojectframework.ui.home.service


import com.zhoujiulong.baselib.http.response.DataResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:33
 */
interface HomeFragmentService {

    @POST("chipset-web/loginapp")
    fun login2(@QueryMap params: Map<String, String>): Call<DataResponse<String>>

}
