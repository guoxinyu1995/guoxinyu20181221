package com.bwie.guoxinyu.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bwie.guoxinyu.R;
import com.bwie.guoxinyu.adaper.MainAdaper;
import com.bwie.guoxinyu.presentre.PresenterImpl;
import com.bwie.guoxinyu.view.Iview;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源id
        button = findViewById(R.id.btn);
        viewPager = findViewById(R.id.view_pager);
        //访问网络图片
        final List<String> list = new ArrayList<>();
        list.add("http://www.zhaoapi.cn/images/quarter/ad1.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad2.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad3.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad4.png");
        //创建适配器
        MainAdaper adaper = new MainAdaper(list,MainActivity.this);
        viewPager.setAdapter(adaper);
        //设置页面改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(list.size()-1 == i){
                    //显示按钮
                    button.setVisibility(View.VISIBLE);
                }else{
                    //隐藏
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //点击跳转
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




}
