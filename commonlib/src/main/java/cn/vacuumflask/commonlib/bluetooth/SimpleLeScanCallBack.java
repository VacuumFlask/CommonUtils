package cn.vacuumflask.commonlib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: WeiCheng
 * Date: 2021/1/29 12:23 PM
 * Description: BLE 蓝牙搜索 回调接口（只做搜索 不做连接）
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public abstract class SimpleLeScanCallBack implements BluetoothAdapter.LeScanCallback {
    private final List<BeaconEntity> datas;
    private final BluetoothAdapter bluetoothAdapter;
    private boolean isEnable;
    private final Timer timer;
    private final static int DelayTime = 2000;//延时停止简体监听
    private int index;//周期次数

    public SimpleLeScanCallBack(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.datas = new ArrayList<>();
        timer = new Timer();
    }

    /**
     * 每两秒 获取一次 数据 获取 五次后 结束
     */
    public void startCycle() {
        isEnable = true;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (index >= 5) {
                    isEnable = false;
                    bluetoothAdapter.stopLeScan(SimpleLeScanCallBack.this);
                    timer.cancel();
                    return;
                }

                index++;
                onScanFinish(datas);


            }
        }, DelayTime, DelayTime);//立即开始 每两秒返回一次结果，第五次后 结束

    }

    /**
     * 自定义 运行时间
     * @param delay 延时开始
     * @param period 每多少时间重复
     */
    public void startCycle(long delay, long period){
        isEnable = true;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScanFinish(datas);

            }
        }, delay, period);

    }

    /**
     * 开启10秒后 获取数据 并结束
     */
    public void start() {
        isEnable = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isEnable = false;
                bluetoothAdapter.stopLeScan(SimpleLeScanCallBack.this);
                onScanFinish(datas);
                timer.cancel();
            }
        }, DelayTime * 5);//开启10秒后关闭
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (isEnable) {
            BeaconEntity entity = BeaconEntity.fromScanData(device, rssi, scanRecord);
            if (entity != null) {
                ListIterator<BeaconEntity> iterator = datas.listIterator();
                while (iterator.hasNext()) {
                    BeaconEntity beaconEntity = iterator.next();
                    String address = beaconEntity.getBluetoothAddress();
                    if (address.equals(entity.getBluetoothAddress())) {
                        iterator.remove();//删除重复
                        break;
                    }
                }
                datas.add(entity);
            }
        }

    }

    public abstract void onScanFinish(List<BeaconEntity> datas);

    /**
     * 停止监听
     * @return 监听期间获取的数据
     */
    public List<BeaconEntity> stopAndGetDatas(){
        isEnable = false;
        timer.cancel();
        bluetoothAdapter.stopLeScan(SimpleLeScanCallBack.this);
        return datas;
    }

    /**
     * 停止监听
     */
    public void  stop(){
        isEnable = false;
        timer.cancel();
        bluetoothAdapter.stopLeScan(SimpleLeScanCallBack.this);
    }
}
