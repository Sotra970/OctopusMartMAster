package com.example.ahmed.octopusmart.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Fragment.FilterFragment;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.Interfaces.FilterListener;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.CategoryProductsRequestBody;
import com.example.ahmed.octopusmart.Model.ServiceModels.FilterModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeCategoryProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.RecyclerAdapter.CategoryProductsAdapter;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.example.ahmed.octopusmart.Util.EndlessRecyclerViewScrollListener;
import com.example.ahmed.octopusmart.Utils.transition.AnimUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.fail;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.hide;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.show;

/**
 * Created by ahmed on 12/6/2017.
 */

public class CategoryActivity extends BaseActivity
        implements FilterListener , HomeAdapterListener {

    @BindView(R.id.cats_recycler_view)
    RecyclerView catsRecyclerView;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_toolbar_title)
    TextView titleText;

    private CategoryProductsAdapter adapter;

    private ArrayList<ProductModel> productModels = new ArrayList<>();

    ArrayList<Integer> filterModelsIDs = new ArrayList<>();
    ArrayList<SubFilterModel> filterModels = new ArrayList<>();
    @BindView(R.id.bar_category_item)
        ImageView bar_category_item ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        toolbar_action_setup();
        menu_bottom_sheet_setub();
        showLoading(show,null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("entertranstion" , getWindow().getEnterTransition() +"") ;
            getWindow().getEnterTransition().addListener(new AnimUtils.TransitionListenerAdapter(){
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    setup();
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    super.onTransitionCancel(transition);
                    setup();
                }
            });
        }else {
            setup();
        }
    }

    void setup(){
        adapter = new CategoryProductsAdapter(productModels,getApplicationContext(),this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2) ;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == adapter.item ? 1 : 2 ;
            }
        });
        catsRecyclerView.setLayoutManager(gridLayoutManager);
        catsRecyclerView.setAdapter(adapter);
        catsRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getData(page) ;
            }
        });

        refresh(false);
    }


    void toolbar_action_setup() {
        titleText.setText(_catModel.getName());
        setSupportActionBar(toolbar);
        Glide.with(this)
                .load(Config.Image_URL+_catModel.getIcon())
                .into(bar_category_item);
    }

    @OnClick(R.id.back_btt)
    public void back_btt() {
        onBackPressed();
    }





    BottomSheetBehavior bottomSheetBehavior ;
    FilterFragment menuFragment;
    void menu_bottom_sheet_setub(){
         menuFragment = FilterFragment.getInstance(_catModel.getFilters(),new FilterFragmentListener(){
             @Override
             public void switch_sheet() {
                 switchBottomSheet();
             }

             @Override
             public void itemRemoved(SubFilterModel subFilterModel, boolean checked) {
                 checkFilter(subFilterModel, checked);
             }
         },this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.filter_fragment, menuFragment);
        fragmentTransaction.commit() ;
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.filter_fragment));

    }

    void switchBottomSheet(){
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onProductClicked(ProductModel productModel, View shared, int postion) {
        if(productModel != null){
            Intent i = new Intent(this, ProductDetailsActivity.class);
            _productModel = productModel ;
            startActivity(i , shared , this) ;
        }
    }

    @Override
    public void onSliderProductClicked(ProductModel productModel, View shared, int postion) {

    }

    @Override
    public void onCategoryHeadarClicked(CatModel catModel, View shared, int postion) {

    }

    public  interface FilterFragmentListener{
        void switch_sheet();
        void itemRemoved(SubFilterModel subFilterModel, boolean checked);
    }

    @Override
    public void itemClicked(FilterModel filter) {

    }

    @Override
    public void itemChildClicked(SubFilterModel subFilterModel, boolean checked) {
        Log.e("categoryAc" ,"itemChildClicked" ) ;
        switchBottomSheet();
        checkFilter(subFilterModel , checked) ;
            refresh(true);
    }
    void refresh(boolean reload){
        if (reload)
        showLoading(show,null);
        productModels = new ArrayList<>() ;
        adapter.clear();
        current_page = -1 ;
        Log.e("category_ac" , productModels.size() +"") ;
        getData(0);
    }

    private void checkFilter(SubFilterModel subFilterModel, boolean checked) {
        if (checked){
            filterModels.add(subFilterModel) ;
            filterModelsIDs.add(subFilterModel.getId()) ;
        }else {
            int pos = filterModelsIDs.indexOf(new Integer(subFilterModel.getId())) ;
            filterModelsIDs.remove(pos) ;
            filterModels.remove(pos);

        }
    }

    int current_page = -1 ;

    void getData(final int page){
        Log.e("page" , page +"---" +current_page) ;
        if (page <current_page ){
            adapter.showLoading(false);
            return;
        }

        int from = (page*20) ;
        int to  = from+20 ;

        Call<HomeCategoryProducts> call = Injector.Api().getCategoryProducts(
              new CategoryProductsRequestBody(  filterModelsIDs, _catModel.getId() , from ,to)
        );
        call.enqueue(new CallbackWithRetry<HomeCategoryProducts>(Injector.Retry_count, Injector.Retry_Time_Offset, call, new onRequestFailure() {
            @Override
            public void onFailure() {
                adapter.showLoading(false);
                    showLoading(fail , new LoadingActionClick(){
                        @Override
                        public void OnClick() {
                            getData(page);
                        }
                    });
            }
        }) {
            @Override
            public void onResponse(Call<HomeCategoryProducts> call, Response<HomeCategoryProducts> response) {

                showLoading(hide , null);

                if(response.isSuccessful()){

                    current_page=  page ;
                   adapter.update(response.body().getData());
                    if (response.body().getData()==null  )
                    {
                        Log.e("category_ac" , "res_size" +  "0") ;
                        Log.e("category_ac" , productModels.size() +"") ;
                        adapter.showLoading(false);
                        if (adapter.data.isEmpty()) showNoDataLayout(true);

                    }else {
                        Log.e("category_ac" , "res_size" +response.body().getData().size() +"") ;
                        if (productModels.isEmpty()) showNoDataLayout(false);

                    }


                }

            }
        });
    }



}
