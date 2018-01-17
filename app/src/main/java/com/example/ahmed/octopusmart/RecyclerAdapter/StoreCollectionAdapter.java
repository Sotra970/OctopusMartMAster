package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Model.StoreCollectionModel;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StoreCollectionAdapter extends RecyclerView.Adapter<StoreCollectionAdapter.ViewHolder> {

    private List<StoreCollectionModel> storeCollectionModelArrayList= new ArrayList<>();
    LayoutInflater inflater;
    Context context;
   /* private Listener mListener;
*/
    public StoreCollectionAdapter(List<StoreCollectionModel> storeCollectionModelArrayList, Context context) {
        inflater = LayoutInflater.from(context);
        this.storeCollectionModelArrayList = storeCollectionModelArrayList;
        this.context = context;
    }

  /*  public void setListener(Listener listener)
    {
        mListener = listener;
    }
*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_store_collection, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoreCollectionModel current = storeCollectionModelArrayList.get(position);

       /* Glide.with(context)
                .load(Config.img_url + shopsModelsList.get(position).getShopPictureLink())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.shopPicturelink);
*/
        /*holder.shopName.setText(current.getShopName());*/

    }

    @Override
    public int getItemCount() {
        return storeCollectionModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.store_img)
        ImageView storeImg;
        @BindView(R.id.store_name_text)
        TextView storeNameText;
        @BindView(R.id.store_item_img)
        ImageView storeItemImg;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        MobileModel mobileHomeModel = mobileModelList.get(getAdapterPosition());
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
