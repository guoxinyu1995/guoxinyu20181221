package com.bwie.guoxinyu.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.guoxinyu.R;
import com.bwie.guoxinyu.adaper.GridAdaper;
import com.bwie.guoxinyu.adaper.ListAdaper;
import com.bwie.guoxinyu.api.Apis;
import com.bwie.guoxinyu.bean.GridBean;
import com.bwie.guoxinyu.bean.ListsBean;
import com.bwie.guoxinyu.presentre.PresenterImpl;
import com.bwie.guoxinyu.view.Iview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowActivity extends AppCompatActivity implements Iview,View.OnClickListener {
    private PresenterImpl presenter;
    private ImageView sweep;
    private ImageView switch1;
    private RecyclerView recycle_grid;
    private RecyclerView recycle_list;
    private GridAdaper gridAdaper;
    private boolean b = true;
    private ListAdaper adaper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        presenter = new PresenterImpl(this);
        initView();
        initRecycleGrid();
        initRecycleView();
    }
    //切换
    private void initRecycleView() {
        changeRecycleView();
        initData();
    }
    //加载网络数据
    private void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("keywords","手机");
        map.put("page","1");
        map.put("sort","0");
        map.put("uid","71");
        presenter.startRequest(Apis.URL_LIST,map,ListsBean.class);
    }

    //切换布局
    private void changeRecycleView() {
        if(b){
            //创建布局管理器
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            recycle_list.setLayoutManager(layoutManager);
            DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
            recycle_list.addItemDecoration(decoration);
        }else{
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            recycle_list.setLayoutManager(layoutManager);
        }
        adaper = new ListAdaper(this,b);
        adaper.setLongCallBack(new ListAdaper.LongCallBack() {
            @Override
            public void callBack(int i) {
                Toast.makeText(ShowActivity.this,"qwer",Toast.LENGTH_SHORT).show();
            }
        });
        recycle_list.setAdapter(adaper);
        b = !b;
    }

    //九宫格
    private void initRecycleGrid() {
        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        recycle_grid.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //创建适配器
        gridAdaper = new GridAdaper(this);
        recycle_grid.setAdapter(gridAdaper);
        gridData();
    }
    //加载网络数据
    private void gridData() {
        Map<String,String> map = new HashMap<>();
        presenter.startRequest(Apis.URL_GRID,map,GridBean.class);
    }

    private void initView() {
        //获取资源id
        //二维码
        sweep = findViewById(R.id.sweep);
        //切换
        switch1 = findViewById(R.id.switch1);
        recycle_grid = findViewById(R.id.recycle_grid);
        recycle_list = findViewById(R.id.recycle_list);
        sweep.setOnClickListener(this);
        switch1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sweep:
                sweepCoad();
                break;
            case R.id.switch1:
                List<ListsBean.DataBean> data = adaper.getData();
                changeRecycleView();
                adaper.setmList(data);
            default:
                    break;
        }
    }
    //扫描二维码
    private void sweepCoad() {
        //判断是否是6.0
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(ShowActivity.this,Manifest.permission.CAMERA)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ShowActivity.this,new String[]{Manifest.permission.CAMERA},100);
            }else{
                Intent intent = new Intent(ShowActivity.this,ZxingActivity.class);
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(ShowActivity.this,ZxingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(ShowActivity.this,ZxingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof GridBean){
            GridBean gridBean = (GridBean) o;
            if(gridBean == null || !gridBean.isSuccess()){
                Toast.makeText(ShowActivity.this,gridBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                gridAdaper.setData(gridBean.getData());
            }
        }else if(o instanceof ListsBean){
           ListsBean listsBean = (ListsBean) o;
            if(listsBean == null || !listsBean.isSuccess()){
                Toast.makeText(ShowActivity.this,listsBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                adaper.setmList(listsBean.getData());
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if(o instanceof Exception){
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(ShowActivity.this,"网络请求错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
