package com.geocentric.foundation.util;

import android.os.Environment;
import android.util.Log;

import com.geocentric.foundation.util.FileUtils.CreateFileFailedException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class LogUtils {

    private static final boolean DEBUG = CommUtils.isDebugVersion();

    /**
     * 默认tag
     */
    private static String defaultTag = "TAG";
    /**
     * 是否需要将日志输出至文件中存储
     */
    private static boolean isLogRecord = true;

    /**
     * 日志信息存储路径
     */
    private static String logPath = Environment.getExternalStorageDirectory() + "/geocentric/log/log.txt";

    public static String getDefaultTag() {
        return defaultTag;
    }

    public static void setDefaultTag(String defaultTag) {
        LogUtils.defaultTag = defaultTag;
    }

    public static boolean isLogRecord() {
        return isLogRecord;
    }

    public static void setIsLogRecord(boolean isLogRecord) {
        LogUtils.isLogRecord = isLogRecord;
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {

        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * 使用Seedland作为Tag输出日志
     *
     * @param msg
     */
    public static final void defaultLog(String msg) {
        if (DEBUG) {
            Log.d(getDefaultTag(), msg);
        }
    }

    public static final void defaultLog(Throwable throwable) {
        if (DEBUG) {
            log(getDefaultTag(), throwable);
        }
    }

    /**
     * 异常日志输出,默认输出log.e,根据标志位会决定是否输出至控制台或者记录至SD卡指定文件中
     *
     * @param tag       标记
     * @param throwable 异常信息
     */
    public static final void log(String tag, Throwable throwable) {
        logThrowablePrivate(Log.ERROR, tag, throwable);
    }

    /***
     * 修改日志文件存储路径
     *
     * @param lofFilePath
     *            日志文件存储路径
     * @throws IOException
     * @throws CreateFileFailedException
     */
    public static final void setLogPath(String lofFilePath) throws CreateFileFailedException, IOException {
        File file = new File(lofFilePath);
        if (file == null || file.isDirectory()) {
            RuntimeException e = new IllegalArgumentException("错误的修改日志文件存储路径");
            LogUtils.defaultLog(e);
        } else {
            FileUtils.createFile(lofFilePath);
            logPath = lofFilePath;
        }
    }

    /**
     * 日志信息文件路径
     *
     * @return
     * @throws CreateFileFailedException
     * @throws IOException
     */
    public static final String getLogFilePath() throws CreateFileFailedException, IOException {
        FileUtils.createFile(logPath);
        return logPath;
    }

    /**
     * 写入异常信息的log,根据标志位会决定是否输出至控制台或者记录至SD卡指定文件中
     *
     * @param level     in(Log.DEBUG,Log.ERROR,Log.INFO,Log.VERBOSE,Log.WARN)
     * @param tag       标记
     * @param throwable 错误信息
     */
    private static final void logThrowablePrivate(int level, final String tag,
                                                  final Throwable throwable) {

        if (CommUtils.isDebugVersion()) {
            return;
        }

        if (DEBUG) {
            // 是否需要将日志输出至控制台
            long time = System.currentTimeMillis();
            switch (level) {
                case Log.ASSERT:
                    break;
                case Log.DEBUG:
                    Log.d(tag, TimeUtils.getTimeFormatMicrosecond(time) + " : ", throwable);
                    break;
                case Log.ERROR:
                    Log.e(tag, TimeUtils.getTimeFormatMicrosecond(time) + " : ", throwable);
                    break;
                case Log.INFO:
                    Log.i(tag, TimeUtils.getTimeFormatMicrosecond(time) + " : ", throwable);
                    break;
                case Log.VERBOSE:
                    Log.v(tag, TimeUtils.getTimeFormatMicrosecond(time) + " : ", throwable);
                    break;
                case Log.WARN:
                    Log.w(tag, TimeUtils.getTimeFormatMicrosecond(time) + " : ", throwable);
                    break;
            }
        }
        if (isLogRecord() && DeviceUtils.isCardExist()) {
            // 是否需要将日志输入至指定文件夹
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        FileUtils.writeLogInfoToFile(getLogFilePath(), throwable);
                    } catch (CreateFileFailedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 打印bean对象的数据，方便调试使用
     * @param bean
     * @return
     */
    public static String bean2String(Object bean) {
        if (bean != null) {
            StringBuilder sb = new StringBuilder();
            Class<?> clazz = bean.getClass();
            Field[] fields = clazz.getDeclaredFields();
            sb.append(clazz.getSimpleName());
            sb.append("[");
            for (Field f : fields) {
                sb.append(f.getName());
                sb.append(" = ");
                try {
                    f.setAccessible(true);
                    sb.append(String.valueOf(f.get(bean)));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                sb.append(",");
            }
            if (sb.lastIndexOf(",") == sb.length() - 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
            return sb.toString();
        }
        return "";
    }
}
