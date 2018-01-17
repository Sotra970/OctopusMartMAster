package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Model.ServiceModels.Favorite.FavoriteModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<ProductModel> productModelArrayList= new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    private Listener mListener;
    public FavoriteAdapter(List<ProductModel> productModelArrayList, Context context) {
        inflater = LayoutInflater.from(context);
        this.productModelArrayList = productModelArrayList;
        this.context = context;
    }

    public void setListener(Listener listener)
    {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_favorite, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ProductModel current = productModelArrayList.get(position);


        Glide.with(context)
                .load(Config.Image_URL + current.getImages().get(0))
                .apply(new RequestOptions().fitCenter())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.favoriteItemImg);

        holder.favoriteItemDate.setText(current.getCreatedAt());
        holder.favoriteItemName.setText(current.getNameAr());

        boolean discount = current.getDiscount() != 0L;
        if(discount){
            holder.discountLayout.setVisibility(View.VISIBLE);

            long oldPrice = current.getPrice();
            long perc = current.getDiscount();

            long newPrice = (oldPrice * perc )/ 100;
            newPrice = oldPrice - newPrice ;

            holder.favoriteItemPriceBefore.setText(oldPrice + " " +context.getString(R.string.le));
            holder.favoriteItemPriceBefore.setPaintFlags(holder.favoriteItemPriceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.favoriteItemPrice.setText(newPrice +" ");

        }else {
            holder.favoriteItemPrice.setText(current.getPrice() +"");
            holder.discountLayout.setVisibility(View.GONE);

        }


        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null)
                    mListener.onFavoriteRemoveClicked(current , position);
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null)
                    mListener.onFavoriteClicked(current,holder.favoriteItemImg);
            }

        });

    }

    @Override
    public int getItemCount() {
        return productModelArrayList.size();
    }

    public void update(ArrayList<ProductModel> body) {

        this.productModelArrayList = body;
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        productModelArrayList.remove(pos);
        notifyItemRemoved(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.discount_layout)
        LinearLayout discountLayout;
        @BindView(R.id.favorite_item_img)
        ImageView favoriteItemImg;
        @BindView(R.id.favorite_item_name)
        TextView favoriteItemName;
        @BindView(R.id.favorite_item_price)
        TextView favoriteItemPrice;
        @BindView(R.id.favorite_date_text)
        TextView favoriteItemDate;
        @BindView(R.id.delete_img)
        View deleteImg;
        @BindView(R.id.favorite_item_price_before)
        TextView favoriteItemPriceBefore;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        TempProductModel mobileHomeModel = mobileModelList.get(getAdapterPosition());
                        mListener.onShopClicked(shopsModel);

                    }
                }
            });*/


        }
    }

    public interface Listener {
        void onFavoriteClicked(ProductModel productModel, View shared);
        void onFavoriteRemoveClicked(ProductModel productModel,int  pos);
    }
}
