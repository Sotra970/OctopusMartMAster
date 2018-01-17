package com.example.ahmed.octopusmart.Request;

import com.example.ahmed.octopusmart.Model.ProductCreateOrderItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.http.Field;

/**
 * Created by ahmed on 23/12/2017.
 */

public class CreateOrderRequest {

    @SerializedName("user_id") long user_id;
    @SerializedName("payment_method_id") int payment_method_id;
    @SerializedName("total_price") long total_price;
    @SerializedName("products") ArrayList<ProductCreateOrderItem> items;

    public CreateOrderRequest(long user_id, int payment_method_id, long total_price, ArrayList<ProductCreateOrderItem> items) {
        this.user_id = user_id;
        this.payment_method_id = payment_method_id;
        this.total_price = total_price;
        this.items = items;
    }
}
