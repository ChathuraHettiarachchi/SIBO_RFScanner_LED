package com.chootdev.rfidreader;

import android.os.Bundle;
import android.serialport.Application;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chootdev.rfidreader.scannerwithled.ledmanager.LEDNotifier;
import com.chootdev.rfidreader.scannerwithled.ledmanager.LEDNotifierException;
import com.chootdev.rfidreader.scannerwithled.scannermanager.RFIDScanner;
import com.chootdev.rfidreader.scannerwithled.scannermanager.RFIDScannerException;

public class MainActivity extends AppCompatActivity implements RFIDScanner.RFIDScannerCallback {

    private TextView tvTitleSysInfo;
    private TextView tvSysInfo;

    private Button btnTurnOff;
    private Button btnTurnRed;
    private Button btnTurnGreen;
    private Button btnTurnBlue;

    private TextView edtCardInfo;

    private Button btnScanCard;
    private Button btnStopScanning;
    private ScrollView scroll;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        try {
            tvSysInfo.setText(LEDNotifier.init(this).getSystemInfo());
        } catch (LEDNotifierException e) {
            e.printStackTrace();
        }

        try {
            RFIDScanner.init(this, this, ((Application) getApplication()))
                    .initiateScanner(true)
                    .startScannerReading();
        } catch (RFIDScannerException e) {
            e.printStackTrace();
        }
    }

    private void initView(){
        tvTitleSysInfo = (TextView) findViewById(R.id.tvTitleSysInfo);
        tvSysInfo = (TextView) findViewById(R.id.tvSysInfo);
        btnTurnOff = (Button) findViewById(R.id.btnTurnOff);
        btnTurnRed = (Button) findViewById(R.id.btnTurnRed);
        btnTurnGreen = (Button) findViewById(R.id.btnTurnGreen);
        btnTurnBlue = (Button) findViewById(R.id.btnTurnBlue);
        edtCardInfo = (TextView) findViewById(R.id.edtCardInfo);
        btnScanCard = (Button) findViewById(R.id.btnScanCard);
        btnStopScanning = (Button) findViewById(R.id.btnStopScanning);
        scroll = (ScrollView) findViewById(R.id.scroll);

        count = 0;

        try {
            LEDNotifier.init(this).turnLEDOff();
        } catch (LEDNotifierException e) {
            e.printStackTrace();
        }

        btnTurnBlue.setOnClickListener(view -> {
            try {
                LEDNotifier.init(getApplicationContext()).turnLEDBlue();
            } catch (LEDNotifierException e) {
                e.printStackTrace();
            }
        });

        btnTurnGreen.setOnClickListener(view -> {
            try {
                LEDNotifier.init(getApplicationContext()).turnLEDGreen();
            } catch (LEDNotifierException e) {
                e.printStackTrace();
            }
        });

        btnTurnRed.setOnClickListener(view -> {
            try {
                LEDNotifier.init(getApplicationContext()).turnLEDRed();
            } catch (LEDNotifierException e) {
                e.printStackTrace();
            }
        });

        btnTurnOff.setOnClickListener(view -> {
            try {
                LEDNotifier.init(getApplicationContext()).turnLEDOff();
            } catch (LEDNotifierException e) {
                e.printStackTrace();
            }
        });

        btnScanCard.setOnClickListener(view -> {
            RFIDScanner.init(this, this, ((Application) getApplication()))
                    .enableAutoReading();
        });

        btnStopScanning.setOnClickListener(view -> {
            RFIDScanner.init(this, this, ((Application) getApplication()))
                    .turnOffAutoReading();
            edtCardInfo.setText("");
            count = 0;
        });
    }

    @Override
    public void onRFIDScannerCatchCard(String content) {
        if (count == 0)
            edtCardInfo.setText("Scanned card inf0\n\n"+(++count)+". "+content+"\n");
        else
            edtCardInfo.append((++count)+". "+content+"\n");

        scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
    }
}
