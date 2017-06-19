package com.chootdev.rfidreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chootdev.rfidreader.scannerwithled.LEDNotifier;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitleSysInfo;
    private TextView tvSysInfo;

    private Button btnTurnOff;
    private Button btnTurnRed;
    private Button btnTurnGreen;
    private Button btnTurnBlue;

    private EditText edtCardInfo;

    private Button btnScanCard;
    private Button btnStopScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        tvSysInfo.setText(LEDNotifier.init(this).getSystemInfo());
    }

    private void initView() {
        tvTitleSysInfo = (TextView) findViewById(R.id.tvTitleSysInfo);
        tvSysInfo = (TextView) findViewById(R.id.tvSysInfo);
        btnTurnOff = (Button) findViewById(R.id.btnTurnOff);
        btnTurnRed = (Button) findViewById(R.id.btnTurnRed);
        btnTurnGreen = (Button) findViewById(R.id.btnTurnGreen);
        btnTurnBlue = (Button) findViewById(R.id.btnTurnBlue);
        edtCardInfo = (EditText) findViewById(R.id.edtCardInfo);
        btnScanCard = (Button) findViewById(R.id.btnScanCard);
        btnStopScanning = (Button) findViewById(R.id.btnStopScanning);

        LEDNotifier.init(this).turnLEDOff();

        btnTurnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LEDNotifier.init(getApplicationContext()).turnLEDBlue();
            }
        });

        btnTurnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LEDNotifier.init(getApplicationContext()).turnLEDGreen();
            }
        });

        btnTurnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LEDNotifier.init(getApplicationContext()).turnLEDRed();
            }
        });

        btnTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LEDNotifier.init(getApplicationContext()).turnLEDOff();
            }
        });
    }
}
