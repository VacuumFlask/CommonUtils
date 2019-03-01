package cn.vacuumflask.commonlib.serialport;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;
import cn.vacuumflask.commonlib.ConversionUtils;
import cn.vacuumflask.commonlib.L;
import cn.vacuumflask.commonlib.MyThreadPool;

public class SerialportService extends Service {

    private SerialPort mSerialPort;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private ReadThread readThread;

    @Override
    public void onCreate() {
        super.onCreate();
        openSerialport();
    }

    private void openSerialport() {
        try {
            mSerialPort = new SerialPort(new File(SerialPortConstants.SerialPortPath), SerialPortConstants.SerialPortBaudrate, 0);
            mInputStream = mSerialPort.getInputStream();
            mOutputStream = mSerialPort.getOutputStream();

            readThread = new ReadThread(mInputStream);
            MyThreadPool.getInstance().execute(readThread);
            L.i("打开串口");
        } catch (IOException e) {
            e.printStackTrace();
            L.e("关闭串口异常" + e.getMessage());
            return;
        }
        L.i("关闭串口成功");
    }

    public void sendData(String data) {
        MyThreadPool.getInstance().execute(new SendThread(mOutputStream, data));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        closeSerialport();
    }

    private void closeSerialport() {
        try {
            readThread.release();
            MyThreadPool.getInstance().remove(readThread);

            mInputStream.close();
            mOutputStream.close();
            mSerialPort.close();

        } catch (IOException e) {
            e.printStackTrace();
            L.e("关闭串口异常" + e.getMessage());
            return;
        }
        L.i("关闭串口成功");

    }

    class MyBinder extends Binder {
        private SerialportService serialportService;

        MyBinder(SerialportService serialportService) {
            this.serialportService = serialportService;
        }

        public SerialportService getSerialportService() {
            return serialportService;
        }
    }

    private class ReadThread extends Thread {
        private InputStream mInputStream;
        private boolean isThreadStart = true;

        public ReadThread(InputStream mInputStream) {
            this.mInputStream = mInputStream;
        }

        @Override
        public void run() {
            super.run();
            while (isThreadStart) {
                try {
                    byte[] byteArray = new byte[1024];
                    int read = mInputStream.read(byteArray);
                    if (read > 0) {
                        String result = ConversionUtils.bytes2HexStr(byteArray, read);
                        L.d("串口接收数据大小：$read   字符串：$result");
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(SerialPortConstants.SerialPortBroadcastAction_Result, result);
                        intent.putExtras(bundle);
                        sendBroadcast(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

        public void release() {
            isThreadStart = false;
        }
    }

    private class SendThread extends Thread {
        private OutputStream mOutputStream;
        private String data;

        public SendThread(OutputStream mOutputStream, String data) {
            this.mOutputStream = mOutputStream;
            this.data = data;
        }

        @Override
        public void run() {
            super.run();

            L.i("发送数据：" + data);
            try {
                byte[] sendData = ConversionUtils.hexStr2bytes(data);
                if (sendData.length > 0) {
                    mOutputStream.write(sendData);
                    mOutputStream.flush();
                    L.i("发送串口数据成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                L.e("串口发送数据失败：" + e.getMessage());
            }

        }

    }

}
