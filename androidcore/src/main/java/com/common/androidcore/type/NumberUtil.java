package com.common.androidcore.type;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字的处理类
 * <p/>
 * Created by Shawn on 2015/4/17.
 */
public class NumberUtil {

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    /**
     * 控制浮点的精度
     * @param value 原始值
     * @param scale 精度位数（保留的小数位数）
     * @param roundMode 精度取值方式
     * @return 精度计算后的数据
     */
    public static double round(double value, int scale, int roundMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundMode);
        double d= bd.doubleValue();
        bd = null;
        return d;
    }

    /**
     * double加法运算
     * @param doubles 需要进行运算的double
     * @return
     */
    public static double doubleAddition(double... doubles) {
        BigDecimal bigDecimal = new BigDecimal(0);
        for (double num : doubles) {
            bigDecimal = bigDecimal.add(new BigDecimal(Double.toString(num)));
        }
        return bigDecimal.doubleValue();
    }

    public static double subtract(String firstDouble, String doubleLess) {
        BigDecimal bigDecimal1 = new BigDecimal(firstDouble);
        BigDecimal bigDecimal2 = new BigDecimal(doubleLess);
        return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }

    public static String keepOnePoint(double num) {
        DecimalFormat df = new DecimalFormat("#####0.0");
        return df.format(num);
    }
}
