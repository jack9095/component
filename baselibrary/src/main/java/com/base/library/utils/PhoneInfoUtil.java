package com.base.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * 手机设备信息
 * 2017-1-13
 * 测试机型：
 * S7-601w 4.1.2系统
 * IMEI,Serial,Mac,AndroidId都有
 * FDR-A01w 5.1.1系统
 * IMEI=null
 * 其他Serial,Mac,AndroidId都有
 * 坚果
 * IMEI,Serial,Mac,AndroidId都有
 */
public class PhoneInfoUtil {

    /**
     * 获取手机的 mac 地址
     * https://blog.csdn.net/hecate1994/article/details/79160229
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    public static String getMacAddress(Context context) {

        String macAddress = null;
        WifiManager wifiManager =
                (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());

        if (wifiManager != null && !wifiManager.isWifiEnabled()) {
            //必须先打开，才能获取到MAC地址
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(false);
        }
        if (null != info) {
            macAddress = info.getMacAddress();
        }
        return macAddress;
    }

    /**
     * 获取设备UUID  获取安卓手机唯一设备号方法   要动态获取权限
     */
//    @SuppressLint({"HardwareIds", "MissingPermission"})
//    private String getMyUUID(Context context) {
//
//        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        final String tmDevice, tmSerial, androidId;
//        // TODO 要动态获取权限
//        tmDevice = "" + tm.getDeviceId();//设备唯一号码
//        tmSerial = "" + tm.getSimSerialNumber();//sim 卡标识
//        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);//在设备首次启动时，系统会随机生成一个64位的数字
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        return deviceUuid.toString();
//    }

    /**
     * TODO 序列号
     * 从Android 2.3 (“Gingerbread”)开始可用，可以通过android.os.Build.SERIAL获取，对于没有通话功能的设备，它会
     * 返回一个唯一的device ID
     *
     * @return
     */
    public static String getSerial() {
        try {
            String str = android.os.Build.class.getField("SERIAL").get(null).toString();
            return str;
        } catch (IllegalAccessException | IllegalArgumentException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 获取设备的IMEI
     * IMEI
     *方式：TelephonyManager.getDeviceId():
     *问题
     *范围：网上说“只能支持拥有通话功能的设备，对于平板不可以”，但是我测试了型号FDR-A01w平板确实拿到的是null,
     *而 型号S7-601的平板却能拿到。
     *持久性：返厂，数据擦除的时候不彻底，保留了原来的标识。
     *权限：需要权限：android.permission.READ_PHONE_STATE
     *bug: 有些厂家的实现有bug，返回一些不可用的数据
     * @return
     */
//    public static String getLocalIMEI(Context context)
//    {
//        TelephonyManager tm = null;
//        try
//        {
//            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (null != tm)
//            {
//                return tm.getDeviceId();
//            }
//        } catch (Exception ex)
//        {
//        } finally
//        {
//            tm = null;
//        }
//        return null;
//    }

    /**
     * Administrator
     * 2017-1-13
     * TODO
     * Mac地址
     * ACCESS_WIFI_STATE权限
     * 有些设备没有WiFi，或者蓝牙，就不可以，如果WiFi没有打开，硬件也不会返回Mac地址
     *
     * @return
     */
    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


    // Android Id

    /**
     * Administrator
     * 2017-1-13
     * TODO
     * ANDROID_ID
     * 2.2（Froyo，8）版本系统会不可信，来自主要生产厂商的主流手机，至少有一个普遍发现的bug，这些有问题的手机相同的ANDROID_ID: 9774d56d682e549c
     * 但是如果返厂的手机，或者被root的手机，可能会变
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }
}
