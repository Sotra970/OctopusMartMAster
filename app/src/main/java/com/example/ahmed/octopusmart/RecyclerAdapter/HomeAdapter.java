package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.App.Config;
import com.example.ahmed.octopusmart.Interfaces.HomeAdapterListener;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeBase;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeCategoryProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeSliderProducts;
import com.example.ahmed.octopusmart.Model.ServiceModels.Home.HomeSortingProducts;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.View.PagerSwitcher;
import com.example.ahmed.octopusmart.ViewPagerAdapter.HomeSliderAdapter;
import com.example.ahmed.octopusmart.holders.CategoryHomeHolder;
import com.example.ahmed.octopusmart.holders.LoadingViewHolder;
import com.example.ahmed.octopusmart.holders.PagerHomeHolder;
import com.example.ahmed.octopusmart.holders.SliderHomeHolder;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  int cat =0 /*,slider=1 */, arrivals =2 , popular =3  ,  loading =4 /*,pager =5*/ ;
    public List<HomeBase> homeBases = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
     HomeAdapterListener listener;

    public HomeAdapter(ArrayList<HomeBase> homeBases, Context context , HomeAdapterListener listener ) {
        inflater = LayoutInflater.from(context);
        this.homeBases = homeBases;
        this.context = context;
        this.listener = listener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       /* if (viewType == slider){
            View view = inflater.inflate(R.layout.home_slider, parent, false);
            final SliderHomeHolder viewHolder = new SliderHomeHolder(view);

            return viewHolder;
        }

        if (viewType == pager){
            View view = inflater.inflate(R.layout.home_pager, parent, false);
            final PagerHomeHolder viewHolder = new PagerHomeHolder(view);
            return viewHolder;
        }*/

        if (viewType == cat || viewType == popular || viewType == arrivals  ){
            View view = inflater.inflate(R.layout.home_section_item, parent, false);
            CategoryHomeHolder viewHolder = new CategoryHomeHolder(view);
            return viewHolder;
        }

        if (viewType == loading){
            View v2 = inflater.inflate(R.layout.item_progress, parent, false);
            return  new LoadingViewHolder(v2);
        }

        return  null ;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("getItemViewType" , position+"") ;

        if (position != 0 && position == getItemCount() - 1 ) return  loading ;
/*       if (homeBases.get(position).isSlider() && position !=0) return  pager ;
       if (homeBases.get(position).isSlider() && position==0) return  slider ;*/
       if (homeBases.get(position).isCat()) return  cat ;
       if (homeBases.get(position).isArrivals()) return  arrivals ;
       if (homeBases.get(position).isPopular()) return  popular ;
       if (homeBases.get(position).isLoading()) return  loading ;
        return  -1 ;
    }

    private boolean showLoader = true;
    public void showLoading(boolean status) {
        showLoader = status;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e("HomeAdapterPosition" , position+"") ;
        int tybe = getItemViewType(position);

        if (getItemViewType(position) == loading){
            Log.e("HomeAdapter" , "loading") ;
            final LoadingViewHolder loaderViewHolder = (LoadingViewHolder) holder;
            if (showLoader) {
                loaderViewHolder.mProgressBar.setVisibility(View.VISIBLE);
            } else {
                loaderViewHolder.mProgressBar.setVisibility(View.GONE);
            }
            return;
        }

        HomeBase current  = homeBases.get(position) ;
    /*    if (tybe == slider){
            bind((HomeSliderProducts) current , (SliderHomeHolder) holder );
            return;
        }

        if (tybe == pager){
            bind((HomeSliderProducts) current , (PagerHomeHolder) holder );
            return;
        }*/

        if (tybe == cat){
            bind((HomeCategoryProducts) current , (CategoryHomeHolder) holder);

            return;
        }

        if (tybe == arrivals || tybe == popular){
            bind((HomeSortingProducts) current , (CategoryHomeHolder) holder , tybe);
            return;
        }


    }

    void bind(final HomeSliderProducts products , final SliderHomeHolder holder ){

        final ViewPager pager  = holder.pager ;
        pager.setAdapter(new HomeSliderAdapter(context,products.getData() , listener));
        pager.setOffscreenPageLimit(products.getData().size());
        final CircleIndicator indicator  = holder.indicator;
        indicator.setViewPager(holder.pager);
           // Auto start of viewpager
           final PagerSwitcher switcher = new PagerSwitcher(context ,  pager , products.getData().size());
           switcher.startSwitching();
           pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
               @Override
               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

               }

               @Override
               public void onPageSelected(int position) {
                   switcher.setPage(position);
               }

               @Override
               public void onPageScrollStateChanged(int state) {

               }
           });

    }


    void bind(final HomeSliderProducts products , final PagerHomeHolder holder ){

        final ViewPager pager  = holder.pager ;
        pager.setAdapter(new HomeSliderAdapter(context,products.getData() , listener));
        pager.setOffscreenPageLimit(products.getData().size());

    }


    void bind(final HomeCategoryProducts cats , final CategoryHomeHolder holder ){
        HomeCategoryAdapter adapter= new HomeCategoryAdapter(cats.getData(),context , listener);
        LinearLayoutManager mobileHorizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.category_list.setLayoutManager(mobileHorizontalLayoutManager);
        holder.category_list.setAdapter(adapter);
        holder.category_title.setText(cats.getCategory().getName());
        holder.see_more.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(Config.Image_URL+cats.getCategory().getIcon())
                .apply(new RequestOptions().fitCenter())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.categories_img);
        holder.category_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoryHeadarClicked(cats.getCategory() , holder.categories_img , 0);
            }
        });

    }


    void bind(HomeSortingProducts products , CategoryHomeHolder holder , int tybe ){
        HomeCategoryAdapter adapter= new HomeCategoryAdapter(products.getData(),context , listener);
        LinearLayoutManager mobileHorizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.category_list.setLayoutManager(mobileHorizontalLayoutManager);
        holder.category_list.setAdapter(adapter);
        String title  = tybe == popular ? Appcontroler.getInstance().getString(R.string.most_popular)
                                        :Appcontroler.getInstance().getString(R.string.new_arrivals) ;

        holder.see_more.setVisibility(View.GONE);
        int icon = tybe == popular ? R.drawable.ic_play_icon : R.drawable.ic_favorite_black_48dp ;
//        int icon = tybe == popular ? R.drawable.new_arrivals : R.drawable.popular ;
        holder.category_title.setText(title );
        Glide.with(context)
                .load(icon)
                .apply(new RequestOptions().fitCenter())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.categories_img);
    }



    @Override
    public int getItemCount() {
        if (homeBases == null || homeBases.size() == 0) {
            return 0;
        }
        // +1 for loader
        return homeBases.size() + 1;
    }

    public void insert(HomeBase temp) {
        this.homeBases.add(temp) ;
        Log.e("HomeAdapterInsert" , (homeBases.size()-1) +"") ;
        if (homeBases.size() == 1 )
            notifyDataSetChanged();
        else
        notifyItemInserted(homeBases.size()-1);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
