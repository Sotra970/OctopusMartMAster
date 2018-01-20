package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.Model.CartProductItem;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.Util.Cart;
import com.example.ahmed.octopusmart.holders.CartItemViewHolder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ahmed on 20/12/2017.
 */

public class CAdapter extends GenericAdapter<CartProductItem> {

    public CAdapter(ArrayList<CartProductItem> items,
                    Context context,
                    GenericItemClickCallback<CartProductItem> adapterItemClickCallbacks )
    {
        super(items, context, adapterItemClickCallbacks);
    }

    ChangePriceCallback changePriceCallback ;


    public void setChangePriceCallback(ChangePriceCallback changePriceCallback) {
        this.changePriceCallback = changePriceCallback;
    }
    HomeAdapterListener productListener ;

    public void setProductListener(HomeAdapterListener productListener) {
        this.productListener = productListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final CartProductItem cartProductItem = getItem(position);
        if(cartProductItem != null){
            final ProductModel productModel = cartProductItem.getProductModel();

            if(productModel != null){
                final CartItemViewHolder cartItemViewHolder = (CartItemViewHolder) holder;

                cartItemViewHolder.name.setText(productModel.getName());

                updatePrice(cartItemViewHolder.price, cartItemViewHolder.count, cartProductItem);

                cartItemViewHolder.delete
                        .setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    Appcontroler.getExecutorService()
                                                .submit(
                                                        new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Cart.removeProduct(productModel.getId(), getContext());
                                                                changePriceCallback.update();
                                                            }
                                                        }
                                                );

                                        removeItems(Collections.singletonList(cartProductItem));


                                    }
                                }
                        );

                cartItemViewHolder.add.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int quan = cartProductItem.getQuantity();
                                cartProductItem.setQuantity(++quan);
                                updatePrice(cartItemViewHolder.price, cartItemViewHolder.count, cartProductItem);
                            }
                        }
                );

                cartItemViewHolder.subtract.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int quan = cartProductItem.getQuantity();
                                if(quan > 1){
                                    cartProductItem.setQuantity(--quan);
                                    updatePrice(cartItemViewHolder.price, cartItemViewHolder.count, cartProductItem);
                                }
                            }
                        }
                );

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productListener.onProductClicked(cartProductItem.getProductModel() , cartItemViewHolder.imageView , 0);
                    }
                });

            }
        }
    }

    private void updatePrice(TextView textView, TextView count, final CartProductItem cartProductItem){
        long price =  -1;
        try {
            price = cartProductItem.getProductModel().getPrice();
        }
        catch (Exception e){}

        if(price != -1){
            int quan = cartProductItem.getQuantity();
            long totalPrice = quan * price;
            count.setText(String.valueOf(quan));
            textView.setText(String.valueOf(totalPrice));
        }

        Appcontroler.getExecutorService()
                .submit(
                        new Runnable() {
                            @Override
                            public void run() {
                                Cart.updateItem(cartProductItem, getContext());
                                changePriceCallback.update();
                            }
                        }
                );
    }
}
