package com.example.ahmed.octopusmart.Model.ServiceModels;

import com.example.ahmed.octopusmart.App.Appcontroler;

import java.util.ArrayList;

/**
 * Created by sotra on 12/21/2017.
 */

public class CategoryProductsRequestBody {
    ArrayList<Integer> sub_filter_ids;
     long cat_id ;
    int from;
    int to ;
    final  long user_id = Appcontroler.getInstance().getUserId();
    public CategoryProductsRequestBody(ArrayList<Integer> sub_filter_ids, long cat_id, int form, int to) {
        this.sub_filter_ids = sub_filter_ids;
        this.cat_id = cat_id;
        this.from = form;
        this.to = to;
    }

    public void setSub_filter_ids(ArrayList<Integer> sub_filter_ids) {
        this.sub_filter_ids = sub_filter_ids;
    }

    public void setCat_id(long cat_id) {
        this.cat_id = cat_id;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
