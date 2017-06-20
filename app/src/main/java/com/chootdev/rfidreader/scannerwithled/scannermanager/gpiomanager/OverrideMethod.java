package com.chootdev.rfidreader.scannerwithled.scannermanager.gpiomanager;

import java.util.HashMap;

/**
 * Created by Choota on 6/19/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

public class OverrideMethod {
    private static HashMap<String, MethodListener> sMethods = new HashMap();
    private static MethodListener sDefaultListener = null;

    public OverrideMethod() {
    }

    public static void setDefaultListener(MethodListener var0) {
        sDefaultListener = var0;
    }

    public static void setMethodListener(String var0, MethodListener var1) {
        if(var1 == null) {
            sMethods.remove(var0);
        } else {
            sMethods.put(var0, var1);
        }

    }

    public static void invokeV(String var0, boolean var1, Object var2) {
        MethodListener var3 = (MethodListener)sMethods.get(var0);
        if(var3 != null) {
            var3.onInvokeV(var0, var1, var2);
        } else if(sDefaultListener != null) {
            sDefaultListener.onInvokeV(var0, var1, var2);
        }

    }

    public static int invokeI(String var0, boolean var1, Object var2) {
        MethodListener var3 = (MethodListener)sMethods.get(var0);
        return var3 != null?var3.onInvokeI(var0, var1, var2):(sDefaultListener != null?sDefaultListener.onInvokeI(var0, var1, var2):0);
    }

    public static long invokeL(String var0, boolean var1, Object var2) {
        MethodListener var3 = (MethodListener)sMethods.get(var0);
        return var3 != null?var3.onInvokeL(var0, var1, var2):(sDefaultListener != null?sDefaultListener.onInvokeL(var0, var1, var2):0L);
    }

    public static float invokeF(String var0, boolean var1, Object var2) {
        MethodListener var3 = (MethodListener)sMethods.get(var0);
        return var3 != null?var3.onInvokeF(var0, var1, var2):(sDefaultListener != null?sDefaultListener.onInvokeF(var0, var1, var2):0.0F);
    }

    public static double invokeD(String var0, boolean var1, Object var2) {
        MethodListener var3 = (MethodListener)sMethods.get(var0);
        return var3 != null?var3.onInvokeD(var0, var1, var2):(sDefaultListener != null?sDefaultListener.onInvokeD(var0, var1, var2):0.0D);
    }

    public static Object invokeA(String var0, boolean var1, Object var2) {
        MethodListener var3 = (MethodListener)sMethods.get(var0);
        return var3 != null?var3.onInvokeA(var0, var1, var2):(sDefaultListener != null?sDefaultListener.onInvokeA(var0, var1, var2):null);
    }
}
