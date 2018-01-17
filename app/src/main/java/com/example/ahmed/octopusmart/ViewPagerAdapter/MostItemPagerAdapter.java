package com.example.ahmed.octopusmart.ViewPagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;

public class MostItemPagerAdapter extends PagerAdapter {

    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private  Context context ;
    public MostItemPagerAdapter(Context context, ArrayList<Integer> images) {
        this.images=images;
        inflater = LayoutInflater.from(context);
        this.context = context ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.popular_pager, view, false);
//        ImageView myImage = myImageLayout
//        .findViewById(R.id.pager_img);
//        Glide.with(context)
//                .load(R.drawable.mobile2)
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .crossFade()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(myImage);
        view.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}