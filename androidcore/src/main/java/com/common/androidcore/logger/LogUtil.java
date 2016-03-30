package com.common.androidcore.logger;

import android.os.Environment;

import com.common.androidcore.thread.ThreadUtil;
import com.common.androidcore.type.DateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

/**
 * @author lifan 创建于 2013年10月29日 下午12:01:05
 * @version Ver 1.0 2013年10月29日 改订 log工具类
 */
public class LogUtil {

    private static final String LOG_NAME = "log.txt";
    private static String LOG_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static boolean DEBUG = false;
    private static boolean SAVE_LOG_TO_FILE = false;

    /**
     * 初始化log，最好是在Application中初始化
     *
     * @param isOpenDebugMode     是否开启debug模式
     * @param isOpenSaveLogToFile 是否开启写log文件
     * @param logDir              log文件目录
     */
    public static void init(boolean isOpenDebugMode, boolean isOpenSaveLogToFile, String logDir) {
        changeDebugMode(isOpenDebugMode);
        changeSaveLogToFile(isOpenSaveLogToFile);
        setLogDir(logDir);
    }

    /**
     * 设置DEBUG模式,默认关闭DEBUG模式
     *
     * @param flag DEBUG模式开关
     */
    public static void changeDebugMode(boolean flag) {
        DEBUG = flag;
    }

    /**
     * 设置是否写log文件，默认不写文件
     *
     * @param flag 写文件开关
     */
    public static void changeSaveLogToFile(boolean flag) {
        SAVE_LOG_TO_FILE = flag;
    }

    /**
     * 设置log文件目录，默认sdcard根目录
     *
     * @param dirPath log文件目录
     */
    public static void setLogDir(String dirPath) {
        LOG_DIR = dirPath;
    }

    /**
     * 获取默认tag
     *
     * @param caller
     * @return
     */
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    /**
     * 输出verbose级别的日志
     *
     * @param msg log的内容
     */
    public static void v(String msg) {
        String tag = generateTag(Thread.currentThread().getStackTrace()[4]);

        if (DEBUG) {
            android.util.Log.v(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Verbose", tag, msg);
        }
    }

    /**
     * 输出verbose级别的日志
     *
     * @param tag log标签
     * @param msg log的内容
     */
    public static void v(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.v(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Verbose", tag, msg);
        }
    }

    /**
     * 输出debug级别的日志
     *
     * @param msg log的内容
     */
    public static void d(String msg) {
        String tag = generateTag(Thread.currentThread().getStackTrace()[4]);
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Debug", tag, msg);
        }
    }

    /**
     * 输出debug级别的日志
     *
     * @param tag log标签
     * @param msg log的内容
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Debug", tag, msg);
        }
    }

    /**
     * 输出info级别的日志
     *
     * @param msg log的内容
     */
    public static void i(String msg) {
        String tag = generateTag(Thread.currentThread().getStackTrace()[4]);
        if (DEBUG) {
            android.util.Log.i(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Info", tag, msg);
        }
    }

    /**
     * 输出info级别的日志
     *
     * @param tag log标签
     * @param msg log的内容
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.i(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Info", tag, msg);
        }
    }

    /**
     * 输出warn级别的日志
     *
     * @param msg log的内容
     */
    public static void w(String msg) {
        String tag = generateTag(Thread.currentThread().getStackTrace()[4]);
        if (DEBUG) {
            android.util.Log.w(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Warn", tag, msg);
        }
    }

    /**
     * 输出warn级别的日志
     *
     * @param tag log标签
     * @param msg log的内容
     */
    public static void w(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.w(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Warn", tag, msg);
        }
    }

    /**
     * 输出error级别的日志
     *
     * @param msg log的内容
     */
    public static void e(String msg) {
        String tag = generateTag(Thread.currentThread().getStackTrace()[4]);
        if (DEBUG) {
            android.util.Log.e(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Error", tag, msg);
        }
    }

    /**
     * 输出error级别的日志
     *
     * @param tag log标签
     * @param msg log的内容
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag, msg);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Error", tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag, msg, tr);
        }

        if (SAVE_LOG_TO_FILE) {
            sendHandlerMsg("Error", tag, msg);
        }
    }

    private static void sendHandlerMsg(String level, String tag, String content) {
        write2SDCard(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss:SSS") + " [Thread-" + Thread.currentThread().getId() + "] " + level.toUpperCase(Locale.getDefault()) + " " + tag + " : " + content);
    }

    private static void write2SDCard(String content) {
        ThreadUtil.getInstance().start(new WriteRunnable(content));
    }

    private static class WriteRunnable implements Runnable {
        private String content;

        public WriteRunnable(String content) {
            this.content = content;
        }

        private File createLogFile() throws IOException {
            File logFile = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(LOG_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                logFile = new File(dir.getAbsolutePath() + File.separator + LOG_NAME);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
            }
            return logFile;
        }

        @Override
        public void run() {
            try {
                File logFile = createLogFile();
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(logFile, true), "UTF-8");
                BufferedWriter writer = new BufferedWriter(write);
                writer.write(content + "\n\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
