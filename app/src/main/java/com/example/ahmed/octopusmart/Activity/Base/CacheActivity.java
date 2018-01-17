package com.example.ahmed.octopusmart.Activity.Base;

import android.os.Bundle;

import com.example.ahmed.octopusmart.Fragment.OrderStateFragment;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.Tracking.OrderTrackingDetailsModel;

public abstract class CacheActivity extends RequestActivity {

    public static CatModel _catModel ;
    public static ProductModel _productModel ;
    public static OrderTrackingDetailsModel _orderModel ;
    public  static  OrderStateFragment.OrderCases _orderCase ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState !=null){
            _catModel = (CatModel)savedInstanceState.getSerializable("_catModel");
            _productModel = (ProductModel) savedInstanceState.getSerializable("_productModel");
            _orderModel = (OrderTrackingDetailsModel)savedInstanceState.getSerializable("_orderModel");
            _orderCase = (OrderStateFragment.OrderCases )savedInstanceState.getSerializable("_orderCase");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         outState.putSerializable("_catModel", _catModel);
         outState.putSerializable("_productModel", _productModel);
         outState.putSerializable("_orderModel", _orderModel);
         outState.putSerializable("_orderCase", _orderCase);
    }


}
