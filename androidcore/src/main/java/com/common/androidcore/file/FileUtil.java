package com.common.androidcore.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author lifan 创建于 2013年10月25日 下午3:43:25
 * @version Ver 1.0 2013年10月25日 改订
 *          文件操作
 */
public class FileUtil {
    public static final float K = 1024;
    public static final float M = 1024 * 1024;
    public static final float G = 1024 * 1024 * 1024;
    private static final String SIZE_BYTE = "%dB";
    private static final String SIZE_KB = "%.2fK";
    private static final String SIZE_MB = "%.2fM";
    private static final String SIZE_GB = "%.2fG";

    /**
     * 获得文件扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getFileExt(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return null;
        }
        String fileExt = null;
        int idx = fileName.lastIndexOf('.');
        if (idx != -1 && idx != fileName.length() - 1) {
            fileExt = fileName.substring(idx + 1);
        }
        return fileExt;
    }

    /**
     * 移动文件
     *
     * @param oldPath 源文件路径
     * @param newPath 目标路径
     * @return 是否成功
     */
    public static boolean moveFile(String oldPath, String newPath) {
        try {
            int read;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((read = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, read);
                }
                inStream.close();
                fs.flush();
                fs.close();
                oldFile.delete();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查找父级目录
     *
     * @param file 当前文件
     * @return 父级目录
     */
    public static String queryParentDirectory(File file) {
        String filePath = file.getAbsolutePath();
        return queryParentDirectory(filePath);
    }

    /**
     * 查找父级目录
     *
     * @param filePath 当前文件路径
     * @return 父级目录
     */
    public static String queryParentDirectory(String filePath) {
        return filePath.split(File.separator)[0];
    }

    /**
     * 文件复制
     *
     * @param oldPath 源文件路径
     * @param newPath 目标路径
     * @return 是否成功
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int read;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((read = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, read);
                }
                inStream.close();
                fs.flush();
                fs.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 文件重命名
     *
     * @param file     需要重命名的文件
     * @param fileName 目标名字
     * @return 是否重命名成功
     */
    public static boolean renameFile(File file, String fileName) {
        return file.renameTo(new File(fileName));
    }

    /**
     * 清空目录
     *
     * @param dirPath 需要清空的目录路径
     * @return 是否清空成功
     */
    public static boolean cleanDirectory(String dirPath) {
        try {

            File file = new File(dirPath);
            File[] files = file.listFiles();
            for (File file2 : files) {
                file2.delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 格式化文件大小
     *
     * @param size 文件大小 单位 byte
     * @return 1.07G、1.3M之类
     */
    public static String getSizeString(float size) {
        if (size < 0) {
            throw new UnsupportedOperationException("Not implementation.");
        } else if (size < K) {
            return String.format(SIZE_BYTE, (int) size);
        } else if (size < M) {
            return String.format(SIZE_KB, size / K);
        } else if (size < G) {
            return String.format(SIZE_MB, size / M);
        } else {
            return String.format(SIZE_GB, size / G);
        }
    }

    /**
     * 获得目录大小
     *
     * @param dir 目录
     * @return 目录大小
     */
    public static long getDirSize(File dir) {
        long count = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                count += getDirSize(file);
            } else {
                count += file.length();
            }
        }
        return count;
    }

    /**
     * 获得目录大小的格式化字符串
     *
     * @param dir 目录
     * @return 格式化字符串
     */
    public static String getDirSizeStr(File dir) {
        long size = getDirSize(dir);
        return getSizeString(size);
    }
}
