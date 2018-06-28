package com.wapptech.waytofeed.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.wapptech.waytofeed.Fragment.HomeFragment;
import com.wapptech.waytofeed.R;
import com.wapptech.waytofeed.utlity.Home;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private HomeFragment mContext;
    private List<Home> homeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, view_count, comment_view;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view_count = (TextView) view.findViewById(R.id.view_count);
            comment_view = (TextView) view.findViewById(R.id.comment_view);
//            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
//            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public HomeAdapter(HomeFragment mContext, List<Home> homeList) {
        this.mContext = mContext;
        this.homeList = homeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Home home = homeList.get(position);
        holder.title.setText(home.title);
        holder.count.setText(home.short_discription);
        holder.view_count.setText(home.view_count);
        holder.comment_view.setText(home.comment_count);

        // loading home cover using Glide library
//        Glide.with(mContext).load(home.thumbnail_image).into(holder.thumbnail);
        Context context = holder.thumbnail.getContext();
        Picasso.with(context).load(home.thumbnail_image).placeholder(R.drawable.icon).error(R.drawable.icon).into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }
}