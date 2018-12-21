package com.bwie.guoxinyu.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.guoxinyu.R;
import com.bwie.guoxinyu.bean.ListsBean;
import java.util.ArrayList;
import java.util.List;

public class ListAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private List<ListsBean.DataBean> mList;
    private Context mContext;
    private Boolean b;

    public ListAdaper(Context mContext, Boolean b) {
        this.mContext = mContext;
        this.b = b;
        mList = new ArrayList<>();
    }
    public void setmList(List<ListsBean.DataBean> lists){
        mList.addAll(lists);
        notifyDataSetChanged();
    }
    public List<ListsBean.DataBean> getData(){
        return mList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        if(b){
            View view = LayoutInflater.from(mContext).inflate(R.layout.linear_item,viewGroup,false);
            holder = new ViewHolderLinear(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.stagger_item,viewGroup,false);
            holder = new ViewHolderStagger(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ListsBean.DataBean dataBean = mList.get(i);
       /* String images = dataBean.getImages();
        String[] img = images.split("\\|");*/
        if(b){
            ViewHolderLinear holderLinear = (ViewHolderLinear) viewHolder;
            holderLinear.linar_title.setText(dataBean.getTitle());
            holderLinear.linar_price.setText("价格"+dataBean.getPrice());
           // Glide.with(mContext).load(img[0]).into(holderLinear.linear_image);
            holderLinear.layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(longCallBack!=null){
                        longCallBack.callBack(i);
                    }
                    return true;
                }
            });
        }else{
            ViewHolderStagger holderStagger = (ViewHolderStagger) viewHolder;
            holderStagger.stagger_title.setText(dataBean.getTitle());
            holderStagger.stagger_price.setText("价格"+dataBean.getPrice());
            //Glide.with(mContext).load(img[0]).into(holderStagger.stagger_image);
            holderStagger.layout1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longCallBack!=null){
                        longCallBack.callBack(i);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    //线性
    class ViewHolderLinear extends RecyclerView.ViewHolder{
        public final ImageView linear_image;
        public final TextView linar_title;
        public final TextView linar_price;
        public final ConstraintLayout layout;
        public ViewHolderLinear(@NonNull View itemView) {
            super(itemView);
            linear_image = itemView.findViewById(R.id.linear_image);
            linar_title = itemView.findViewById(R.id.linar_title);
            linar_price = itemView.findViewById(R.id.linar_price);
            layout = itemView.findViewById(R.id.con);
        }
    }
    //瀑布
    class ViewHolderStagger extends RecyclerView.ViewHolder{
        public final ImageView stagger_image;
        public final TextView stagger_title;
        public final TextView stagger_price;
        public final ConstraintLayout layout1;
        public ViewHolderStagger(@NonNull View itemView) {
            super(itemView);
            stagger_image = itemView.findViewById(R.id.stagger_image);
            stagger_title = itemView.findViewById(R.id.stagger_title);
            stagger_price = itemView.findViewById(R.id.stagger_price);
            layout1 = itemView.findViewById(R.id.conn);
        }
    }
    private LongCallBack longCallBack;
    public void setLongCallBack(LongCallBack longCallBack){
        this.longCallBack = longCallBack;
    }
    //定义接口
    public interface LongCallBack{
        void callBack(int i);
    }
}
