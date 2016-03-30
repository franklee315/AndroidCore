package com.common.androidcore.net;

import android.content.Context;
import android.content.Intent;

/**
 * @author lifan 创建于 2013年10月25日 下午4:11:56
 * @version Ver 1.0 2013年10月25日 改订
 * 邮件工具
 */
public class MailUtil {
    private static MailUtil mailUtil;

    private Context context;

    private MailUtil(Context context) {
        this.context = context;
    }

    /**
     * 获得MailUtil实例
     *
     * @param context 上下问对象
     * @return MailUtil实例
     */
    public static MailUtil getInstance(Context context) {
        if (mailUtil == null) {
            mailUtil = new MailUtil(context);
        }
        return mailUtil;
    }

    /**
     * 调用系统邮件客户端发送邮件
     *
     * @param to      收件人地址
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void postMail(String[] to, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(emailIntent, "发送邮件..."));
    }
}
