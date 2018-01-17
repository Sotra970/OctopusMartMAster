package com.example.ahmed.octopusmart.Interfaces;

import com.example.ahmed.octopusmart.Model.ServiceModels.FilterModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;

/**
 * Created by sotra on 4/6/2017.
 */
public interface FilterListener {
   public void itemClicked(FilterModel filter);
   public void itemChildClicked(SubFilterModel subFilterModel, boolean checked);


}
