package com.example.ahmed.octopusmart.Model;

import java.io.Serializable;

/**
 * Created by ahmed on 12/9/2017.
 */

public class StoreCollectionModel implements Serializable {
    String storeImg;
    String storeNameText;
    String storeFeedBackText;
    String storeItemImg;
    String visitStoreText;

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public String getStoreNameText() {
        return storeNameText;
    }

    public void setStoreNameText(String storeNameText) {
        this.storeNameText = storeNameText;
    }

    public String getStoreFeedBackText() {
        return storeFeedBackText;
    }

    public void setStoreFeedBackText(String storeFeedBackText) {
        this.storeFeedBackText = storeFeedBackText;
    }

    public String getStoreItemImg() {
        return storeItemImg;
    }

    public void setStoreItemImg(String storeItemImg) {
        this.storeItemImg = storeItemImg;
    }

    public String getVisitStoreText() {
        return visitStoreText;
    }

    public void setVisitStoreText(String visitStoreText) {
        this.visitStoreText = visitStoreText;
    }
}
