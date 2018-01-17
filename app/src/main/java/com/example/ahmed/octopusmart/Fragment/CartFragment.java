package com.example.ahmed.octopusmart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity;
import com.example.ahmed.octopusmart.Activity.ChooseAddressActivity;
import com.example.ahmed.octopusmart.Activity.ProductDetailsActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.App.MyPreferenceManager;
import com.example.ahmed.octopusmart.Decorator.DividerItemDecoration;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.Model.CartProductItem;
import com.example.ahmed.octopusmart.Model.ProductCreateOrderItem;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.RecyclerAdapter.CAdapter;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.ChangePriceCallback;
import com.example.ahmed.octopusmart.Request.CreateOrderRequest;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.example.ahmed.octopusmart.Util.Cart;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ahmed on 12/7/2017.
 */

public class CartFragment extends BaseLoginFragment
        implements GenericItemClickCallback<CartProductItem>
{


    @BindView(R.id.recycler_view_cart)
    RecyclerView cartRecyclerView;
    CAdapter cartAdapter;

    public static CartFragment getInstance() {
        return new CartFragment();
    }

    private boolean isAddressAvailable(){
        return false;
    }

    @OnClick(R.id.buy_btn)
    void createOrder(){
        if(isAddressAvailable()){


            Appcontroler.getInstance().getExecutorService()
                    .submit(
                            new Runnable() {
                                @Override
                                public void run() {
                                    boolean valid = false;
                                    ArrayList<CartProductItem> cartProductItems =
                                            Cart.getProducts(getContext());

                                    if(cartProductItems == null || cartProductItems.isEmpty()){
                                        Handler ui = new Handler(Looper.getMainLooper());
                                        ui.post(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        hideLoading();
                                                        getBaseActivity().showLongToast(
                                                                view, R.string.cart_is_empty
                                                        );
                                                    }
                                                }
                                        );
                                        return;
                                    }

                                    if(cartProductItems != null && !cartProductItems.isEmpty()){
                                        ArrayList<ProductCreateOrderItem> orderItems =
                                                new ArrayList<>();

                                        long total = 0;

                                        for(CartProductItem cartProductItem : cartProductItems){
                                            total += cartProductItem.getTotalPrice();
                                            orderItems.add(
                                                    new ProductCreateOrderItem(
                                                            cartProductItem.getId(),
                                                            cartProductItem.getQuantity(),
                                                            cartProductItem.getTotalPrice()
                                                    )
                                            );
                                        }

                                        if(!orderItems.isEmpty() && total != 0){
                                            long userId = getId();
                                            int payment = getPaymentMethod();
                                            if(userId != -1){
                                                valid  = true;
                                                CreateOrderRequest request =
                                                        new CreateOrderRequest(
                                                                userId, payment, total, orderItems
                                                        );
                                                Call<ResponseBody> call = Injector.Api().createOrder(
                                                        request
                                                );

                                                call.enqueue(
                                                        new CallbackWithRetry<ResponseBody>(
                                                                call,
                                                                new onRequestFailure() {
                                                                    @Override
                                                                    public void onFailure() {
                                                                        createOrder();
                                                                    }
                                                                }
                                                        ) {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                hideLoading();
                                                                if(response.isSuccessful()){
                                                                    getBaseActivity().showLongToast(
                                                                            view, R.string.order_created
                                                                    );

                                                                    if(cartAdapter != null){
                                                                        cartAdapter.updateData(new ArrayList<CartProductItem>());

                                                                        showNoDataLayout(true);
                                                                    }

                                                                    Appcontroler.getInstance().getExecutorService()
                                                                            .submit(
                                                                                    new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            Cart.clearCart(getContext());
                                                                                        }
                                                                                    }
                                                                            );
                                                                }
                                                                else{
                                                                    getBaseActivity().showLongToast(
                                                                            view, R.string.order_create_failed
                                                                    );
                                                                }
                                                            }
                                                        }
                                                );
                                            }
                                        }

                                        if(!valid){
                                            Handler ui = new Handler(Looper.getMainLooper());
                                            ui.post(
                                                    new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            hideLoading();
                                                            getBaseActivity().showLongToast(
                                                                    view, R.string.order_create_failed
                                                            );
                                                        }
                                                    }
                                            );
                                        }
                                    }
                                }
                            }
                    );
        }
        else{
            Intent i = new Intent(getContext(), ChooseAddressActivity.class);
            startActivity(i);
        }


    }


    @BindView(R.id.total)
    TextView totalPriceTextView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view ==null){
            view = inflater.inflate(R.layout.fragment_cart,container,false);

            ButterKnife.bind(this,view);

            cartAdapter = new CAdapter(null, getContext(), this);
            cartAdapter.setChangePriceCallback(new ChangePriceCallback() {
                @Override
                public void update() {
                    load();
                }
            });
            cartAdapter.setProductListener(new HomeAdapterListener(){
                @Override
                public void onProductClicked(ProductModel productModel, View shared, int postion) {
                  if(productModel != null){
                        getBaseActivity()._productModel = productModel;
                        getBaseActivity().startActivity(new Intent(getActivity() , ProductDetailsActivity.class),shared , getActivity());
                    }
                }

                @Override
                public void onSliderProductClicked(ProductModel productModel, View shared, int postion) {

                }

                @Override
                public void onCategoryHeadarClicked(CatModel catModel, View shared, int postion) {

                }
            });

            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


            cartRecyclerView.setLayoutManager(layoutManager);
            cartRecyclerView.setAdapter(cartAdapter);


        }

        return view;
    }

    @Override
    public void onItemClicked(CartProductItem item ) {

    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    private void load(){
        showLoading();
        Appcontroler.getInstance().getExecutorService()
                .submit(
                        new Runnable() {
                            @Override
                            public void run() {
                                final ArrayList<CartProductItem> cartProductItems = Cart.getProducts(getContext());

                                long total = 0;
                                for(CartProductItem cartProductItem : cartProductItems){
                                    total += cartProductItem.getTotalPrice();
                                }


                                Handler uiHandler = new Handler(Looper.getMainLooper());
                                final long finalTotal = total;
                                uiHandler.post(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                if(cartAdapter != null){
                                                    cartAdapter.updateData(cartProductItems);

                                                    if(finalTotal != 0){
                                                        totalPriceTextView.setText(String.valueOf(finalTotal));
                                                    }

                                                    showNoDataLayout(cartAdapter.isDataSetEmpty());
                                                }

                                                hideLoading();
                                            }
                                        }
                                );
                            }
                        }
                );
    }

    private void showLoading(){
        try {
           showLoading(LoadingDialogActivity.LoadingCases.show, null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void hideLoading(){
        try {
                    showLoading(LoadingDialogActivity.LoadingCases.hide, null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getPaymentMethod() {
        return 2;
    }
}

