package com.example.ahmed.octopusmart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Activity.ProductDetailsActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
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


    public void onTabSelected() {
        Log.e("fav" , "onTabSelected") ;
        getData();
    }

    void remove_fav( long id ){
        int pos = -1 ;
        for(ProductModel productModel : favoriteAdapter.productModelArrayList){
            pos++ ;
            if (productModel.getId() == id){
                break;
            }
        }
        if (pos != -1 )
        favoriteAdapter.remove(pos);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("favFragment" , "onActivityResult " + requestCode  +"  " + resultCode +" data " + data);
        if (requestCode == 404 && data !=null){
            ProductModel productModel = (ProductModel) data.getExtras().getSerializable("product");
            if (!productModel.isFav()){
                remove_fav( productModel.getId());
            }
        }

        if (requestCode == BaseActivity.fav_login_code  && resultCode==200){
            getData();
        }

    }



    void getData(){


        if (!Appcontroler.isUserSigned()){
            getBaseActivity().startLogin();
            return;
        }


       showLoading(show,null);
        favoriteAdapter.update(new ArrayList<ProductModel>());

        Call<ArrayList<ProductModel>> call = Injector.Api().getFavoriteData(Appcontroler.getUserId());

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
    public void onFavoriteClicked(ProductModel productModel , View sharedView) {

        if(productModel != null){
            Intent i = new Intent(getContext(), ProductDetailsActivity.class);
            getBaseActivity()._productModel = productModel ;
            ActivityOptionsCompat options ;
            if (sharedView!=null)
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), sharedView, getString(R.string.sharedView));
            else
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity());

            ActivityCompat.startActivityForResult(getActivity(), i , 404, options.toBundle());
        }

    }

    @Override
    public void onFavoriteRemoveClicked(ProductModel productModel, int pos) {
        delete(productModel.getId() , pos);
    }
}
