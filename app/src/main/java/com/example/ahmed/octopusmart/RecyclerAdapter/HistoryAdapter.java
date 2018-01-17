package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
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
import com.example.ahmed.octopusmart.Fragment.OrderStateFragment;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.Tracking.OrderTrackingDetailsModel;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<OrderTrackingDetailsModel> OrderTrackingDetailsModelArrayList= new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    private Listener mListener;
    public HistoryAdapter(ArrayList<OrderTrackingDetailsModel> OrderTrackingDetailsModelArrayList, Context context , Listener listener ) {
        inflater = LayoutInflater.from(context);
        this.OrderTrackingDetailsModelArrayList = OrderTrackingDetailsModelArrayList;
        this.context = context;
        this.mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderTrackingDetailsModel current = OrderTrackingDetailsModelArrayList.get(position);

        Glide.with(context)
                .load(Config.Image_URL + current.getProducts().get(0))
                .apply(new RequestOptions().fitCenter())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.itemImg);

        holder.itemDate.setText(current.getCreatedAt());
        holder.itemOrderId.setText(current.getId()+ "");
        holder.itemPrice.setText(current.getTotalPrice()+ "");
        holder.sizeText.setText(" "+current.getProducts().size()+" ");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null)
                mListener.onOrderClicked(current);
            }

        });






    }

    @Override
    public int getItemCount() {
        return OrderTrackingDetailsModelArrayList.size();
    }


    public void update(ArrayList<OrderTrackingDetailsModel> body) {

        this.OrderTrackingDetailsModelArrayList = body;
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.products_size)
        TextView sizeText;
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_order_id)
        TextView itemOrderId;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.date_text)
        TextView itemDate;
        @BindView(R.id.history_linear)
        LinearLayout otherProducts;





        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }

    public interface Listener {
        void onOrderClicked(OrderTrackingDetailsModel model);
    }
}
