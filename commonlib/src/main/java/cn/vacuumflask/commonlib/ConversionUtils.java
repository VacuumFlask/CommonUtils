package cn.vacuumflask.commonlib;

public class ConversionUtils {

    /**
     * 字节数组转换成对应的16进制表示的字符串
     *
     * @param data   目标数组
     * @param length 长度
     * @return 字符串
     */
    public static String bytes2HexStr(byte[] data, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(data, 0, temp, 0, length);
        return bytes2HexStr(temp);
    }

    /**
     * 字节数组转换成对应的16进制表示的字符串
     *
     * @param data 目标数组
     * @return 大写字符串
     */
    public static String bytes2HexStr(byte[] data) {
        StringBuffer sBuffer = new StringBuffer();

        for (byte datum : data) {
            String hexString = Integer.toHexString(datum);
            if (hexString.length() < 2) {
                sBuffer.append(0);
            }
            sBuffer.append(hexString);
        }

        return sBuffer.toString().toUpperCase();
    }


    /**
     * 把十六进制表示的字节数组字符串，转换成十六进制字节数组
     *
     * @param inHex 目标字符串
     */
    public static byte[] hexStr2bytes(String inHex) {
        int hexlen = inHex.length();
        String str = "";
        byte[] bytes;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            bytes = new byte[hexlen / 2];
            str = "0" + inHex;
        } else {
            bytes = new byte[hexlen / 2];
            str = inHex;
        }

        int j = 0;
        int i = 0;

        while (i < hexlen) {
            bytes[j] = hexToByte(str.substring(i, i + 2));
            j++;
            i += 2;
        }

        return bytes;
    }


    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


}
