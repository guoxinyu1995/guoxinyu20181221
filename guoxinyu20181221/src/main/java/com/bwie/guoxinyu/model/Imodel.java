package com.bwie.guoxinyu.model;

import java.util.Map;

public interface Imodel {
    void requestData(String url, Map<String,String> map,Class clazz,MyCallBack myCallBack);
}
