package com.example.ahmed.octopusmart.Activity;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.CartProductItem;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserRateModel;
import com.example.ahmed.octopusmart.Model.UserReviewItem;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.ProductSpecsAdapter;
import com.example.ahmed.octopusmart.RecyclerAdapter.SliderAdapter;
import com.example.ahmed.octopusmart.RecyclerAdapter.UserReviewAdapter;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.example.ahmed.octopusmart.Util.Cart;
import com.rd.PageIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.ahmed.octopusmart.App.Appcontroler.getUserId;

/**
 * Created by ahmed on 19/12/2017.
 */

public class ProductDetailsActivity extends BaseActivity {



    @BindView(R.id.main_container)
    View theView;

    private final static int MAX_REVIEWS = 4;

    @OnClick(R.id.back_btt)
    void onBack(){
        onBackPressed();
    }


    @BindView(R.id.collabse_toolbar_child)
    View collabse_toolbar_child  ;

    @BindView(R.id.fake_image)
    ImageView fake_image ;

    @BindView(R.id.fragment_product_detail_images_indicator)
    CircleIndicator product_detail_images_indicator ;



    void showAppbar(final boolean show ){
                collabse_toolbar_child.animate().setDuration(100).setStartDelay(200).alpha( show ? 1: 0 );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        initData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    showAppbar(false);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    showAppbar(true);
                    Log.e("ProductDetails" ,"onTransitionEnd" ) ;
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    showAppbar(true);
                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }else {
            showAppbar(true);
        }



    }



    private void setup_add_rate() {
        UserRateModel myRating = _productModel.getMyRate();
        Log.e("myrate" , myRating +"");
        if (myRating==null)
        {
            rate_is_indicator.setClickable(false);
            product_ratingBar.setOnRatingChangeListener(
                    new MaterialRatingBar.OnRatingChangeListener() {
                        @Override
                        public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                            addRate(rating);
                        }
                    }
            );
        }

        else {
            rate_is_indicator.setClickable(true);
        }
    }

    @BindView(R.id.add_fav)
    ImageView add_fav;

    @OnClick(R.id.add_cart)
    void switchCart(){
        long id = _productModel.getId();
        if(Cart.isProductCarted(id)){
            removeFromCart();
        }
        else{
            addToCart();
        }
    }


    @BindView(R.id.cart_image)
    ImageView cartImage;

    private void addToCart() {
        cartImage.setImageResource(
                R.drawable.ic_check_black_48dp
        );

        addCart.setBackgroundColor(ResourcesCompat.getColor(
                getResources(),
                R.color.green_500,
                null
        ));

        cartText.setText(R.string.added_to_cart);

        Appcontroler.getInstance().getExecutorService()
                .submit(
                        new Runnable() {
                            @Override
                            public void run() {
                                Cart.addProduct(new CartProductItem(_productModel), ProductDetailsActivity.this);
                            }
                        }
                );
    }

    private void removeFromCart() {
        cartImage.setImageResource(
                R.drawable.ic_add_shopping_cart_black_48dp
        );

        addCart.setBackgroundColor(ResourcesCompat.getColor(
                getResources(),
                R.color.colorAccent,
                null
        ));

        cartText.setText(R.string.add_to_cart);

        Appcontroler.getInstance().getExecutorService()
                .submit(
                        new Runnable() {
                            @Override
                            public void run() {
                                Cart.removeProduct(_productModel.getId(), ProductDetailsActivity.this);
                            }
                        }
                );
    }

    private void addRate(final double rating){
        try {
            long userId = getUserId();
            long productId = _productModel.getId();
            Call<ResponseBody> call =
                    Injector.Api().addRate(userId, productId, rating);

            call.enqueue(
                    new CallbackWithRetry<ResponseBody>(
                            call,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    addRate(rating);

                                }
                            }
                    ) {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                showLongToast(theView, R.string.product_rated);
                            }
                        }
                    }
            );
        }catch (Exception e){

        }
    }


    private void initData() {
            if (_productModel == null) finish();
            else bind();
    }

    private void bind() {
        getComments();
        bindStaticData();
    }

    @BindView(R.id.activity_product_details_store_title)
    TextView storeTitle;

    @BindView(R.id.activity_product_details_store_image)
    ImageView storeImage;

    @BindView(R.id.activity_product_details_title)
    TextView productTitle;

    @BindView(R.id.activity_product_details_new_price)
    TextView newPriceText;

    @BindView(R.id.activity_product_details_old_price)
    TextView oldPriceText;

    @BindView(R.id.product_details_discounted_price_layout)
    View discountLayout;

    @BindView(R.id.product_details_normal_price_layout)
    View normalLayout;

    @BindView(R.id.activity_product_details_price)
    TextView priceText;

    @BindView(R.id.activity_product_details_my_rate)
    MaterialRatingBar product_ratingBar;

    @BindView(R.id.rate_is_indicator)
    View rate_is_indicator;

    @BindView(R.id.activity_product_details_total_ratings)
    TextView totalRating;

    @BindView(R.id.activity_product_details_rating_count)
    TextView ratingCount;

    @BindView(R.id.activity_product_details_view_pager)
    ViewPager imagesPager;

    @BindView(R.id.main_toolbar_title_with_notif)
    TextView toolbar_title;


    @OnClick(R.id.add_fav)
    void switchFav(){
        if(_productModel.isFav()){
            removeFav();
        }
        else{
            addFav();
        }
    }

    private void addFav() {
        long userId = getUserId();
        if(userId != -1){
            Call<ResponseBody> call = Injector.Api().addFav(userId, _productModel.getId());

            add_fav.setImageResource(R.drawable.ic_favorite_black_24dp);

            call.enqueue(
                    new CallbackWithRetry<ResponseBody>(
                            call,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    addFav();
                                }
                            }
                    ) {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                _productModel.setFav(true);
                            }
                        }
                    }
            );
        }
    }

    private void removeFav() {
        long userId = getUserId();
        if(userId != -1){
            Call<ResponseBody> call = Injector.Api().removeFav(userId, _productModel.getId());

            add_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            call.enqueue(
                    new CallbackWithRetry<ResponseBody>(
                            call,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    removeFav();
                                }
                            }
                    ) {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                _productModel.setFav(false);
                            }
                        }
                    }
            );
        }
    }

    @BindView(R.id.add_cart)
    LinearLayout addCart;

    @BindView(R.id.add_cart_text)
    TextView cartText;

    private void bindStaticData() {
        if(_productModel != null){

            toolbar_title.setText(_productModel.getName());
            SliderAdapter sliderAdapter = new SliderAdapter(this, _productModel.getImages());
            imagesPager.setAdapter(sliderAdapter);
            product_detail_images_indicator.setViewPager(imagesPager);


            productTitle.setText(_productModel.getName());

            if(_productModel.isFav()){
                add_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
            else{
                add_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            boolean discount = _productModel.getDiscount() != 0L;

            if(discount){
                normalLayout.setVisibility(View.GONE);
                discountLayout.setVisibility(View.VISIBLE);

                long oldPrice = _productModel.getPrice();
                long perc = _productModel.getDiscount();

                long newPrice = (oldPrice * perc )/ 100;
                newPrice = oldPrice - newPrice ;

                oldPriceText.setText(oldPrice + " " + getString(R.string.le));
                oldPriceText.setPaintFlags(oldPriceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                newPriceText.setText(newPrice + " " + getString(R.string.le));
            }

            else{
                discountLayout.setVisibility(View.GONE);
                normalLayout.setVisibility(View.VISIBLE);

                long price = _productModel.getPrice();
                priceText.setText(price + " " + getString(R.string.le));
            }

            if(Cart.isProductCarted(_productModel.getId())){
                cartImage.setImageResource(
                        R.drawable.ic_check_black_48dp
                );

                addCart.setBackgroundColor(ResourcesCompat.getColor(
                        getResources(),
                        R.color.green_500,
                        null
                ));

                cartText.setText(R.string.added_to_cart);
            }
            else{
                cartImage.setImageResource(
                        R.drawable.ic_add_shopping_cart_black_48dp
                );

                addCart.setBackgroundColor(ResourcesCompat.getColor(
                        getResources(),
                        R.color.colorAccent,
                        null
                ));

                cartText.setText(R.string.add_to_cart);
            }

            UserModel supplier = _productModel.getSupplier();
            if(supplier != null){
                storeTitle.setText(supplier.getName());
                Glide.with(this)
                        .load(Config.Image_URL+supplier.getImage())
                        .apply(new RequestOptions().circleCrop())
                        .into(storeImage);
            }

            UserRateModel myRating = _productModel.getMyRate();
            Log.e("myrate" , myRating +"");
            if(myRating != null){
                try {
                    product_ratingBar.setRating(((float) myRating.getRate()));
                }
                catch (Exception e){}
            }else setup_add_rate();


            UserRateModel ratings = _productModel.getUserRates();
            if(ratings != null){
                double rate = ratings.getRate();

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                DecimalFormat formatter = (DecimalFormat)nf;
                formatter.applyPattern("#.#");
                String fString = formatter.format(rate);

                totalRating.setText(String.valueOf(fString));
                ratingCount.setText(String.valueOf(ratings.getUserCount()));

                product_ratingBar.setSecondaryProgress((int) rate);
            }

            bindSpecs();
        }
    }

    @BindView(R.id.product_details_review_recycler)
    RecyclerView reviewRecycler;
    private UserReviewAdapter userReviewAdapter;



    private void bindReviews(ArrayList<UserReviewItem> reviewItems) {
        ArrayList<UserReviewItem> reviewItems1 = new ArrayList<>();
        for(int i = 0; i < MAX_REVIEWS; i++){
            try {
                reviewItems1.add(reviewItems.get(i));
            }
            catch (Exception e){

            }
        }

        userReviewAdapter = new UserReviewAdapter(
                reviewItems1,
                this,
                new GenericItemClickCallback<UserReviewItem>() {
                    @Override
                    public void onItemClicked(UserReviewItem item) {

                    }
                },
                false);

        com.example.ahmed.octopusmart.Decorator.DividerItemDecoration decoration =
                new com.example.ahmed.octopusmart.Decorator.DividerItemDecoration(this);
        decoration.setActivated(true);

        reviewRecycler.addItemDecoration(decoration);

        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewRecycler.setAdapter(userReviewAdapter);
    }

    @BindView(R.id.product_details_spec_recycler)
    RecyclerView specRecycler;
    private ProductSpecsAdapter productSpecsAdapter;

    private void bindSpecs() {
        ArrayList<SubFilterModel> specs = _productModel.getMoreData();
        productSpecsAdapter = new ProductSpecsAdapter(
                specs,
                this,
                new GenericItemClickCallback<SubFilterModel>() {
                    @Override
                    public void onItemClicked(SubFilterModel item) {

                    }
                });

        specRecycler.setLayoutManager(new LinearLayoutManager(this));
        specRecycler.setAdapter(productSpecsAdapter);
    }

    private void getComments() {
        long userId = getUserId();

        Call<ArrayList<UserReviewItem>> commentsResponseCall =
                Injector.Api().getComments(_productModel.getId());

        commentsResponseCall.enqueue(
                new CallbackWithRetry<ArrayList<UserReviewItem>>(
                        commentsResponseCall,
                        new onRequestFailure() {
                            @Override
                            public void onFailure() {

                            }
                        }

                ) {
                    @Override
                    public void onResponse(Call<ArrayList<UserReviewItem>> call, Response<ArrayList<UserReviewItem>> response) {
                        if (response.isSuccessful()) {
                            userReviewItems =
                                    response.body();

                            bindReviews(userReviewItems);
                        }
                    }
                }
        );
    }

    private ArrayList<UserReviewItem> userReviewItems;


    @BindView(R.id.write_review)
    EditText reviewEdit;

    @OnClick(R.id.send_review)
     void sendReview(){
        String review = "";
        try {
            review = reviewEdit.getEditableText().toString();
        }
        catch (Exception e){
            e.printStackTrace();
            reviewEdit.setText("");
            showLongToast(
                    theView,
                    R.string.invalid_review);
        }

        if(review != null && !review.isEmpty()){
            showLoading(LoadingCases.show, null);

            long userId = getUserId();
            long productId = _productModel.getId();

            Call<ResponseBody> call =
                    Injector.Api().addComment(userId, productId, review);

            call.enqueue(
                    new CallbackWithRetry<ResponseBody>(
                            call,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    showLoading(LoadingCases.fail,
                                            new LoadingActionClick() {
                                                @Override
                                                public void OnClick() {
                                                    sendReview();
                                                }
                                            });
                                }
                            }
                    ) {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            showLoading(LoadingCases.hide, null);

                            if(response.isSuccessful()){
                                reviewEdit.setText("");
                                showLongToast(theView, R.string.review_submit_success);
                            }
                            else{
                                showLongToast(theView, R.string.toast_unexpected_error);
                            }
                        }
                    }
            );
        }
    }

    @OnClick(R.id.activity_product_details_show_specs)
    void showAllSpecs(){
        Intent intent = new Intent(this, AllSpecsActivity.class);
        intent.putExtra(AllSpecsActivity.KEY_SPECS, _productModel.getMoreData());
        startActivity(intent);
    }

    @OnClick(R.id.activity_product_details_show_reviews)
    void showAllReviews(){
        Intent intent = new Intent(this, AllReviewsActivity.class);
        intent.putParcelableArrayListExtra(AllReviewsActivity.KEY_REVIEWS, userReviewItems);
        startActivity(intent);
    }


    @OnClick(R.id.back_btt)
    void back_btt(){
        onBackPressed();
    }
}