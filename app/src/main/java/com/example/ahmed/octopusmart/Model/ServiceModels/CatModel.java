
package com.example.ahmed.octopusmart.Model.ServiceModels;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.ahmed.octopusmart.Util.LangUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CatModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("filters")
    @Expose
    private ArrayList<FilterModel> filters;


    private final static long serialVersionUID = 4345802382775072851L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<FilterModel> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<FilterModel> filters) {
        this.filters = filters;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("nameAr", nameAr).append("nameEn", nameEn).append("icon", icon).toString();
    }

    public String getName(){
        return ( LangUtils.isAr()  ? getNameAr() : getNameEn());
    }


}
