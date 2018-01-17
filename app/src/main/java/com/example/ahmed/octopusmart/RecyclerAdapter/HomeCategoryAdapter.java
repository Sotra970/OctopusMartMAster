package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private List<ProductModel> data = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
   private HomeAdapterListener listener;

    public HomeCategoryAdapter(List<ProductModel> mobileModelList, Context context , HomeAdapterListener listener) {
        inflater = LayoutInflater.from(context);
        this.data = mobileModelList;
        this.context = context;
        this. listener= listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_section_product_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final ProductModel current = data.get(position);



        Glide.with(context)
                .load(Config.Image_URL + current.getImages().get(0))
                .apply(new RequestOptions().fitCenter())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.mobileImg);


        boolean discount = current.getDiscount() != 0L;
        if(discount){
            holder.priceBefore.setVisibility(View.VISIBLE);
            long oldPrice = current.getPrice();
            long perc = current.getDiscount();

            long newPrice = (oldPrice * perc )/ 100;
            newPrice = oldPrice - newPrice ;

            holder.priceBefore.setText(oldPrice+" " +context.getString(R.string.le));
            holder.priceBefore.setPaintFlags(holder.priceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.price.setText(newPrice+" "+context.getString(R.string.le));

        }else {
            holder.price.setText(current.getPrice()+" "+context.getString(R.string.le));
            holder.priceBefore.setVisibility(View.GONE);

        }


        holder.product_rate.setRating((float) current.getUserRates().getRate());
        holder.saleText.setText(current.getDiscount()+"%");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onProductClicked(current, holder.mobileImg , position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.mobile_img)
        ImageView mobileImg;
        @BindView(R.id.sale_text)
        TextView saleText;
        @BindView(R.id.price_before)
        TextView priceBefore;
        @BindView(R.id.mobile_name)
        TextView mobileName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.product_rate)
        RatingBar product_rate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
