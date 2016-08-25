package com.wicare.wistormdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_user, R.id.btn_device,R.id.btn_vehicle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_user:
                UserApiActivity.startAciton(MainActivity.this);
                break;
            case R.id.btn_device:
                DeviceApiActivity.startAciton(MainActivity.this);
                break;
            case R.id.btn_vehicle:
                VehicleActivity.startAction(MainActivity.this);
                break;
        }
    }
}
