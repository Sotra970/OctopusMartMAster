package com.example.ahmed.octopusmart.Interfaces;

import android.view.View;

import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;

/**
 * Created by sotra on 12/9/2017.
 */

public interface HomeAdapterListener {
        void onProductClicked(ProductModel productModel , View shared  , int postion );
        void onSliderProductClicked(ProductModel productModel , View shared  , int postion );
        void onCategoryHeadarClicked(CatModel catModel , View shared  , int postion );

}
