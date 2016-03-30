package com.common.androidcore.type;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xufei 创建于 2013年10月30日 下午5:15:50
 * @version Ver 1.0 2013年10月30日 改订
 *          SharedPreference工具类
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil spUtil;

    /**
     * 开启过的sp缓存
     */
    private Map<String, SharedPreferences> spCache;

    private static Context context;
    private static String configName;

    private SharedPreferencesUtil() {
        spCache = new HashMap<>();
    }

    /**
     * 获得spUtil实例
     *
     * @param c      上下文对象
     * @param spName sp的文件名
     * @return spUtil实例
     */
    public static SharedPreferencesUtil getInstance(Context c, String spName) {
        if (spUtil == null) {
            spUtil = new SharedPreferencesUtil();
        }
        context = c;
        configName = spName;
        return spUtil;
    }

    private SharedPreferences getPreferences() {
        SharedPreferences sp = spCache.get(configName);
        if (sp == null) {
            sp = context.getSharedPreferences(configName, Context.MODE_PRIVATE);
            spCache.put(configName, sp);
        }
        return sp;
    }

    private Editor getEditor() {
        return getPreferences().edit();
    }

    /**
     * 插入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, String value) {
        Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 插入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void putBoolean(String key, boolean value) {
        Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 插入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void putFloat(String key, float value) {
        Editor editor = getEditor();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 插入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void putInt(String key, int value) {
        Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 插入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void putLong(String key, long value) {
        Editor editor = getEditor();
        editor.putFloat(key, value);
        editor.commit();
    }


    public void putKVMap(Map<String, String> map) {
        Editor editor = getEditor();
        Set<String> ketSet = map.keySet();
        for (String key : ketSet) {
            editor.putString(key, map.get(key));
        }
        editor.commit();
    }

    /**
     * 插入键值对
     *
     * @param key   键
     * @param value 值
     */
    public void putStringSet(String key, Set<String> value) {
        Editor editor = getEditor();
        editor.putStringSet(key, value);
        editor.commit();
    }

    /**
     * 根据键取对应的值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    /**
     * 根据键取对应的值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    /**
     * 根据键取对应的值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    /**
     * 根据键取对应的值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    /**
     * 根据键取对应的值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public long getLong(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    /**
     * 根据键取对应的值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 值
     */
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getPreferences().getStringSet(key, defaultValue);
    }

    /**
     * 获取所有键值对
     *
     * @return 键值对集合
     */
    public Map<String, ?> getAll() {
        return getPreferences().getAll();
    }

    /**
     * 根据键删除一个键值对
     *
     * @param key 键
     */
    public void remove(String key) {
        Editor editor = getEditor();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 所有键值对中是否包含此键
     *
     * @param key 键
     * @return 是否包含
     */
    public boolean isContainKey(String key) {
        return getAll().containsKey(key);
    }

    /**
     * 保存序列化对象
     *
     * @param object 序列化对象
     * @param key    键
     */
    public void put(String key, Serializable object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
            objectOutputStream.close();
            byteArrayOutputStream.close();
            put(key, serStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取序列化对象
     *
     * @param key 键
     * @return 序列化对象
     */
    public Serializable getSerializableObject(String key) {
        String serStr = getString(key, null);
        if (TextUtils.isEmpty(serStr)) {
            return null;
        }
        try {
            String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Serializable object = (Serializable) objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
