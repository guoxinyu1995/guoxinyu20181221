package com.bwie.guoxinyu.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bwie.guoxinyu.R;

public class CustomView extends LinearLayout {
    private Context context;
    private EditText editText;

    public CustomView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CustomView(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(context,R.layout.search_item,null);
        editText = view.findViewById(R.id.search);
        addView(view);
    }
}
