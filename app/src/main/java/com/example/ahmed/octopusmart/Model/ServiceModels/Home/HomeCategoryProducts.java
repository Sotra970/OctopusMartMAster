package com.example.ahmed.octopusmart.Model.ServiceModels.Home;

import com.example.ahmed.octopusmart.Model.*;
import com.example.ahmed.octopusmart.Model.ServiceModels.*;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 12/19/2017.
 */

public class HomeCategoryProducts extends  HomeBase implements Serializable {
    CatModel category ;
    ArrayList<ProductModel> data ;

    public CatModel getCategory() {
        return category;
    }
    public ArrayList<ProductModel> getData() {
        return data;
    }
  }
