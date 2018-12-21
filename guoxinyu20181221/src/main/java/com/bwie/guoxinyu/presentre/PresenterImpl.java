package com.bwie.guoxinyu.presentre;


import com.bwie.guoxinyu.activity.MainActivity;
import com.bwie.guoxinyu.model.ModelImpl;
import com.bwie.guoxinyu.model.MyCallBack;
import com.bwie.guoxinyu.view.Iview;

import java.util.Map;

public class PresenterImpl implements Ipresenter{
    private Iview mIview;
    private ModelImpl model;

    public PresenterImpl(Iview iview) {
        mIview = iview;
        model = new ModelImpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> map, Class clazz) {
        model.requestData(url, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIview.requestData(data);
            }
        });
    }
    //防止内存泄漏
    public void onDetach(){
        if(model!=null){
            model = null;
        }
        if(mIview!=null){
            mIview = null;
        }
    }
}
