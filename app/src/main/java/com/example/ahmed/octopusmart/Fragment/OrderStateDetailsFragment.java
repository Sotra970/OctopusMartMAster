package com.example.ahmed.octopusmart.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Model.ServiceModels.Tracking.OrderTrackingDetailsModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.TrackingItemAdapter;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ahmed on 12/19/2017.
 */

public class OrderStateDetailsFragment extends BaseFragment {

    @BindView(R.id.recycler_view_tracking)
    RecyclerView trackingRecyclerView;

    @BindView(R.id.price_text)
    TextView totalPrice;

    @BindView(R.id.making_order_img)
    ImageView makingOrderImg;
    @BindView(R.id.making_order_view)
    View makingOrderView;
    @BindView(R.id.making_order_text)
    TextView makingOrderText;
    @BindView(R.id.delivery_img)
    ImageView deliveryImg;
    @BindView(R.id.delivery_text)
    TextView deliveryText;
    @BindView(R.id.delivery_done_img)
    ImageView deliveryDoneImg;
    @BindView(R.id.delivery_done_view)
    View deliveryDoneView;
    @BindView(R.id.delivery_done_text)
    TextView deliveryDoneText;

    TrackingItemAdapter trackingItemAdapter;





    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null)
        {
            view = inflater.inflate(R.layout.fragment_tracking,container,false);

            ButterKnife.bind(this,view);

            trackingItemAdapter= new TrackingItemAdapter(getBaseActivity()._orderModel.getProducts(),getContext());

            trackingRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            trackingRecyclerView.setAdapter(trackingItemAdapter);

            totalPrice.setText(getBaseActivity()._orderModel.getTotalPrice() + "" + getString(R.string.le));

            switch (getBaseActivity()._orderModel.getStatus().getStatus())
            {
                case 1:
                    stage2();
                    break;
                case 2:
                    stage2();
                    stage3();
                    break;

            }
        }




        return view;

    }


    void stage2()
    {
        deliveryImg.setAlpha(1f);
        deliveryDoneView.setAlpha(1);

    }

    void stage3()
    {

        deliveryDoneImg.setAlpha(1f);


    }







    public static OrderStateDetailsFragment getInstance() {
        OrderStateDetailsFragment orderStateDetailsFragment = new OrderStateDetailsFragment() ;
        return   orderStateDetailsFragment ;
    }
}
