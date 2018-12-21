package com.bwie.guoxinyu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bwie.guoxinyu.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZxingActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZXingView xingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        //获取资源id
        xingView = findViewById(R.id.zxing);
        xingView.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //打开后置摄像头
        xingView.startCamera();
        //延迟
        xingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xingView.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
