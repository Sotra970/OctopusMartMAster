package com.example.ahmed.octopusmart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.ahmed.octopusmart.Activity.Base.RequestActivity;
import com.example.ahmed.octopusmart.Activity.ProductDetailsActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeBase;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeCategoryProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeSliderProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeSortingProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.RecyclerAdapter.HomeAdapter;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.MenuCategoryAdapter;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.example.ahmed.octopusmart.Util.EndlessRecyclerViewScrollListener;
import com.example.ahmed.octopusmart.View.PagerSwitcher;
import com.example.ahmed.octopusmart.ViewPagerAdapter.HomeSliderAdapter;
import com.example.ahmed.octopusmart.holders.PagerHomeHolder;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.ahmed.octopusmart.Activity.Base.CacheActivity._productModel;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.fail;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.hide;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.show;

/**
 * Created by ahmed on 12/4/2017.
 */

public class HomeFragment extends BaseLoginFragment implements HomeAdapterListener {

    @BindView(R.id.home_list)
    RecyclerView home_list;
    HomeAdapter adapter ;
    ArrayList<HomeBase> homeBases = new ArrayList<>();
    View view;
    int current_page = -1 ;

    @BindView(R.id.home_scroll_view)
    NestedScrollView mScrollView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_home,container,false);
            ButterKnife.bind(this,view);


            adapter = new HomeAdapter(homeBases ,getActivity() , this) ;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setAutoMeasureEnabled(true);
            home_list.setLayoutManager(linearLayoutManager);
            home_list.setAdapter(adapter);
            home_list.setNestedScrollingEnabled(false);
//            home_list.setHasFixedSize(true);
            home_list.setItemAnimator(new DefaultItemAnimator());
            if (savedInstanceState ==null){

            }
            showLoading(show , null);




            getSlider(new RequestActivity.SuccessListener() {
                @Override
                public void onFinish() {

                    getCategories(new RequestActivity.SuccessListener() {
                        @Override
                        public void onFinish() {
                            getData(0);
                            loadMoreSetup();
                        }
                    });


                }
            });


        }
         return view;

    }

    private void loadMoreSetup() {
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged()
            {
                View view = (View)mScrollView.getChildAt(mScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (mScrollView.getHeight() + mScrollView
                        .getScrollY()));

                if (diff == 0) {
                    // your pagination code
                    int page  = current_page ;
                    getData(++page);
                }
            }
        });

    }


    void getData(final int page){
        Log.e("page" , page +"---" +current_page) ;
        if (page <current_page|| page > 3 ){
            adapter.showLoading(false);
            return;
        }

        Call<ResponseBody> call = Injector.Api().getHomeData(Appcontroler.getUserId());
        call.enqueue(new CallbackWithRetry<ResponseBody>(Injector.Retry_count, Injector.Retry_Time_Offset, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                adapter.showLoading(false);
                if (page ==0)
                showLoading(fail , new LoadingActionClick(){
                    @Override
                    public void OnClick() {
                        getData(page);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                showLoading(hide , null);

                if(response.isSuccessful()){
                    current_page=  page ;
                    try {


                        JSONArray jsonArray = new JSONArray(response.body().string());

                        if (jsonArray.length() == 0){
                            adapter.showLoading(false);
                        }
                        for ( int i=0 ; i<jsonArray.length() ; i++) {
                            try {
                                JSONObject current  = jsonArray.getJSONObject(i) ;
                                String tybe  = current.getString("display_model_tybe");
                                if (tybe.equals("slider")){
                                    HomeSliderProducts temp  = new Gson().fromJson(current.toString(), HomeSliderProducts.class) ;
                                    adapter.insert(temp);
                                }else if (tybe.equals("category")){
                                    HomeCategoryProducts temp  = new Gson().fromJson(current.toString(), HomeCategoryProducts.class) ;
                                    adapter.insert(temp);
                                }else if (tybe.equals("popular") || tybe.equals("new_arraivals") ){
                                    HomeSortingProducts temp  = new Gson().fromJson(current.toString(), HomeSortingProducts.class) ;
                                    adapter.insert(temp);
                                }
                            }catch (Exception e){
                                Log.e("parse_Ex_child" , e.toString());
                            }
                        }
                    }catch (Exception e){
                        Log.e("parse_Ex_boy" , e.toString());
                    }
                }

            }
        });
    }


    @BindView(R.id.menu_categories_list)
    RecyclerView categories_list ;


    @BindView(R.id.pager)
    public ViewPager pager ;

    @BindView(R.id.indicator)
    public CircleIndicator indicator ;




    void getSlider(final  RequestActivity.SuccessListener successListener){
        Call<HomeSliderProducts> call = Injector.Api().slider(Appcontroler.getUserId());
        call.enqueue(new CallbackWithRetry<HomeSliderProducts>(call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showLoading(fail , new LoadingActionClick(){
                    @Override
                    public void OnClick() {
                        getSlider(successListener);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<HomeSliderProducts> call, Response<HomeSliderProducts> response) {
                if (response.isSuccessful() && !response.body().getData().isEmpty()){

                    HomeSliderProducts products = response.body() ;
                    pager.setAdapter(new HomeSliderAdapter(getContext(),products.getData() , HomeFragment.this));
                    pager.setOffscreenPageLimit(products.getData().size());
                    indicator.setViewPager(pager);
                    // Auto start of viewpager
                    final PagerSwitcher switcher = new PagerSwitcher(getContext() ,  pager , products.getData().size());
                    switcher.startSwitching();
                    pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            switcher.setPage(position);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }
                successListener.onFinish();
            }
        });
    }

    void getCategories(final RequestActivity.SuccessListener successListener){
        getBaseActivity().getCategories(new RequestActivity.CategoriesListener() {
            @Override
            public void onFinish(ArrayList<CatModel> models) {

                Log.e("models" , models.size() +"");
                MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter(models, getActivity(), new MenuFragment.CategoryMenuListener() {
                    @Override
                    public void onCategoryClicked(CatModel catModel, View shared) {
                        onCategoryHeadarClicked(catModel ,shared , 0);
                    }

                    @Override
                    public void onAllCategoryClicked(View shared) {
                        getBaseActivity().startCategory(null  , getBaseActivity());
                    }
                },false);

                categories_list.setLayoutManager(new GridLayoutManager(getContext(),2));
                categories_list.setAdapter(menuCategoryAdapter);
                successListener.onFinish();
            }

            @Override
            public void onFailure() {
                showLoading(fail , new LoadingActionClick(){
                    @Override
                    public void OnClick() {
                       getCategories(successListener);
                    }
                });
            }
        });
    }


    @Override
    public void onProductClicked(ProductModel productModel, View shared, int postion) {
        if(productModel != null){
            Intent i = new Intent(getContext(), ProductDetailsActivity.class);
            _productModel = productModel ;
            getBaseActivity().startActivity(i , shared , getActivity()) ;
        }
    }

    @Override
    public void onSliderProductClicked(ProductModel productModel, View shared, int postion) {
        if(productModel != null){
            Intent i = new Intent(getContext(), ProductDetailsActivity.class);
            _productModel = productModel ;
            getBaseActivity().startActivity(i);
        }
    }

    @Override
    public void onCategoryHeadarClicked(CatModel catModel, View shared, int postion) {
        getBaseActivity().startCategory(catModel , shared , getActivity());
    }
}