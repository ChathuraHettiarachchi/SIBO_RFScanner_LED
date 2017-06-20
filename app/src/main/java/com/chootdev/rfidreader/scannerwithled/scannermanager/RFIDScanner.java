package com.chootdev.rfidreader.scannerwithled.scannermanager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.serialport.Application;
import android.serialport.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Choota on 6/19/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

public class RFIDScanner {

    protected static Application mApplication;
    protected static SerialPort mSerialPort;

    private static InputStream mInputStream;
    private static OutputStream mOutputStream;

    private static Context context;
    private static RFIDScanner scanner;
    private static RFIDScannerCallback callback;

    private static byte[] reciveBuffer = new byte[128];  //data
    private static boolean currentSystemStatus;
    private static boolean threadFlag = true;
    private static boolean stxCheck = false;
    private static int count = 0;
    private static int size = 0;
    private static int blockNumber = 01;
    private static String display = "";
    private static String currentCmd = "1";
    private static String stx = "20 00";
    private static String send = "4F 00";
    private static String bcc = "";
    private static List<String> hexData;

    private final static String Auto_Read_ID = "80 04 07 03 01 00";
    private final static String Stop_Auto_Reading = "81 01 00";

    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.obj != null) {
                        hexData = Arrays.asList(msg.obj.toString().split("\\s+"));

                        if (hexData != null && hexData.size() > 8) {
                            int dataLength = (int) Long.parseLong(hexData.get(7), 16);

                            List<String> cardData = hexData.subList(8, (8 + dataLength));
                            String cardNumber = "";

                            for (int i = 0; i < cardData.size(); i++) {
                                if (i == cardData.size() - 1)
                                    cardNumber += cardData.get(i);
                                else
                                    cardNumber += (cardData.get(i) + " ");
                            }
                            callback.onRFIDScannerCatchCard(cardNumber);
                        }
                    }
                    break;
            }
        }
    };

    public static RFIDScanner init(Context appContext, RFIDScannerCallback scannerCallback, Application application) {
        context = appContext;
        callback = scannerCallback;
        mApplication = application;
        hexData = new ArrayList<>();

        stx = "20 00";
        send = "4F 00";
        currentCmd = "1";
        blockNumber = 1;

        if (scanner == null)
            scanner = new RFIDScanner();

        return scanner;
    }

    public static RFIDScanner initiateScanner(boolean systemStatus) throws RFIDScannerException {
        currentSystemStatus = systemStatus;
        if (systemStatus) {
            try {
                mSerialPort = mApplication.getSerialPort();
                mOutputStream = mSerialPort.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RFIDScannerException("System cannot initiate RFID scanner");
            } catch (SecurityException e) {
                e.printStackTrace();
                throw new RFIDScannerException("System cannot initiate RFID scanner");
            }
        }

        return scanner;
    }

    public static void startScannerReading() {
        if (currentSystemStatus) {
            readRFIDThread();
        }
    }

    public static void readRFIDThread() {
        mInputStream = mSerialPort.getInputStream();
        new Thread(() -> {
            while (threadFlag) {
                if (mInputStream != null) {
                    try {
                        byte[] receiveBuffer = new byte[128];
                        size = mInputStream.read(receiveBuffer);
                        display = bytes2HexString(receiveBuffer, size);

                        count = size + count;
                        stxChecker();

                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = display;
                        mHandler.sendMessage(msg); // to display data
                        display = "";

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onRFIDScannerFireException("System cannot initiate RFID scanner");
                    }
                }
            }
        }).start();
    }

    private static String bytes2HexString(byte[] b, int size) {
        String hex = "";
        String out = "";
        for (int i = 0; i < size; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);

            if (hex != null) {
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                out += hex + " ";
            }

        }

        return out.toUpperCase();
    }

    private static void stxChecker() {
        if (reciveBuffer[0] == 0x15) {
            mHandler.sendEmptyMessage(2);
        } else if (reciveBuffer[0] == 0x06) {
            count = 0;
        } else if (reciveBuffer[0] == 0x20 && stxCheck == false) {
            mHandler.sendEmptyMessageDelayed(0, 100);
            stxCheck = true;
        } else if (stxCheck == false) {
            mHandler.sendEmptyMessage(3);
        }
    }

    public static void enableAutoReading() {
        send = Auto_Read_ID;
        bcc = "";

        serialPortSender();
    }

    public static void turnOffAutoReading() {
        send = Stop_Auto_Reading;
        bcc = "";

        serialPortSender();
    }

    private static void serialPortSender() {
        bcc();
        byte[] outData = toArray(send.toString());
        byte[] stxx = toArray(stx.toString());
        byte[] bccc = toArray(bcc.toString());
        byte[] outBuffer = new byte[4 + outData.length];

        if (stxx.length == 2 && bccc.length == 2) {

            System.arraycopy(stxx, 0, outBuffer, 0, stxx.length);
            System.arraycopy(outData, 0, outBuffer, 2, outData.length);
            System.arraycopy(bccc, 0, outBuffer, outBuffer.length - 2, bccc.length);

            try {
                mOutputStream.write(outBuffer, 0, outBuffer.length);
                for (int i = 0; i < outBuffer.length; i++) {
                }
            } catch (IOException e) {
                callback.onRFIDScannerFireException("System cannot handle IO devices and values");
            }
        } else {
            System.out.println("sum error");
        }

        switch (outData[0]) {
            case (byte) 0x4F:
                currentCmd = "1";
                break;
            case (byte) 0x80:
                currentCmd = "2";
                break;
            case (byte) 0x81:
                currentCmd = "3";
                break;
            case (byte) 0x10:
                currentCmd = "5";
                break;
            case (byte) 0x4C:
                currentCmd = "6";
                break;
            case (byte) 0x12:
                currentCmd = "7";
                break;
            case (byte) 0x11:
                currentCmd = "8";
                break;

        }
    }

    private static void bcc() {
        byte[] ary = toArray(send.toString());
        int result = (byte) (ary[0] ^ ary[1]);
        for (int i = 2; i < ary.length; i++) {
            result = (byte) (result ^ ary[i]);
        }
        bcc = Integer.toHexString(~result & 0xFF).toUpperCase() + " 03";
    }

    private static byte[] toArray(String cmd) {
        cmd = cmd.trim().replace(" ", "");
        int cmd_length = cmd.length() / 2;
        byte[] outBuffer = new byte[cmd_length];

        if (cmd_length > 0) {
            for (int i = 0; i < cmd_length; i++) {
                String k = cmd.substring(i * 2, (i + 1) * 2);
                try {
                    outBuffer[i] = (byte) Integer.parseInt(k, 16);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onRFIDScannerFireException("Card info and byte array mismatch");
                }
            }
        }
        return outBuffer;
    }

    public interface RFIDScannerCallback {
        void onRFIDScannerCatchCard(String content);
        void onRFIDScannerFireException(String message);
    }
}
