package com.example.ahmed.octopusmart.RecyclerAdapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.App.Config;

import java.util.ArrayList;

/**
 * Created by Ahmed on 9/15/2017.
 */

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> images;


    public SliderAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String image = Config.Image_URL+images.get(position);

        LinearLayout linearLayout = new LinearLayout(context);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.height = ViewPager.LayoutParams.MATCH_PARENT;
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        linearLayout.setLayoutParams(params);


        ImageView imageView =
                new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(false);

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        linearLayout.addView(imageView);

        Glide.with(context)
                .load(image)
                .apply(new RequestOptions().fitCenter())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);

        container.addView(linearLayout);

        return linearLayout;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            container.removeView((View) object);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
