package com.zhoujiulong.baselib.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/08/16
 * 描述 :
 */

public class JsonUtil {

    private JsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object Object
     * @return String
     */
    public static String object2String(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 转成bean
     *
     * @param gsonString String
     * @param cls        T
     * @return T
     */
    public static <T> T jsonToBean(String gsonString, Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, cls);
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json String
     * @param cls  Class
     * @param <T>  T
     * @return List
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString String
     * @return List
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
    }

    /**
     * 转成map的
     *
     * @param gsonString String
     * @return Map
     */
    public static <T> Map<String, T> jsonToMaps(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }

}

















