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
import com.example.ahmed.octopusmart.Activity.Base.RequestActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.CartProductItem;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.ProductModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserRateModel;
import com.example.ahmed.octopusmart.Model.UserReviewItem;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.HomeCategoryAdapter;
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
        supportFinishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
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



    ProductModel product ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        product = _productModel ;
        ButterKnife.bind(this);

        Glide.with(this)
                .load(Config.Image_URL+product.getImages().get(0))
                .into(fake_image);

        headar_bind();
       if (savedInstanceState ==null){
           get_product_full_data(new  SuccessListener(){
               @Override
               public void onFinish() {
                   initData();
               }
           }) ;
       }else {
           product = (ProductModel) savedInstanceState.getSerializable("product");
           initData();
       }
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

    private void get_product_full_data(final SuccessListener successListener) {
        //
        showLoading(LoadingCases.show , null);
        Call<ProductModel> call = Injector.Api().getProduct(
                product.getId() ,
                Appcontroler.getUserId()
        ) ;
        call.enqueue(new CallbackWithRetry<ProductModel>(call, new onRequestFailure() {
            @Override
            public void onFailure() {
                showLoading(LoadingCases.fail, new LoadingActionClick() {
                    @Override
                    public void OnClick() {
                        get_product_full_data(successListener);
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()){
                    product = response.body() ;
                    successListener.onFinish();
                }
                showLoading(LoadingCases.hide , null);
            }
        });
    }


    private void setup_add_rate() {
        UserRateModel myRating = product.getMyRate();
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
        long id = product.getId();
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

        Appcontroler.getExecutorService()
                .submit(
                        new Runnable() {
                            @Override
                            public void run() {
                                Cart.addProduct(new CartProductItem(product), ProductDetailsActivity.this);
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

        Appcontroler.getExecutorService()
                .submit(
                        new Runnable() {
                            @Override
                            public void run() {
                                Cart.removeProduct(product.getId(), ProductDetailsActivity.this);
                            }
                        }
                );
    }

    private void addRate(final double rating){
        try {
            long userId = getUserId();
            long productId = product.getId();
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
            if (product == null) finish();
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
        if(product.isFav()){
            removeFav();
        }
        else{
            addFav();
        }
    }

    private void addFav() {
        long userId = getUserId();
        if(userId != -1){
            Call<ResponseBody> call = Injector.Api().addFav(userId, product.getId());

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
                                product.setFav(true);
                            }
                        }
                    }
            );
        }
    }

    private void removeFav() {
        long userId = getUserId();
        if(userId != -1){
            Call<ResponseBody> call = Injector.Api().removeFav(userId, product.getId());

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
                                product.setFav(false);
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


    void headar_bind(){

        toolbar_title.setText(product.getName());
        SliderAdapter sliderAdapter = new SliderAdapter(this, product.getImages());
        imagesPager.setAdapter(sliderAdapter);
        product_detail_images_indicator.setViewPager(imagesPager);

    }

    private void bindStaticData() {
        if(product != null){


            similar_setup();

            productTitle.setText(product.getName());

            if(product.isFav()){
                add_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
            else{
                add_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            boolean discount = product.getDiscount() != 0L;

            if(discount){
                normalLayout.setVisibility(View.GONE);
                discountLayout.setVisibility(View.VISIBLE);

                long oldPrice = product.getPrice();
                long perc = product.getDiscount();

                long newPrice = (oldPrice * perc )/ 100;
                newPrice = oldPrice - newPrice ;

                oldPriceText.setText(oldPrice + " " + getString(R.string.le));
                oldPriceText.setPaintFlags(oldPriceText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                newPriceText.setText(newPrice + " " + getString(R.string.le));
            }

            else{
                discountLayout.setVisibility(View.GONE);
                normalLayout.setVisibility(View.VISIBLE);

                long price = product.getPrice();
                priceText.setText(price + " " + getString(R.string.le));
            }

            if(Cart.isProductCarted(product.getId())){
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

            UserModel supplier = product.getSupplier();
            if(supplier != null){
                storeTitle.setText(supplier.getName());
                Glide.with(this)
                        .load(Config.Image_URL+supplier.getImage())
                        .apply(new RequestOptions().circleCrop())
                        .into(storeImage);
            }

            UserRateModel myRating = product.getMyRate();
            Log.e("myrate" , myRating +"");
            if(myRating != null){
                try {
                    product_ratingBar.setRating(((float) myRating.getRate()));
                }
                catch (Exception e){}
            }else setup_add_rate();


            UserRateModel ratings = product.getUserRates();
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

    @BindView(R.id.similar_products)
    RecyclerView similar_list ;
    private void similar_setup() {
        HomeCategoryAdapter adapter= new HomeCategoryAdapter(product.getSimilar(), getApplicationContext(), new HomeAdapterListener() {
            @Override
            public void onProductClicked(ProductModel productModel, View shared, int postion) {
                _productModel = productModel ;
                startActivity(new Intent(getApplicationContext() , ProductDetailsActivity.class) , shared , ProductDetailsActivity.this);
            }

            @Override
            public void onSliderProductClicked(ProductModel productModel, View shared, int postion) {

            }

            @Override
            public void onCategoryHeadarClicked(CatModel catModel, View shared, int postion) {

            }
        });

        similar_list.setAdapter(adapter);
        similar_list.setLayoutManager(new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.HORIZONTAL , false));


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
        ArrayList<SubFilterModel> specs = product.getMoreData();
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
                Injector.Api().getComments(product.getId());

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
            long productId = product.getId();

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
        intent.putExtra(AllSpecsActivity.KEY_SPECS, product.getMoreData());
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
        supportFinishAfterTransition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("product" , product);
    }

    @Override
    public void supportFinishAfterTransition() {
        Log.e("ProductDetails" , "supportFinishAfterTransition");
        if (product !=null){
            Intent intent = new Intent() ;
            intent.putExtra("product" , product );
            setResult(1  , intent);
            Log.e("ProductDetails" , "setResult");
        }
        super.supportFinishAfterTransition();
    }
}