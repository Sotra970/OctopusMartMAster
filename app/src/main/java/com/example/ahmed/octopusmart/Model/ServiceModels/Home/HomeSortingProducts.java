package com.example.ahmed.octopusmart.Model.ServiceModels.Home;

import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 12/19/2017.
 */

public class HomeSortingProducts extends  HomeBase implements Serializable{
    ArrayList<ProductModel> data ;

    public ArrayList<ProductModel> getData() {
        return data;
    }

    public void setData(ArrayList<ProductModel> data) {
        this.data = data;
    }
}
