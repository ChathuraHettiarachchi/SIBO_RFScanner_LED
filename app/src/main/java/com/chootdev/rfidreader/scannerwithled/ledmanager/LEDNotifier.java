package com.chootdev.rfidreader.scannerwithled.ledmanager;

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

    public static LEDNotifier init(Context appContext) throws LEDNotifierException {
        try {
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
        } catch (Exception e) {
            throw new LEDNotifierException("No LED service fount on this device");
        }
    }

    public static void turnLEDOff() throws LEDNotifierException {
        try {
            manager.smdtSetExtrnalGpioValue(1, true);
            manager.smdtSetExtrnalGpioValue(2, true);
        } catch (Exception e) {
            throw new LEDNotifierException("No LED service fount on this device");
        }
    }

    public static void turnLEDRed() throws LEDNotifierException {
        try {
            manager.smdtSetExtrnalGpioValue(1, false);
            manager.smdtSetExtrnalGpioValue(2, false);
        } catch (Exception e) {
            throw new LEDNotifierException("No LED service fount on this device");
        }
    }

    public static void turnLEDGreen() throws LEDNotifierException {
        try {
            manager.smdtSetExtrnalGpioValue(1, false);
            manager.smdtSetExtrnalGpioValue(2, true);
        } catch (Exception e) {
            throw new LEDNotifierException("No LED service fount on this device");
        }
    }

    public static void turnLEDBlue() throws LEDNotifierException {
        try {
            manager.smdtSetExtrnalGpioValue(1, true);
            manager.smdtSetExtrnalGpioValue(2, false);
        } catch (Exception e) {
            throw new LEDNotifierException("No LED service fount on this device");
        }
    }

    public static String getSystemInfo() throws LEDNotifierException {
        try {
            WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiMan.getConnectionInfo();
            String mac = info.getMacAddress();

            return "SW SDK=" + manager.getFirmwareVersion() +
                    "\nDDR size=" + manager.getRunningMemory() +
                    "\nEMMC size=" + manager.getInternalStorageMemory() +
                    "\nETH MAC=" + manager.smdtGetEthMacAddress() +
                    "\nWIFI MAC=" + mac +
                    "\nS/N: " + android.os.Build.SERIAL;
        } catch (Exception e) {
            throw new LEDNotifierException("No LED service fount on this device");
        }
    }
}
