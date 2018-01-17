
package com.example.ahmed.octopusmart.Model.ServiceModels;

import java.io.Serializable;
import java.util.List;

import com.example.ahmed.octopusmart.Util.LangUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class FilterModel implements Serializable
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
    @SerializedName("sub_filters")
    @Expose
    private List<SubFilterModel> subFilters = null;
    private final static long serialVersionUID = 7321864953613297664L;

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

    public List<SubFilterModel> getSubFilters() {
        return subFilters;
    }

    public void setSubFilters(List<SubFilterModel> subFilters) {
        this.subFilters = subFilters;
    }


    public String getName(){
        return ( LangUtils.isAr()  ? getNameAr() : getNameEn());
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("nameAr", nameAr).append("nameEn", nameEn).append("icon", icon).append("subFilters", subFilters).toString();
    }

}
