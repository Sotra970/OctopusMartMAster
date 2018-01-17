package com.example.ahmed.octopusmart.Model.ServiceModels.Home;

import java.io.Serializable;

/**
 * Created by sotra on 12/19/2017.
 */

public class HomeBase implements Serializable {
    String display_model_tybe ;

    public String getDisplay_model_tybe() {
        return display_model_tybe;
    }

    public void setDisplay_model_tybe(String display_model_tybe) {
        this.display_model_tybe = display_model_tybe;
    }

    public  boolean isCat(){
        return  display_model_tybe.equals("category");
    }


    public boolean isArrivals(){
        return  display_model_tybe.equals("new_arraivals");
    }


    public boolean isPopular(){
        return  display_model_tybe.equals("popular");
    }

    public boolean isSlider(){
        return  display_model_tybe.equals("slider");
    }

    public boolean isLoading(){
        return  display_model_tybe.equals("loading");
    }
}
