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
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.holders.LoadingViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int item  = 0 , loading   = 1 ;

    public List<ProductModel> data = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    HomeAdapterListener listener;
    public CategoryProductsAdapter(ArrayList<ProductModel> mobileModelList, Context context , HomeAdapterListener listener) {
        inflater = LayoutInflater.from(context);
        this.data = mobileModelList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == item){
            View view = inflater.inflate(R.layout.category_product_item, parent, false);
            CategryProductViewHolder viewHolder = new CategryProductViewHolder(view);
            return viewHolder;
        }else {
            View view = inflater.inflate(R.layout.item_progress , parent , false);
            return  new LoadingViewHolder(view) ;
        }
    }

    @Override
    public int getItemViewType(int position) {
         if (position != 0 && position == getItemCount() - 1 )   return  loading ;  else  return  item  ;
    }

    private boolean showLoader = true;
    public void showLoading(boolean status) {
        showLoader = status;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == loading){
            final LoadingViewHolder loaderViewHolder = (LoadingViewHolder) viewHolder;
            if (showLoader) {
                loaderViewHolder.mProgressBar.setVisibility(View.VISIBLE);
            } else {
                loaderViewHolder.mProgressBar.setVisibility(View.GONE);
            }
            return;
        }else {

            final CategryProductViewHolder holder = (CategryProductViewHolder) viewHolder ;
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
                    if (listener!=null)
                        listener.onProductClicked(current , holder.mobileImg , position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data !=null && !data.isEmpty())
        return data.size()+1;
        else return 0 ;
    }

    public void update(ArrayList<ProductModel> data) {
        if (data !=null)
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        data = new ArrayList<>();
        notifyDataSetChanged();
    }

    class CategryProductViewHolder extends RecyclerView.ViewHolder {


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





        public CategryProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }




}
