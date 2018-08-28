package com.tools.jj.tools.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:Gson解析帮助类
 * version:1.0.0
 */

public class JsonUtil {

    private static Gson gson = null;

    private static class JsonUtilHolder {
        private static final JsonUtil INSTANCE = new JsonUtil();
    }

    private JsonUtil() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(JsonObject.class, new JsonDeserializer<Object>() {

            @Override
            public Object deserialize(JsonElement jsonElement, Type type,
                                      JsonDeserializationContext jsonDeserializationContext)
                    throws JsonParseException {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return jsonObject;
            }
        });

        gson = builder.disableHtmlEscaping().create();
    }

    public static final JsonUtil getInstance() {
        return JsonUtilHolder.INSTANCE;
    }


    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        return gson.toJson(object);
    }

    public static Map<String, Object> toMap(Object obj) {
        JsonElement element = gson.toJsonTree(obj);
        return gson.fromJson(element, Map.class);
    }

    public static <T> T fromJson(String content, Class<T> clazz) {
        if (TextUtils.isEmpty(content) || clazz == null) {
            return null;
        }
        try {
            return gson.fromJson(content, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String content, TypeToken<T> token) {
        if (TextUtils.isEmpty(content) || token == null) {
            return null;
        }
        try {
            return gson.fromJson(content, token.getType());
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 从json中搜索，根据键的名字，返回值。
     * @param json
     * @param name json中的键名
     * @return Object
     */
    public static Object findObject(String json , String name){

        Object object = null;

        if(TextUtils.isEmpty(json) || TextUtils.isEmpty(name)){
            return null;
        }

        try {
            JSONObject jsonobject = new JSONObject(json);
            if(!jsonobject.has(name)){
                return null;
            } else {
                object = jsonobject.get(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
