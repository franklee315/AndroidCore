package com.common.androidcore.net;

import android.app.Activity;
import android.content.Intent;

/**
 * @author lifan 创建于 2013年10月25日 下午4:11:56
 * @version Ver 1.0 2013年10月25日 改订
 *          邮件工具
 */
public class MailUtil {
    /**
     * 调用系统邮件客户端发送邮件
     *
     * @param to      收件人地址
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public static void postMail(Activity context, String[] to, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(emailIntent, "发送邮件..."));
    }
}
