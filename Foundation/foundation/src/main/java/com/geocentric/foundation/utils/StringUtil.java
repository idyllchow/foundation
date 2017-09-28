package com.geocentric.foundation.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
    /**
     * 生成金额格式pattern，同时编译正则表达式
     */
    public static final Pattern patternMoney = Pattern
            .compile("^(([1-9]\\d*)|0)\\.?(\\d{1,2})?$");

    /**
     * 判断字符是否为金额格式
     *
     * @param moneyText
     * @return boolean 返回true如果匹配成功
     */
    public static final boolean isMoneyFormat(String moneyText) {
        if (isStrEmpty(moneyText)) {
            return false;
        }
        return patternMoney.matcher(moneyText).find();
    }

    /**
     * 格式化字符串为两位小数
     *
     * @param numberStr
     * @return String 格式化后字符
     * @throws NumberFormatException two decimal places
     */
    public static final String computeTwoDecimalPlaces(String numberStr)
            throws NumberFormatException {
        if (isStrEmpty(numberStr))
            return null;
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(Double.valueOf(numberStr));
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    /**
     * 格式化金额，例 1,234.00
     *
     * @param doubleValue double类型需要转化的值
     * @return
     */
    public static final String formatAmount(double doubleValue) {
        try {
            return new DecimalFormat(",##0.00").format(doubleValue);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("the amount " + doubleValue
                    + " is invalid", nfe);
        }
    }


    /**
     * 格式化金额，例 1,234.00
     *
     * @param strValue String类型需要转化的值
     * @return String
     */
    public static final String formatAmount(String strValue) {
        if (isDigits(strValue)) {
            return formatAmount(Double.parseDouble(strValue) / 100d);
        } else {
            return null;
        }
    }

    /**
     * 判断字符串是否有纯数字构成，不包含其它任何字符
     *
     * @param value
     * @return boolean
     */
    public static final boolean isDigits(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        return value.matches("[0-9]+");
    }

    /**
     * 判断字符串是否只为汉字或英文字符
     *
     * @param str
     * @return boolean
     */
    public static final boolean isChineseOrEnglish(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String regex = "[\u4e00-\u9fa5a-zA-Z]+";
        return str.matches(regex);
    }

    /**
     * 是否是由数字和英文字符组成的字符串
     *
     * @param str
     * @return boolean
     */
    public static final boolean isNumOrEnglish(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String regex = "[a-z0-9A-Z]+";
        return str.matches(regex);
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return boolean
     */
    public static final boolean isStrEmpty(String str) {
        if (str == null || str.length() == 0)
            return true;
        return false;
    }

    /**
     * 字符串是否是整数
     *
     * @param str
     * @return boolean
     */
    public static final boolean isNum(String str) {
        if (isStrEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 统计字符串中中文字符个数
     *
     * @param str
     * @return int
     */
    public static final int getChineseCharNumber(String str) {
        if (isStrEmpty(str)) {
            return 0;
        }
        String E1 = "[\u4e00-\u9fa5]";// 中文
        int chineseCount = 0;
        for (int i = 0; i < str.length(); i++) {
            String temp = String.valueOf(str.charAt(i));
            if (temp.matches(E1)) {
                chineseCount++;
            }
        }

        return chineseCount;
    }

    /**
     * 统计字符串中英文文字符个数
     *
     * @param str
     * @return int
     */
    public static final int getEnglishCharNumber(String str) {
        if (isStrEmpty(str)) {
            return 0;
        }
        String E1 = "[a-zA-Z]";
        int chineseCount = 0;
        for (int i = 0; i < str.length(); i++) {
            String temp = String.valueOf(str.charAt(i));
            if (temp.matches(E1)) {
                chineseCount++;
            }
        }

        return chineseCount;
    }

    /**
     * 统计字符串中数字符个数
     *
     * @param str
     * @return int
     */
    public static final int getNumberCharNumber(String str) {
        if (isStrEmpty(str)) {
            return 0;
        }
        String E1 = "[0-9]";
        int chineseCount = 0;
        for (int i = 0; i < str.length(); i++) {
            String temp = String.valueOf(str.charAt(i));
            if (temp.matches(E1)) {
                chineseCount++;
            }
        }

        return chineseCount;
    }

    /**
     * 统计字符串中特殊字符个数
     *
     * @param str
     * @return int
     */
    public static final int getSpecialCharNumber(String str) {
        if (isStrEmpty(str)) {
            return 0;
        }
        return str.length()
                - (getChineseCharNumber(str) + getEnglishCharNumber(str) + getNumberCharNumber(str));

    }

    /**
     * 将字符串的首字符转化成小写
     *
     * @param str 要转化的字符串
     * @return String 返回首字符变小写之后的字符串
     */
    public static String toFirstLowerCase(String str) {
        if (str != null && str.length() > 0) {
            return str.substring(0, 1).toLowerCase()
                    + str.substring(1, str.length());
        } else {
            return str;
        }
    }

    /**
     * 获取源字符串中preStr后的字符串
     *
     * @param srcStr 源字符串
     * @param preStr 要截掉的起始字符串
     * @return String
     */
    public static String cutOutStringTrail(String srcStr, String preStr) {
        if (TextUtils.isEmpty(srcStr)) {
            return "";
        }
        if (TextUtils.isEmpty(preStr)) {
            return srcStr;
        }
        int index = srcStr.lastIndexOf(preStr);
        if (index < 0 || index + 1 > srcStr.length()) {
            return srcStr;
        }
        return srcStr.substring(index + preStr.length());
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return String
     */
    public final static String subZeroAndDot(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 默认用utf-8对字符串进行编码
     *
     * @param encodingStr 待编码的字符串
     * @return String 编码后的字符串,有可能为空
     */
    public static final String getEncodingByUTF8(String encodingStr) {
        if (encodingStr == null)
            return null;
        String encodedStr = null;
        try {
            encodedStr = URLEncoder.encode(encodingStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.defaultLog(e);
        }
        return encodedStr;
    }

    /**
     * 用指定的编码格式对字符串进行编码
     *
     * @param encodingStr
     * @param charset
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static final String getEncoding(String encodingStr, String charset)
            throws UnsupportedEncodingException {
        if (encodingStr == null || charset == null)
            return null;
        return URLEncoder.encode(encodingStr, charset);
    }

    /**
     * 默认用utf-8对字符串进行解码
     *
     * @param decodingStr 待解码的字符串
     * @return String 解码后的字符串
     */
    public static final String getDecodingByUTF8(String decodingStr) {
        if (decodingStr == null)
            return null;
        String decodedStr = null;
        try {
            decodedStr = URLDecoder.decode(decodingStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.defaultLog(e);
        }
        return decodedStr;
    }

    private static final String BLANK = " ";

    /**
     * 提供在字符串中，通过等步长加入空格的方法
     *
     * @param optStr 被操作字符串
     * @param step   步长。必须大于0
     * @return String
     */
    public static String getSpiltStrByBlanks(String optStr, int step) {
        StringBuilder sb = new StringBuilder();
        if (step <= 0) {
            new IllegalArgumentException("错误的输入参数：步长必须大于0！");
        }
        if (null != optStr) {
            int i = 1, length = optStr.length();
            while (i * step < length) {
                sb.append(optStr.substring((i - 1) * step, i * step));
                sb.append(BLANK);
                i++;
            }
            sb.append(optStr.substring((i - 1) * step, length));
        } else {
            return null;
        }
        return sb.toString().trim();
    }

    /**
     * 用指定的编码格式对字符串进行编码
     *
     * @param decodingStr 待解码的字符串
     * @return String 解码后的字符串
     */
    public static final String getDecoding(String decodingStr, String charset)
            throws UnsupportedEncodingException {
        if (decodingStr == null || charset == null)
            return null;
        return URLDecoder.decode(decodingStr, charset);
    }

    public static final String substringAfterIndex(String srcStr, int index) {
        String result = "";
        int length = srcStr.length();
        if (length >= index) {
            result = srcStr.substring(length - index, length);
        }
        return result;
    }
}
