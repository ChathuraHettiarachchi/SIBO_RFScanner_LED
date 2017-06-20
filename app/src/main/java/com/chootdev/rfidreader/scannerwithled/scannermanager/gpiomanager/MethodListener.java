package com.chootdev.rfidreader.scannerwithled.scannermanager.gpiomanager;

/**
 * Created by Choota on 6/19/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

public interface MethodListener {
    void onInvokeV(String var1, boolean var2, Object var3);

    int onInvokeI(String var1, boolean var2, Object var3);

    long onInvokeL(String var1, boolean var2, Object var3);

    float onInvokeF(String var1, boolean var2, Object var3);

    double onInvokeD(String var1, boolean var2, Object var3);

    Object onInvokeA(String var1, boolean var2, Object var3);
}
