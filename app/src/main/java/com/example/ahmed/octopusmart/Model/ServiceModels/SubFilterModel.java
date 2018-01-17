
package com.example.ahmed.octopusmart.Model.ServiceModels;

import java.io.Serializable;

import com.example.ahmed.octopusmart.Util.LangUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SubFilterModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("body_ar")
    @Expose
    private String bodyAr;
    @SerializedName("body_en")
    @Expose
    private String bodyEn;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("icon")
    @Expose
    private String icon;
    private final static long serialVersionUID = 4726510009798267432L;
    private int group , child;
    boolean isChecked;

    public String getBodyAr() {
        return bodyAr;
    }

    public void setBodyAr(String bodyAr) {
        this.bodyAr = bodyAr;
    }

    public String getBodyEn() {
        return bodyEn;
    }

    public void setBodyEn(String bodyEn) {
        this.bodyEn = bodyEn;
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
    public String getName(){
        return ( LangUtils.isAr()  ? getNameAr() : getNameEn());
    }
    public String getBody(){
        return ( LangUtils.isAr()  ? getBodyAr() : getBodyEn());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("bodyAr", bodyAr).append("bodyEn", bodyEn).append("nameAr", nameAr).append("nameEn", nameEn).append("icon", icon).toString();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }
}
