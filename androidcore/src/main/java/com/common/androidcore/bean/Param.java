package com.common.androidcore.bean;

import java.io.Serializable;

/**
 * Created by Frank on 2015.9.16.
 */
public class Param implements Serializable {
    public String key;
    public String value;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
