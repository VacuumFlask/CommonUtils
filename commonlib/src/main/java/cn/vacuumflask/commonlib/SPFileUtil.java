package cn.vacuumflask.commonlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * SharedPreferences 工具类
 */
public class SPFileUtil {

    /**
     * 不实例化对象 获取文件中相对应的值
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @return 返回相对应的数据
     */
    public static String getMsg(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * 不实例化对象 获取文件中相对应的值
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @return 返回相对应的数据
     */
    public static int getIntMsg(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    /**
     * 不实例化对象 获取文件中相对应的值
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @return 返回相对应的数据
     */
    public static long getLongMsg(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, -1L);
    }

    /**
     * 不实例化对象 获取文件中相对应的值
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @return
     */
    public static boolean getMessageBoolean(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 不实例化对象 获取文件中相对应的值
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @return
     */
    public static boolean getMessageBooleanDeTrue(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    /**
     * 返回SharedPreference文件中是否含有该值
     *
     * @return 键值的集合
     */
    public static boolean hasKey(Context context, String fileName, String key) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE).contains(key);
    }

    /**
     * 不实例化对象 删除相对所有键值数据
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    public static void deleteAllKey(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    /**
     * 不实例化对象 删除相对所有键值数据
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    public static void deleteKey(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }

    /**
     * 不实例化对象 删除相对所有键值数据
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    public static void deleteContainsKey(Context context, String fileName, String contains) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<String, ?> map = sharedPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entrySet = map.entrySet();
        for (Map.Entry<String, ?> entry : entrySet) {
            String key = entry.getKey();
            if (key.contains(contains)) {
                editor.remove(key).apply();
            }
        }

    }

    /**
     * 不实例化对象，直接保存数据
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     * @return 返回是否保存成功
     */
    public static boolean setMessage(Context context, String fileName, String key, String vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.edit().putString(key, vlaue).commit();
    }

    /**
     * 不实例化对象，直接保存数据
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     * @return 返回是否保存成功
     */
    public static boolean setMessage(Context context, String fileName, String key, boolean vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.edit().putBoolean(key, vlaue).commit();
    }

    /**
     * 不实例化对象，直接保存数据
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     * @return 返回是否保存成功
     */
    public static boolean setMessage(Context context, String fileName, String key, int vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.edit().putInt(key, vlaue).commit();
    }

    /**
     * 不实例化对象，直接保存数据
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     * @return 返回是否保存成功
     */
    public static boolean setMessage(Context context, String fileName, String key, long vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.edit().putLong(key, vlaue).commit();
    }

    /**
     * 不实例化对象，直接保存数据，且不返回结果
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     */
    public static void setMessageApply(Context context, String fileName, String key, String vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, vlaue).apply();
    }

    /**
     * 不实例化对象，直接保存数据，且不返回结果
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     */
    public static void setMessageApply(Context context, String fileName, String key, boolean vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, vlaue).apply();
    }

    /**
     * 不实例化对象，直接保存数据，且不返回结果
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     */
    public static void setMessageApply(Context context, String fileName, String key, int vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, vlaue).apply();
    }

    /**
     * 不实例化对象，直接保存数据，且不返回结果
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param key      键值
     * @param vlaue    值
     */
    public static void setMessageApply(Context context, String fileName, String key, long vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, vlaue).apply();
    }

    /**
     * 不实例化对象，批量保存数据，且不返回结果
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param map    值
     */
    public static void setMessageApply(Context context, String fileName, HashMap<String, String> map) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String entryKey = entry.getKey();
            String entryValue = entry.getValue();
            editor.putString(entryKey, entryValue);
        }
        editor.apply();
    }

    /**
     * 不初始化返回SharedPreference的键值集合
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回集合
     */
    public static List<String> getKeys(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entries = map.entrySet();
        Iterator<? extends Map.Entry<String, ?>> iterator = entries.iterator();
        ArrayList<String> keys = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, ?> entry = iterator.next();
            String key = entry.getKey();
            keys.add(key);
        }
        return keys;
    }

    /**
     * 不初始化返回SharedPreference的键值集合
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回集合
     */
    public static TreeMap<String, String> getMapKeys(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        Map<String, ?> map = sharedPreferences.getAll();
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.putAll((Map<? extends String, ? extends String>) map);
        return treeMap;
    }

    /**
     * 不初始化返回SharedPreference的键值集合
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回集合
     */
    public static TreeMap<String, Boolean> getMapBooleanKeys(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        Map<String, ?> map = sharedPreferences.getAll();
        TreeMap<String, Boolean> treeMap = new TreeMap<>();
        treeMap.putAll((Map<? extends String, ? extends Boolean>) map);
        return treeMap;
    }

    /**
     * 不初始化返回SharedPreference的键值集合
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 返回集合
     */
    public static List<String> getValue(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entries = map.entrySet();
        Iterator<? extends Map.Entry<String, ?>> iterator = entries.iterator();
        ArrayList<String> values = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, ?> entry = iterator.next();
            String value = entry.getValue().toString();
            values.add(value);
        }
        return values;
    }

    public static void deleteFile(Context context, String spName) {
        @SuppressLint("SdCardPath")
        File file = new File("/data/data/" + context.getPackageName() + "/shared_prefs", spName + ".xml");
        if (file.exists()) {
            boolean delete = file.delete();
            L.d("删除是否成功：" + delete);
        }
    }
}
