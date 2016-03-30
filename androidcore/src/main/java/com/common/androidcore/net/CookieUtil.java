package com.common.androidcore.net;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.List;

/**
 * Created by Frank on 2015.9.8.
 */
public class CookieUtil {
    private static CookieUtil singleton;
    private CookieManager cookieManager;

    public static CookieUtil getInstance() {
        if (singleton == null) {
            synchronized (CookieUtil.class) {
                if (singleton == null) {
                    singleton = new CookieUtil();
                }
            }
        }
        return singleton;
    }

    public void enableCookie() {
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    public boolean isCookieEnable() {
        return cookieManager != null;
    }

    public List<HttpCookie> getCookieList() {
        if (isCookieEnable()) {
            return cookieManager.getCookieStore().getCookies();
        }
        return null;
    }

    public void removeAllCookie() {
        if (isCookieEnable()) {
            cookieManager.getCookieStore().removeAll();
        }
    }
}
