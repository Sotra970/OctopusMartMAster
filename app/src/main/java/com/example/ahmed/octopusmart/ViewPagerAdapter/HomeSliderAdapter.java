package com.example.ahmed.octopusmart.ViewPagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeSliderProducts;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;

public class HomeSliderAdapter extends PagerAdapter {

    private ArrayList<HomeSliderProducts.SliderModel> data;
    private LayoutInflater inflater;
    private  Context context ;
    HomeAdapterListener homeAdapterListener ;


    public HomeSliderAdapter(Context context, ArrayList<HomeSliderProducts.SliderModel> images ,HomeAdapterListener homeAdapterListener ) {
        this.data =images;
        inflater = LayoutInflater.from(context);
        this.context = context ;
        this.homeAdapterListener = homeAdapterListener ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    View myImageLayout;
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
            myImageLayout = inflater.inflate(R.layout.home_slider_item, view, false);
            final HomeSliderProducts.SliderModel current  =  data.get(position) ;
           final ImageView myImage = myImageLayout.findViewById(R.id.image);
            Log.e("HomeSliderAdapterImage" , Config.Image_URL+current.getImage());



            Glide.with(context)
                    .load(Config.Image_URL+current.getImage())
                    .apply(new RequestOptions().fitCenter())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(myImage);


            myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeAdapterListener.onSliderProductClicked(current.getProduct() , myImage , 0);
                }
            });
            view.addView(myImageLayout);

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}