package com.zhoujiulong.baselib.http.response;

import java.io.Serializable;

/**
 * 后台数据返回的公共部分
 * Created by 0169670 on 2017/1/9.
 */
public class BaseResponse implements Serializable {

    public int code;
    public String message;

}