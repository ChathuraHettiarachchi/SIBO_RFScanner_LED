package com.chootdev.rfidreader.scannerwithled;

import android.app.smdt.SmdtManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Choota on 6/19/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

public class LEDNotifier {

    private static LEDNotifier notifier;
    private static Context context;
    private static SmdtManager manager;

    public static LEDNotifier init(Context appContext) {
        if (notifier != null) {
            context = appContext;
            manager.create(context);
            return notifier;
        } else {
            context = appContext;
            notifier = new LEDNotifier();
            manager = SmdtManager.create(appContext);
            return notifier;
        }
    }

    public static void turnLEDOff() {
        manager.smdtSetExtrnalGpioValue(1, true);
        manager.smdtSetExtrnalGpioValue(2, true);
    }

    public static void turnLEDRed() {
        manager.smdtSetExtrnalGpioValue(1, false);
        manager.smdtSetExtrnalGpioValue(2, false);
    }

    public static void turnLEDGreen() {
        manager.smdtSetExtrnalGpioValue(1, false);
        manager.smdtSetExtrnalGpioValue(2, true);
    }

    public static void turnLEDBlue() {
        manager.smdtSetExtrnalGpioValue(1, true);
        manager.smdtSetExtrnalGpioValue(2, false);
    }

    public static String getSystemInfo() {
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMan.getConnectionInfo();
        String mac = info.getMacAddress();

         return  "SW SDK=" + manager.getFirmwareVersion() +
                "\nDDR size=" + manager.getRunningMemory() +
                "\nEMMC size=" + manager.getInternalStorageMemory() +
                "\nETH MAC=" + manager.smdtGetEthMacAddress() +
                "\nWIFI MAC=" + mac +
                "\nS/N: " + android.os.Build.SERIAL;
    }
}
