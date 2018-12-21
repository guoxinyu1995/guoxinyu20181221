package com.bwie.guoxinyu.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.guoxinyu.R;
import com.bwie.guoxinyu.bean.GridBean;

import java.util.ArrayList;
import java.util.List;

public class GridAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GridBean.DataBean> mData;
    private Context mContext;

    public GridAdaper(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }
    public void setData(List<GridBean.DataBean> datas){
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_grid_item,viewGroup,false);
        ViewHolderGrid holderGrid = new ViewHolderGrid(view);
        return holderGrid;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderGrid holderGrid = (ViewHolderGrid) viewHolder;
        holderGrid.text.setText(mData.get(i).getName());
        Glide.with(mContext).load(mData.get(i).getIcon()).into(holderGrid.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHolderGrid extends RecyclerView.ViewHolder{
        public final ImageView image;
        public final TextView text;
        public ViewHolderGrid(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.grid_image);
            text = itemView.findViewById(R.id.grid_title);
        }
    }
}
