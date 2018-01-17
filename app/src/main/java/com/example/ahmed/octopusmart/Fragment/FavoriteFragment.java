package com.example.ahmed.octopusmart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Activity.ProductDetailsActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.ServiceModels.Favorite.FavoriteModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.RecyclerAdapter.FavoriteAdapter;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.fail;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.hide;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.show;

/**
 * Created by ahmed on 12/7/2017.
 */

public class FavoriteFragment extends BaseLoginFragment implements FavoriteAdapter.Listener {

    @BindView(R.id.recycler_view_favorite)
    RecyclerView favoriteRecyclerView;

    FavoriteAdapter favoriteAdapter;


    private ArrayList<ProductModel> productModelArrayList= new ArrayList<>();


    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favorite,container,false);

        ButterKnife.bind(this,view);
        favoriteAdapter = new FavoriteAdapter(productModelArrayList,getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        favoriteRecyclerView.setLayoutManager(layoutManager);
        favoriteRecyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.setListener(this);

        getData();

        return view;
    }


    void getData(){

       showLoading(show,null);

        Call<ArrayList<ProductModel>> call = Injector.Api().getFavoriteData(Appcontroler.getInstance().getUserId());

        call.enqueue(new CallbackWithRetry<ArrayList<ProductModel>>(Injector.Retry_count, Injector.Retry_Time_Offset, call, new onRequestFailure() {
            @Override
            public void onFailure() {

               showLoading(fail, new LoadingActionClick() {
                    @Override
                    public void OnClick() {
                        getData();
                    }
                });

            }
        }) {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {

                if(response.isSuccessful()){

                    if(response.body()!=null && !response.body().isEmpty()){
                        favoriteAdapter.update(response.body());
                        showNoDataLayout(false);
                    }
                    else
                        showNoDataLayout(true);
                }
                showLoading(hide,null);

            }
        });

    }

    void delete(final long id  , final int pos ){
       showLoading(show , null);
        Call<ResponseBody> call = Injector.Api().deleteFav(
                id , Appcontroler.getUserId()
        );
        call.enqueue(new CallbackWithRetry<ResponseBody>(call, new onRequestFailure() {
            @Override
            public void onFailure() {
               showLoading(fail, new LoadingActionClick() {
                    @Override
                    public void OnClick() {
                        delete(id,pos);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               showLoading(hide , null);
                if (response.isSuccessful()){
                    favoriteAdapter.remove(pos);
                }
            }
        });
    }
    @Override
    public void onFavoriteClicked(ProductModel productModel , View shared) {

        if(productModel != null){
            Intent i = new Intent(getContext(), ProductDetailsActivity.class);
            getBaseActivity()._productModel = productModel ;
            getBaseActivity().startActivity(i , shared , getActivity()) ;
        }

    }

    @Override
    public void onFavoriteRemoveClicked(ProductModel productModel, int pos) {
        delete(productModel.getId() , pos);
    }
}
