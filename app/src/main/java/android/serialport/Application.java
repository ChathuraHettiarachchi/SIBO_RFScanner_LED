package android.serialport;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;


public class Application extends android.app.Application {

    private SerialPort mSerialPort = null;

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            mSerialPort = new SerialPort(new File("/dev/ttyS3"), 9600);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }
}
