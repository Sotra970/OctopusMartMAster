
package com.example.ahmed.octopusmart.Model.ServiceModels.Favorite;

import java.io.Serializable;
import java.util.List;

import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserRateModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("supplier_id")
    @Expose
    private String supplierId;
    @SerializedName("prod_details_id")
    @Expose
    private Integer prodDetailsId;
    @SerializedName("avilable")
    @Expose
    private String avilable;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("rel")
    @Expose
    private Integer rel;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("cat")
    @Expose
    private CatModel cat;
    @SerializedName("sub_cat")
    @Expose
    private List<Object> subCat = null;
    @SerializedName("description_ar")
    @Expose
    private String descriptionAr;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("user_model")
    @Expose
    private UserModel userModel;
    @SerializedName("user_rates")
    @Expose
    private UserRateModel userRates;
    @SerializedName("is_fav")
    @Expose
    private Boolean isFav;
    private final static long serialVersionUID = 4066501256711758484L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getProdDetailsId() {
        return prodDetailsId;
    }

    public void setProdDetailsId(Integer prodDetailsId) {
        this.prodDetailsId = prodDetailsId;
    }

    public String getAvilable() {
        return avilable;
    }

    public void setAvilable(String avilable) {
        this.avilable = avilable;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRel() {
        return rel;
    }

    public void setRel(Integer rel) {
        this.rel = rel;
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

    public CatModel getCat() {
        return cat;
    }

    public void setCat(CatModel cat) {
        this.cat = cat;
    }

    public List<Object> getSubCat() {
        return subCat;
    }

    public void setSubCat(List<Object> subCat) {
        this.subCat = subCat;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserRateModel getUserRates() {
        return userRates;
    }

    public void setUserRates(UserRateModel userRates) {
        this.userRates = userRates;
    }

    public Boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(Boolean isFav) {
        this.isFav = isFav;
    }

}
