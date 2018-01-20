
package com.example.ahmed.octopusmart.Model.ServiceModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.ahmed.octopusmart.Util.LangUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ProductModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("supplier_id")
    @Expose
    private String supplierId;
    @SerializedName("prod_details_id")
    @Expose
    private Long prodDetailsId;
    @SerializedName("avilable")
    @Expose
    private String avilable;
    @SerializedName("price")
    @Expose
    private Long price;
    @SerializedName("discount")
    @Expose
    private Long discount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("rel")
    @Expose
    private Long rel;
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
    @SerializedName("supplier")
    @Expose
    private UserModel supplier;
    @SerializedName("more_data")
    @Expose
    private ArrayList<SubFilterModel> moreData = null;
    private ArrayList<String> images = new ArrayList<>();

    private Integer totalPrice;
    private Integer count;

    @SerializedName("user_rates")
    @Expose
    private UserRateModel userRates;

    @SerializedName("similar")
    @Expose
    private ArrayList<ProductModel> similar = new ArrayList<>();



    @SerializedName("my_rates")
    @Expose
    private UserRateModel myRate;

    @SerializedName("is_fav")
    @Expose
    private boolean isFav;

    public boolean isFav() {
        return isFav;
    }

    private final static long serialVersionUID = -3694115637770084395L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Long getProdDetailsId() {
        return prodDetailsId;
    }

    public void setProdDetailsId(Long prodDetailsId) {
        this.prodDetailsId = prodDetailsId;
    }

    public String getAvilable() {
        return avilable;
    }

    public void setAvilable(String avilable) {
        this.avilable = avilable;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRel() {
        return rel;
    }

    public void setRel(Long rel) {
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

    public void setCat(CatModel cat) {
        this.cat = cat;
    }

    public UserModel getSupplier() {
        return supplier;
    }

    public void setSupplier(UserModel supplier) {
        this.supplier = supplier;
    }

    public ArrayList<SubFilterModel> getMoreData() {
        return moreData;
    }

    public void setMoreData(ArrayList<SubFilterModel> moreData) {
        this.moreData = moreData;
    }

    public UserRateModel getUserRates() {
        return userRates;
    }

    public void setUserRates(UserRateModel userRates) {
        this.userRates = userRates;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("supplierId", supplierId).append("prodDetailsId", prodDetailsId).append("avilable", avilable).append("price", price).append("discount", discount).append("createdAt", createdAt).append("rel", rel).append("nameAr", nameAr).append("nameEn", nameEn).append("cat", cat).append("subCat", subCat).append("descriptionAr", descriptionAr).append("descriptionEn", descriptionEn).append("supplier", supplier).append("moreData", moreData).append("userRates", userRates).toString();
    }

    public String getName() {
        return (Objects.equals(LangUtils.getLanguage(), LangUtils.LANGUAGE_AR)) ? nameAr : nameEn;
    }

    public UserRateModel getMyRate() {
        return myRate;
    }

    public void setFav(boolean fav) {
        this.isFav = fav;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setMyRate(UserRateModel myRate) {
        this.myRate = myRate;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }


    public ArrayList<ProductModel> getSimilar() {
        return similar;
    }

    public void setSimilar(ArrayList<ProductModel> similar) {
        this.similar = similar;
    }
}