//package com.tianjistar.help.utils;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//import com.google.gson.reflect.TypeToken;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class GsonUtil {
//    private static Gson gson = null;
//
//    static {
//        if (gson == null) {
//            gson = new Gson();
//        }
//    }
//
//    private GsonUtil() {
//    }
//
//
//    /**
//     * 转成json
//     *
//     * @param object
//     * @return
//     */
//    public static String GsonString(Object object) {
//        String gsonString = null;
//        if (gson != null) {
//            gsonString = gson.toJson(object);
//        }
//        return gsonString;
//    }
//
//    /**
//     * 转成bean
//     *
//     * @param gsonString
//     * @param cls
//     * @return
//     */
//    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
//        T t = null;
//        if (gson != null) {
//            t = gson.fromJson(gsonString, cls);
//        }
//        return t;
//    }
//
//    /**
//     * 转成list
//     *
//     * @param json
//     * @param clazz
//     * @return
//     */
//
//    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
//        List<T> lst = new ArrayList<T>();
//        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
//        for (final JsonElement elem : array) {
//            lst.add(gson.fromJson(elem, clazz));
//        }
//        return lst;
//    }
//
//    /**
//     * 转成list中有map的
//     *
//     * @param gsonString
//     * @return
//     */
//    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
//        List<Map<String, T>> list = null;
//        if (gson != null) {
//            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
//        }
//        return list;
//    }
//
//    /**
//     * 转成map的
//     *
//     * @param gsonString
//     * @return
//     */
//    public static <T> Map<String, T> GsonToMaps(String gsonString) {
//        Map<String, T> map = null;
//        if (gson != null) {
//            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {}.getType());
//        }
//        return map;
//    }
////
////    public static void main(String[] args) {
////        BeanReturn<Comment> commentBeanReturn = GsonToBean("{\"ret\":200,\"msg\":\"暂无评论！\"}", BeanReturn.class);
////    }
//}
