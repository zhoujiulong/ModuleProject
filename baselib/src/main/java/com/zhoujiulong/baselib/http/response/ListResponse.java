package com.zhoujiulong.baselib.http.response;

import java.io.Serializable;
import java.util.List;

/**
 * Author : yk
 * Email : 754667445@qq.com
 * Time : 2017/08/17
 * 描述 :
 */

public class ListResponse<T> extends BaseResponse implements Serializable {

    public List<T> data;

}
