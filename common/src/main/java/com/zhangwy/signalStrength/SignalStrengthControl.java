package com.zhangwy.signalStrength;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.zhangwy.util.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SignalStrengthControl {

    private static final String TAG = SignalStrengthControl.class.getSimpleName();

    public static SignalStrengthControl get() {
        return SignalStrengthHolder.INSTANCE;
    }

    private static class SignalStrengthHolder {
        static SignalStrengthControl INSTANCE = new SignalStrengthControl();
    }

    private WifiManager mWifiManager;
    private ConnectivityManager mConnectManager;

    private SignalStrength mSignalStrength;
    private boolean flag_init_success;

    public void init(Context context) {
        try {
            if (flag_init_success)
                return;
            context = context.getApplicationContext();

            mConnectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            TelephonyManager mTeleManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTeleManager != null) {
                mTeleManager.listen(new PhoneStateListener() {
                    @Override
                    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                        Logger.i(TAG, "onSignalStrengthsChanged");
                        mSignalStrength = signalStrength;
                    }
                }, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            }
            flag_init_success = true;
        } catch (Throwable e) {
            flag_init_success = false;
            Logger.e(TAG, e);
        }
    }

    public synchronized Item getCurrentValue() {
        if (!flag_init_success || mConnectManager == null)
            return null;

        boolean isNull = false, isAvailable = false, isConnected = false;

        try {
            NetworkInfo networkInfo = mConnectManager.getActiveNetworkInfo();

            if (isNull = (networkInfo != null) && (isAvailable = networkInfo.isAvailable()) && (isConnected = networkInfo.isConnected())) {
                if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                    return getWifiRssi();
                } else if (ConnectivityManager.TYPE_MOBILE == networkInfo.getType()
                        && TelephonyManager.NETWORK_TYPE_LTE == networkInfo.getSubtype()) {
                    return get4GSignalStrength();
                }
            }
        } catch (Throwable e) {
            Logger.e(TAG, e);
        }
        int value = -1;
        try {
            value = Integer.valueOf("5" + getValue(isNull) + getValue(isAvailable) + getValue(isConnected) + "5");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new Item(value, -1, "unknown");
    }

    private String getValue(boolean flag) {
        return flag ? "1" : "0";
    }

    private Item get4GSignalStrength() {
        if (mSignalStrength == null)
            return null;

        int mLteRsrp = getField(mSignalStrength, "mLteRsrp");
        int Level = getMethod(mSignalStrength, "getLevel");

        Logger.i(TAG, "mLteRsrp = " + mLteRsrp + " Level = " + Level);
        return new Item(mLteRsrp, Level, "Mobile");
    }

    private Item getWifiRssi() {
        if (mWifiManager == null)
            return null;

        WifiInfo wifiInfo = null;
        if (mWifiManager.isWifiEnabled()) {
            wifiInfo = mWifiManager.getConnectionInfo();
        }

        if (wifiInfo != null) {
            int rssi = wifiInfo.getRssi();
            int level = WifiManager.calculateSignalLevel(rssi, 5);
            Logger.i(TAG, "wifiInfo  rssi = " + rssi + " level = " + level);
            return new Item(rssi, level, "WiFi");
        }
        return null;
    }

    public static class Item {
        public int signalstrength;
        public int signallevel;
        public String network;

        public Item(int signalstrength, int signallevel, String network) {
            this.signalstrength = signalstrength;
            this.signallevel = signallevel;
            this.network = network;
        }
    }

    private static int getField(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (int) field.get(instance);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int getMethod(Object instance, String fieldName) {
//        try {
//            Method method = instance.getClass().getDeclaredMethod(fieldName, null);
//            method.setAccessible(true);
//            return (int) method.invoke(instance, null);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
        return 0;
    }

}
