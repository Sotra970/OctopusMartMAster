package com.example.ahmed.octopusmart.Model;

import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ahmed on 20/12/2017.
 */

public class CartProductItem implements Serializable{

    private ProductModel productModel;
    private int quantity;

    public long getTotalPrice(){
        long price = productModel.getPrice();
        return price * quantity;
    }

    public CartProductItem(ProductModel productModel, int quantity) {
        this.productModel = productModel;
        this.quantity = quantity;
    }

    public long getId(){
        try {
            return productModel.getId();
        }
        catch (Exception e){
            return -1;
        }
    }

    public CartProductItem(ProductModel productModel) {
        this.productModel = productModel;
        this.quantity = 1;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
