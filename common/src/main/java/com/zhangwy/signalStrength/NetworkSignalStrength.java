package com.zhangwy.signalStrength;

import android.content.Context;

public abstract class NetworkSignalStrength {
    private static NetworkSignalStrength instance;
    public static NetworkSignalStrength getInstance() {
        if (instance == null) {
            synchronized (NetworkSignalStrength.class) {
                if (instance == null) {
                    instance = new NetworkSignalStrengthImpl();
                }
            }
        }
        return instance;
    }

    public abstract void init(Context context);

    public abstract int getSignalStrength(NetWorkType type);

    public enum NetWorkType {
        WiFi, Mobile;
    }
}
