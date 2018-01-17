package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Fragment.MenuFragment;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.holders.MenuCategoryHolder;

import java.util.ArrayList;
import java.util.List;


public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryHolder> {

    int item=0 , all=1 ;
    private List<CatModel> data = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
   private MenuFragment.CategoryMenuListener listener;
   boolean circle_category_layout = true ;

    public MenuCategoryAdapter(List<CatModel> mobileModelList, Context context , MenuFragment.CategoryMenuListener listener) {
        inflater = LayoutInflater.from(context);
        this.data = mobileModelList;
        this.context = context;
        this. listener= listener;
    }

    public MenuCategoryAdapter(List<CatModel> mobileModelList, Context context , MenuFragment.CategoryMenuListener listener, boolean circle_category_layout) {
        inflater = LayoutInflater.from(context);
        this.data = mobileModelList;
        this.context = context;
        this. listener= listener;
        this.circle_category_layout = circle_category_layout;
    }

    @Override
    public MenuCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        if (viewType == item){
            int layout_id = circle_category_layout ==true ? R.layout.circle_category_layout : R.layout.linear_category_layout;
            View view = inflater.inflate(layout_id, parent, false);
            MenuCategoryHolder viewHolder = new MenuCategoryHolder(view);
            return viewHolder;
        }else {
            View view = inflater.inflate(R.layout.men_category_all_item, parent, false);
            MenuCategoryHolder viewHolder = new MenuCategoryHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final MenuCategoryHolder holder, final int position) {
        if (getItemViewType(position) == item){
            final CatModel current = data.get(position);

            Glide.with(context)
                    .load(Config.Image_URL + current.getIcon())
                    .apply(new RequestOptions().fitCenter())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(holder.categories_img);

            holder.category_title.setText(current.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onCategoryClicked(current, holder.categories_img);
                    }
                }
            });
        }else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onAllCategoryClicked (holder.categories_img);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() < 9 ? data.size() : 9;
    }

    @Override
    public int getItemViewType(int position) {
        if ( data.size() < 9){
            return  item ;
        }else {
            if (position!=8){
                return item ;
            }else return  all ;
        }
    }
}
