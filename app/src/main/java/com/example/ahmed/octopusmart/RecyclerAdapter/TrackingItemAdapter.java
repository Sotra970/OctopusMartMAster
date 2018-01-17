package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.Tracking.OrderTrackingDetailsModel;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrackingItemAdapter extends RecyclerView.Adapter<TrackingItemAdapter.ViewHolder> {

    private List<ProductModel> trackingModelArrayList= new ArrayList<>();
    LayoutInflater inflater;
    Context context;
   /* private Listener mListener;
*/
    public TrackingItemAdapter(List<ProductModel> trackingModelArrayList, Context context) {
        inflater = LayoutInflater.from(context);
        this.trackingModelArrayList = trackingModelArrayList;
        this.context = context;
    }

  /*  public void setListener(Listener listener)
    {
        mListener = listener;
    }
*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_tracking, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductModel current = trackingModelArrayList.get(position);

       /* Glide.with(context)
                .load(Config.img_url + shopsModelsList.get(position).getShopPictureLink())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.shopPicturelink);
*/


        Glide.with(context)
                .load(Config.Image_URL + current.getImages())
                .apply(new RequestOptions().fitCenter())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.trackingItemImg);

       holder.trackingItemName.setText(current.getNameAr());
       holder.trackingItemPrice.setText(current.getPrice()+"");
       holder.countText.setText(current.getCount() + "");


    }

    @Override
    public int getItemCount() {
        return trackingModelArrayList.size();
    }

    public void update(ArrayList<ProductModel> body) {
        this.trackingModelArrayList = body;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tracking_item_img)
        ImageView trackingItemImg;
        @BindView(R.id.tracking_item_name)
        TextView trackingItemName;
        @BindView(R.id.tracking_item_price)
        TextView trackingItemPrice;
        @BindView(R.id.count_text)
        TextView countText;





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

    /*public interface Listener {
        void onShopClicked(ShopsModel shopsModel);
    }*/
}
