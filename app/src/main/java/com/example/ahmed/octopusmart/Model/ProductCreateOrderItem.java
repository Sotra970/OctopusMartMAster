package com.example.ahmed.octopusmart.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 23/12/2017.
 */

public class ProductCreateOrderItem {

    @SerializedName("id")
    private long id;

    @SerializedName("count")
    private int count;

    @SerializedName("total_price")
    private long total_price;

    public ProductCreateOrderItem(long id, int count, long total_price) {
        this.id = id;
        this.count = count;
        this.total_price = total_price;
    }


}
