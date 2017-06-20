package com.chootdev.rfidreader.scannerwithled.scannermanager.gpiomanager;

/**
 * Created by Choota on 6/19/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

public class Gpio {
    public static final String TAG = "GPIO";
    private static final String mPathstr = "/sys/class/gpio_sw/P";
    private static final String mDataName = "/data";
    private static final String mPullName = "/pull";
    private static final String mDrvLevelName = "/drv_level";
    private static final String mMulSelName = "/mul_sel";

    private Gpio() {
    }

    private static int nativeWriteGpio(String var0, String var1) {
        return OverrideMethod.invokeI("android.os.Gpio#nativeWriteGpio(Ljava/lang/String;Ljava/lang/String;)I", true, (Object)null);
    }

    private static int nativeReadGpio(String var0) {
        return OverrideMethod.invokeI("android.os.Gpio#nativeReadGpio(Ljava/lang/String;)I", true, (Object)null);
    }

    public static int writeGpio(char group, int num, int value) {
        String dataPath = composePinPath(group, num).concat("/data");
        return nativeWriteGpio(dataPath, Integer.toString(value));
    }

    public static int readGpio(char group, int num) {
        String dataPath = composePinPath(group, num).concat("/data");
        return nativeReadGpio(dataPath);
    }

    public static int setPull(char group, int num, int value) {
        String dataPath = composePinPath(group, num).concat("/pull");
        return nativeWriteGpio(dataPath, Integer.toString(value));
    }

    public static int getPull(char group, int num) {
        String dataPath = composePinPath(group, num).concat("/pull");
        return nativeReadGpio(dataPath);
    }

    public static int setDrvLevel(char group, int num, int value) {
        String dataPath = composePinPath(group, num).concat("/drv_level");
        return nativeWriteGpio(dataPath, Integer.toString(value));
    }

    public static int getDrvLevel(char group, int num) {
        String dataPath = composePinPath(group, num).concat("/drv_level");
        return nativeReadGpio(dataPath);
    }

    public static int setMulSel(char group, int num, int value) {
        String dataPath = composePinPath(group, num).concat("/mul_sel");
        return nativeWriteGpio(dataPath, Integer.toString(value));
    }

    public static int getMulSel(char group, int num) {
        String dataPath = composePinPath(group, num).concat("/mul_sel");
        return nativeReadGpio(dataPath);
    }

    private static String composePinPath(char group, int num) {
        String groupstr = String.valueOf(group).toUpperCase();
        String numstr = Integer.toString(num);
        return "/sys/class/gpio_sw/P".concat(groupstr).concat(numstr);
    }
}
