package cn.vacuumflask.commonlib.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Author: WeiCheng
 * Date: 2021/1/29 2:36 PM
 * Description: 蓝牙工具类
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothUtils {
    private static BluetoothUtils bluetoothUtils;
    private Context mContext;
    private BluetoothAdapter bluetoothAdapter;


    public static BluetoothUtils getSingleInstance(Context mContext) {
        if (bluetoothUtils == null) {
            synchronized (BluetoothUtils.class) {
                bluetoothUtils = new BluetoothUtils(mContext);
            }
        }
        return bluetoothUtils;
    }

    private BluetoothUtils(Context mContext) {
        this.mContext = mContext;
        BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        if (adapter != null && adapter.isEnabled()) {
            bluetoothAdapter = adapter;
        }

    }
}
