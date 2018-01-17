package com.example.ahmed.octopusmart.Model.ServiceModels.Home;

import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 12/19/2017.
 */

public class HomeSliderProducts  extends  HomeBase implements  Serializable {
    ArrayList<SliderModel> data ;

    public ArrayList<SliderModel> getData() {
        return data;
    }

    public  class  SliderModel implements Serializable {
        String image  ;
        ProductModel product ;

        public String getImage() {
            return image;
        }

        public ProductModel getProduct() {
            return product;
        }
    }


}
