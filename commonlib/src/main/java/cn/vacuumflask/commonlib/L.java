package cn.vacuumflask.commonlib;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 日志工具类
 */
public class L {

    private static String TAG = "MainActivity";
    private static boolean isShowLog = true;
    private static String fileName = "log";//文件夹名
    private static String logName = "logCat.txt";//文件名
    private static boolean isShowLine = false;

    public static void setTag(String tag) {
        L.TAG = tag;
    }

    public static void setIsShowLog(boolean isShowLog) {
        L.isShowLog = isShowLog;
    }

    public static void setIsShowLine(boolean isShowLine) {
        L.isShowLine = isShowLine;
    }

    /**
     * 保存日志文件夹
     *
     * @param fileName 文件夹名称
     */
    public static void setFileName(String fileName) {
        L.fileName = fileName;
    }

    /**
     * 保存日志文件
     *
     * @param logName 文件名称
     */
    public static void setLogName(String logName) {
        L.logName = logName;
    }

    public static void i(String msg) {
        if (isShowLog) {
            if (isShowLine) {
                Log.i(TAG, generateLocationMsg() + msg);
            } else {
                Log.i(TAG, msg);
            }
        }
    }

    public static void d(String msg) {
        if (isShowLog) {
            if (isShowLine) {
                Log.d(TAG, generateLocationMsg() + msg);
            } else {
                Log.d(TAG, msg);
            }
        }
    }

    public static void e(String msg) {
        if (isShowLog) {
            if (isShowLine) {
                Log.e(TAG, generateLocationMsg() + msg);
            } else {
                Log.e(TAG, msg);
            }
        }
    }

    public static void v(String msg) {
        if (isShowLog) {
            if (isShowLine) {
                Log.v(TAG,msg);
            } else {
                Log.v(TAG, msg);
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private static String generateLocationMsg() {
        String locationMsgh = "---> %s.%s(Line:%d)：";
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        locationMsgh = String.format(locationMsgh, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return locationMsgh;
    }

    //保存到文件
    public static void file(String msg) {

        if (isShowLog) {
            i("file--->" + msg);
            final String msgStr = msg + "\r\n" + "\r\n";//添加回车换行

            MyThreadPool.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    d("保存位置：" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName + "/" + logName);
                    FileOutputStream outputStream = null;
                    try {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
                        File logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName + "/" + logName);

                        if (!file.exists()) {//判断文件是否存在
                            file.mkdirs();//不存在 创建一个
                            if (!logFile.exists()) {
                                logFile.createNewFile();
                            }
                        }
                        outputStream = new FileOutputStream(logFile, true);//true  不覆盖以前内容   默认为false
                        outputStream.write(msgStr.getBytes());

                    } catch (IOException e) {
                        e.printStackTrace();

                        e("请检查是否开启权限");
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }

    }

}
