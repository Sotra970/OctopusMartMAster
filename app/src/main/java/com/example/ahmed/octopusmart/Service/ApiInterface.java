package com.example.ahmed.octopusmart.Service;




import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.CategoryProductsRequestBody;
import com.example.ahmed.octopusmart.Model.ServiceModels.Favorite.FavoriteModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeCategoryProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeSliderProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.Tracking.OrderTrackingDetailsModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.example.ahmed.octopusmart.Model.UserReviewItem;
import com.example.ahmed.octopusmart.Request.CreateOrderRequest;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ahmed on 8/29/2017.
 */

public interface ApiInterface {

    @GET("home")
    Call<ResponseBody> getHomeData(@Query("user_id")  long user_id);

    @POST("category/products")
    Call<HomeCategoryProducts> getCategoryProducts(
          @Body CategoryProductsRequestBody categoryProductsRequestBody
     );



    @GET("users/{id}/history")
    Call<ArrayList<OrderTrackingDetailsModel>> getHistoryData(
            @Path("id") long id
    );

    @GET ("users/{id}/traking")
    Call<ArrayList<OrderTrackingDetailsModel>> getTrackingData(
            @Path("id") long id
    );




    @GET ("users/{id}/favs")
    Call<ArrayList<ProductModel>> getFavoriteData(
            @Path("id") long id
    );



    @FormUrlEncoded
    @POST("login")
    Call<UserModel> login(
         @Field("email") String email ,
         @Field("password") String password ,
         @Field("token") String token
    );

    @POST("regitser")
    Call<UserModel> register(
          @Body  UserModel userModel
    );

    @GET("categories")
    Call<ArrayList<CatModel>> getCategories();


    @GET("products/{product_id}/comments")
    Call<ArrayList<UserReviewItem>> getComments(
            @Path("product_id") long productId
    );

    @FormUrlEncoded
    @POST("product/comments/add")
    Call<ResponseBody> addComment(
            @Field("user_id") long userId,
            @Field("product_id") long productId,
            @Field("comment") String comment
    );

    @FormUrlEncoded
    @POST("product/fav/add")
    Call<ResponseBody> addFav(
            @Field("user_id") long user_id,
            @Field("product_id") long productId
    );

    @FormUrlEncoded
    @POST("product/fav/remove")
    Call<ResponseBody> removeFav(
            @Field("user_id") long user_id,
            @Field("product_id") long product_id
    );

    @FormUrlEncoded
    @POST("product/rate/add")
    Call<ResponseBody> addRate(
            @Field("user_id") long userId,
            @Field("product_id") long productId,
            @Field("rate") double rate
    );

    @POST("create/order")
    Call<ResponseBody> createOrder(
            @Body CreateOrderRequest createOrderRequest
    );




    @FormUrlEncoded
    @POST("user/password")
    Call<ResponseBody> changePassword(
            @Field("user_id")long userId,
            @Field("oldPassword")String oldP,
            @Field("newPassword")String newP
    );

    @FormUrlEncoded
    @POST("user/phone")
    Call<ResponseBody> changePhone(
            @Field("user_id")long userId,
            @Field("phone")String newP,
            @Field("password")String pass
    );

    @FormUrlEncoded
    @POST("user/email")
    Call<ResponseBody> changeEmail(
            @Field("user_id")long userId,
            @Field("email")String newE,
            @Field("password")String pass
    );

    @FormUrlEncoded
    @POST("product/fav/remove")
    Call<ResponseBody> changeAddress(
            long userId,
            String address
    );


    @FormUrlEncoded
    @POST("product/fav/remove")
    Call<ResponseBody> deleteFav(
            @Field("product_id") long id,
            @Field("user_id") long userId);


    @GET("slider")
    Call<HomeSliderProducts> slider(
            @Query("user_id") long userId
    );

}
