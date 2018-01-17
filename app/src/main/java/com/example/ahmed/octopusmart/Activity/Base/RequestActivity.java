package com.example.ahmed.octopusmart.Activity.Base;

import android.os.Bundle;


import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class RequestActivity extends UplodaImagesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public  void getCategories(final CategoriesListener getCategoriesListener){

        Call<ArrayList<CatModel>> catModelCall = Injector.Api().getCategories();

        catModelCall.enqueue(new CallbackWithRetry<ArrayList<CatModel>>(catModelCall, new onRequestFailure() {
            @Override
            public void onFailure() {
                getCategoriesListener.onFailure();
            }
        }) {
            @Override
            public void onResponse(Call<ArrayList<CatModel>> call, Response<ArrayList<CatModel>> response) {
                if (response.isSuccessful())
                getCategoriesListener.onFinish(response.body());
            }
        });

    }
    public  interface CategoriesListener {
        void onFinish(ArrayList<CatModel> models);

        void onFailure();
    }

    public  interface SuccessListener {
        void onFinish();
    }

}
