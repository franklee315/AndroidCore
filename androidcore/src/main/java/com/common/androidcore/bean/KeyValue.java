package com.common.androidcore.bean;

import java.io.Serializable;

/**
 * Created by Frank on 2015.9.16.
 * 解析URL参数用到的Bean
 */
public class KeyValue implements Serializable {
    public String key;
    public String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
