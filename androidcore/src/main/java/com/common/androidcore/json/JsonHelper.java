package com.common.androidcore.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * JSON操作帮助类
 *
 * @author lifan
 * @version v1.0, 2013.07.11
 */
public class JsonHelper {
    /**
     * 获取默认GSON对象
     *
     * @return 默认GSON对象
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * 根据类型生成Gson对象
     */
    public static Gson getGson(String type) {
        if (TYPE_EXPOSE.equals(type)) {
            return exposeGson;
        } else {
            return gson;
        }
    }

    /**
     * 生成GSon的类型，为expose，只有对象的字段上标识了@Expose的属性，才会生成的json字符串中
     */
    public static final String TYPE_EXPOSE = "expose";

    /**
     * 允许使用expose注解创建GSON对象，适配java.util.Date,java.sql.Date,java.util.Calendar,java.sql.Timestamp的时间类型
     */
    private static Gson exposeGson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new UtilDateSerializer())
            .registerTypeAdapter(Calendar.class, new UtilCalendarSerializer())
            .registerTypeAdapter(GregorianCalendar.class, new UtilCalendarSerializer())
            .registerTypeAdapter(java.sql.Date.class, new SQLDateSerializer())
            .registerTypeAdapter(Timestamp.class, new TimestampSerializer())
            .setDateFormat(DateFormat.LONG).setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation().create();

    /**
     * 创建GSON对象，适配java.util.Date,java.sql.Date,java.util.Calendar,java.sql.Timestamp的时间类型
     */
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new UtilDateSerializer())
            .registerTypeAdapter(Calendar.class, new UtilCalendarSerializer())
            .registerTypeAdapter(GregorianCalendar.class, new UtilCalendarSerializer())
            .registerTypeAdapter(java.sql.Date.class, new SQLDateSerializer())
            .registerTypeAdapter(Timestamp.class, new TimestampSerializer())
            .setDateFormat(DateFormat.LONG).setPrettyPrinting().create();

    /**
     * 时间类型适配java.util.Date，所有的时间转换JSON格式的时候使用long类型
     */
    private static class UtilDateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

        @Override
        public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.getTime());
        }

        @Override
        public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return new Date(element.getAsJsonPrimitive().getAsLong());
        }
    }

    /**
     * 时间类型适配java.util.Calendar，所有的时间转换JSON格式的时候使用long类型
     *
     * @author 王亮
     * @version v1.0, 2013.07.11
     * @see
     * @since v1.0
     */
    private static class UtilCalendarSerializer implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

        @Override
        public JsonElement serialize(Calendar cal, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(Long.valueOf(cal.getTimeInMillis()));
        }

        @Override
        public Calendar deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(element.getAsJsonPrimitive().getAsLong());
            return cal;
        }

    }

    /**
     * 时间类型适配java.sql.Date，所有的时间转换JSON格式的时候使用long类型
     *
     * @author 王亮
     * @version v1.0, 2013.07.11
     * @see
     * @since v1.0
     */
    private static class SQLDateSerializer implements
            JsonSerializer<java.sql.Date>, JsonDeserializer<java.sql.Date> {

        @Override
        public JsonElement serialize(java.sql.Date date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.getTime());
        }

        @Override
        public java.sql.Date deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return new java.sql.Date(element.getAsJsonPrimitive().getAsLong());
        }

    }

    /**
     * 时间类型适配java.sql.Timestamp，所有的时间转换JSON格式的时候使用long类型
     *
     * @author 王亮
     * @version v1.0, 2013.07.11
     * @see
     * @since v1.0
     */
    private static class TimestampSerializer implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

        @Override
        public JsonElement serialize(Timestamp date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.getTime());
        }

        @Override
        public Timestamp deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return new Timestamp(element.getAsJsonPrimitive().getAsLong());
        }
    }

    /**
     * 判断一个字符串是否符合json
     *
     * @param json 待判断的字符串
     * @return true 该字符串符合JSON规则 or not
     * @Date 2015/4/16
     * @author Shawn
     */
    public static boolean isJson(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

}
