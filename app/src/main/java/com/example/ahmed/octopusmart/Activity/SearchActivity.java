package com.example.ahmed.octopusmart.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.InfiniteScroll.InfiniteScrollLoaderCallback;
import com.example.ahmed.octopusmart.InfiniteScroll.InfiniteScrollRecyclerView;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.SearchResultsAdapter;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.example.ahmed.octopusmart.ViewHolder.ProductVH;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmed Naeem on 1/19/2018.
 */

public class SearchActivity extends BaseActivity
        implements GenericItemClickCallback<ProductModel>,
        InfiniteScrollLoaderCallback {

    private final static int PAGE_SIZE = 10;

    @BindView(R.id.recycler)
    InfiniteScrollRecyclerView recyclerView;

    @BindView(R.id.search_view)
    SearchView searchView;

    private SearchResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        initRecycler();
        initSearchView();
    }

    private void loadSearchResults(final String query, final long start, final long end){
        if(query != null && !query.isEmpty()){

            long userId = Appcontroler.getUserId();

            Call<ArrayList<ProductModel>> call =
                    Injector.Api().searchProducts(userId, query, start, end);

            if(start == 0){
                // no data there, show normal loading
                showLoading(LoadingCases.show, null);
            }

            else{
                // infinte scroll loading
                loading.setVisibility(View.VISIBLE);
            }

            call.enqueue(
                    new CallbackWithRetry<ArrayList<ProductModel>>(
                            call,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    if(start != 0){
                                        loading.setVisibility(View.GONE);
                                    }

                                    showLoading(LoadingCases.fail,
                                            new LoadingActionClick() {
                                                @Override
                                                public void OnClick() {
                                                    loadSearchResults(query, start, end);
                                                }
                                            });
                                }
                            }
                    ) {
                        @Override
                        public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                            if(start == 0){
                                // no data there, normal loading
                                showLoading(LoadingCases.hide, null);
                            }

                            else{
                                // infinte scroll loading
                                loading.setVisibility(View.GONE);
                            }

                            if (response.isSuccessful()){

                                ArrayList<ProductModel> productModels = response.body();

                                if(adapter != null){

                                    adapter.addItemsToBottom(productModels);

                                    showNoDataLayout(productModels == null || productModels.isEmpty());

                                    if(productModels == null || productModels.isEmpty() ||
                                            productModels.size() < PAGE_SIZE)
                                    {
                                        endReached = true;
                                    }
                                }
                            }

                            else{
                                if(start == 0){
                                    showNoDataLayout(true);
                                }
                            }
                        }
                    }
            );
        }
    }

    private void initSearchView() {
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setBackground(
                ResourcesCompat.getDrawable(
                        getResources(),
                        R.drawable.search_bg,
                        null
                )
        );

        final EditText et = searchView.findViewById(R.id.search_src_text);
        et.setBackground(
                ResourcesCompat.getDrawable(
                        getResources(),
                        R.drawable.search_bg,
                        null
                )
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                endReached = false;
                currentQuery = newText;

                if(newText.isEmpty()){
                    if(adapter != null){
                        adapter.updateData(null);
                    }

                    showNoDataLayout(false);
                }

                else{
                    loadSearchResults(newText, 0, PAGE_SIZE);
                }

                return true;
            }
        });
    }

    private void initRecycler() {
        adapter = new SearchResultsAdapter(null, this, this);

        recyclerView.setScrollLoaderCallback(this);

        recyclerView.setGravity(InfiniteScrollRecyclerView.GRAVITY_BOTTOM);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(ProductModel productModel) {
        if(productModel != null){
            ArrayList<ProductModel> productModels = adapter.getItems();

            View shared = null;

            int id = -1;
            try {
                id = productModels.indexOf(productModel);
                shared = ((ProductVH) recyclerView.findViewHolderForLayoutPosition(id)).image;

            }
            catch (Exception e){

            }

            Intent i = new Intent(this, ProductDetailsActivity.class);
            _productModel = productModel ;

            if(shared != null){
                startActivity(i , shared , this) ;
            }

            else{
                startActivity(i);
            }
        }
    }

    @OnClick(R.id.activity_back)
    void exit(){
        finish();
    }

    @BindView(R.id.loading)
    View loading;

    private String currentQuery;
    private boolean endReached = false;

    @Override
    public void onLoadMore() {
        if(adapter != null && !endReached){
            loadSearchResults(currentQuery, adapter.getItemCount(), adapter.getItemCount() + PAGE_SIZE);
        }
    }
}


