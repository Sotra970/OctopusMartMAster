package com.example.ahmed.octopusmart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Activity.OrderStateDetailsActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.ServiceModels.Tracking.OrderTrackingDetailsModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.HistoryAdapter;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.fail;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.hide;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.show;

/**
 * Created by ahmed on 12/19/2017.
 */

public class OrderStateFragment extends BaseFragment implements HistoryAdapter.Listener {

    @BindView(R.id.recycler_view_history)
    RecyclerView historyRecyclerView;
    HistoryAdapter historyAdapter;

    private ArrayList<OrderTrackingDetailsModel> historyModelArrayList= new ArrayList<>();


    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null)
        { view = inflater.inflate(R.layout.fragment_history,container,false);

            ButterKnife.bind(this,view);

            historyAdapter = new HistoryAdapter(historyModelArrayList,getContext(),this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            historyRecyclerView.setLayoutManager(layoutManager);
            historyRecyclerView.setAdapter(historyAdapter);

            getData();

        }






        return view;

    }


    void getData(){
        getBaseActivity().showLoading(show , null);

        Call<ArrayList<OrderTrackingDetailsModel>> call ;

         call =  orderCase == OrderCases.tracking ? Injector.Api().getTrackingData(Appcontroler.getUserId())
          : Injector.Api().getHistoryData(Appcontroler.getUserId());

        call.enqueue(new CallbackWithRetry<ArrayList<OrderTrackingDetailsModel>>(Injector.Retry_count, Injector.Retry_Time_Offset, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                getBaseActivity().showLoading(fail, new LoadingActionClick() {
                    @Override
                    public void OnClick() {
                        getData();
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ArrayList<OrderTrackingDetailsModel>> call, Response<ArrayList<OrderTrackingDetailsModel>> response) {
                if(response.isSuccessful()){
                    getBaseActivity().showLoading(hide , null);
                    if (response.body()!=null && !response.body().isEmpty())
                    {
                        showNoDataLayout(false);
                        historyAdapter.update(response.body());

                    }
                    else showNoDataLayout(true);
                }

            }
        });

    }




    OrderCases orderCase ;
    public static OrderStateFragment getInstance(OrderCases order_case) {
        OrderStateFragment orderStateFragment  = new OrderStateFragment();
        orderStateFragment.orderCase = order_case ;
        return orderStateFragment;
    }

    @Override
    public void onOrderClicked(OrderTrackingDetailsModel model) {
        getBaseActivity()._orderModel = model ;
        startActivity(new Intent(getContext()  , OrderStateDetailsActivity.class));
    }


    public  enum OrderCases {
        history , tracking
    }


}
