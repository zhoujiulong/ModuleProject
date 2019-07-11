package com.zhoujiulong.baselib.utils;

/**
 * @author zhoujiulong
 * @createtime 2019/4/4 13:59
 */
public class BeanUtil {

    /**
     * 复制对象
     */
    public static <T> T cloneBean(T object, Class<T> cls) {
        if (object == null) return null;
        String jsonObject = JsonUtil.object2String(object);
        return JsonUtil.jsonToBean(jsonObject, cls);
    }

}












