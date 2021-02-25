package cn.vacuumflask.commonlib.bluetooth;

import android.bluetooth.BluetoothDevice;

import cn.vacuumflask.commonlib.ConversionUtils;

/**
 * Author: WeiCheng
 * Date: 2021/1/29 12:14 PM
 * Description:信标 实体类
 */
public class BeaconEntity {
    private String name;
    private String mac;
    private int major;
    private int minor;
    private String proximityUuid;
    private String bluetoothAddress;
    private int txPower;
    private int rssi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public String getProximityUuid() {
        return proximityUuid;
    }

    public void setProximityUuid(String proximityUuid) {
        this.proximityUuid = proximityUuid;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public int getTxPower() {
        return txPower;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "BeaconEntity{" +
                "name='" + name + '\'' +
                ", mac='" + mac + '\'' +
                ", major=" + major +
                ", minor=" + minor +
                ", proximityUuid='" + proximityUuid + '\'' +
                ", bluetoothAddress='" + bluetoothAddress + '\'' +
                ", txPower=" + txPower +
                ", rssi=" + rssi +
                '}';
    }

    public static BeaconEntity fromScanData(BluetoothDevice device, int rssi, byte[] scanData) {

        int startByte = 2;
        boolean patternFound = false;
        while (startByte <= 5) {
            if (((int) scanData[startByte + 2] & 0xff) == 0x02 &&
                    ((int) scanData[startByte + 3] & 0xff) == 0x15) {
                // yes!  This is an iBeacon
                patternFound = true;
                break;
            } else if (((int) scanData[startByte] & 0xff) == 0x2d &&
                    ((int) scanData[startByte + 1] & 0xff) == 0x24 &&
                    ((int) scanData[startByte + 2] & 0xff) == 0xbf &&
                    ((int) scanData[startByte + 3] & 0xff) == 0x16) {
                BeaconEntity iBeacon = new BeaconEntity();
                iBeacon.major = 0;
                iBeacon.minor = 0;
                iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000";
                iBeacon.txPower = -55;
                return iBeacon;
            } else if (((int) scanData[startByte] & 0xff) == 0xad &&
                    ((int) scanData[startByte + 1] & 0xff) == 0x77 &&
                    ((int) scanData[startByte + 2] & 0xff) == 0x00 &&
                    ((int) scanData[startByte + 3] & 0xff) == 0xc6) {

                BeaconEntity iBeacon = new BeaconEntity();
                iBeacon.major = 0;
                iBeacon.minor = 0;
                iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000";
                iBeacon.txPower = -55;
                return iBeacon;
            }
            startByte++;
        }


        if (!patternFound) {
            // This is not an iBeacon
            return null;
        }

        BeaconEntity iBeacon = new BeaconEntity();

        iBeacon.major = (scanData[startByte + 20] & 0xff) * 0x100 + (scanData[startByte + 21] & 0xff);
        iBeacon.minor = (scanData[startByte + 22] & 0xff) * 0x100 + (scanData[startByte + 23] & 0xff);
        iBeacon.txPower = (int) scanData[startByte + 24]; // this one is signed
        iBeacon.rssi = rssi;
        iBeacon.mac = device.getAddress();

        // AirLocate:
        // 02 01 1a 1a ff 4c 00 02 15  # Apple's fixed iBeacon advertising prefix
        // e2 c5 6d b5 df fb 48 d2 b0 60 d0 f5 a7 10 96 e0 # iBeacon profile uuid
        // 00 00 # major
        // 00 00 # minor
        // c5 # The 2's complement of the calibrated Tx Power

        // Estimote:
        // 02 01 1a 11 07 2d 24 bf 16
        // 394b31ba3f486415ab376e5c0f09457374696d6f7465426561636f6e00000000000000000000000000000000000000000000000000

        byte[] proximityUuidBytes = new byte[16];
        System.arraycopy(scanData, startByte + 4, proximityUuidBytes, 0, 16);
        String hexString = ConversionUtils.BytesToHexString(proximityUuidBytes);
        StringBuilder sb = new StringBuilder();
        sb.append(hexString.substring(0, 8));
        sb.append("-");
        sb.append(hexString.substring(8, 12));
        sb.append("-");
        sb.append(hexString.substring(12, 16));
        sb.append("-");
        sb.append(hexString.substring(16, 20));
        sb.append("-");
        sb.append(hexString.substring(20, 32));
        iBeacon.proximityUuid = sb.toString();

        if (device != null) {
            iBeacon.bluetoothAddress = device.getAddress();
            iBeacon.name = device.getName();
        }

        return iBeacon;
    }
}
