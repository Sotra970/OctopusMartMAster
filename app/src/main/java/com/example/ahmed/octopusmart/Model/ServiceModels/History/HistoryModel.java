
package com.example.ahmed.octopusmart.Model.ServiceModels.History;

import java.io.Serializable;
import java.util.List;

import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("products")
    @Expose
    private List<ProductModel> products = null;
    @SerializedName("status")
    @Expose
    private Status status;
    private final static long serialVersionUID = 2317600270160503202L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
