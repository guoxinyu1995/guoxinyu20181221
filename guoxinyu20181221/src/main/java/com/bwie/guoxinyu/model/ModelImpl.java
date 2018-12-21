package com.bwie.guoxinyu.model;

import com.bwie.guoxinyu.util.ICallBack;
import com.bwie.guoxinyu.util.OkHttpUtil;

import java.util.Map;

public class ModelImpl implements Imodel {
    private MyCallBack myCallBack;
    @Override
    public void requestData(String url, final Map<String, String> map, Class clazz, final MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        OkHttpUtil.getIntance().doPost(url, map, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void faniled(Exception e) {
                myCallBack.setData(e.getMessage());
            }
        });
    }
}
