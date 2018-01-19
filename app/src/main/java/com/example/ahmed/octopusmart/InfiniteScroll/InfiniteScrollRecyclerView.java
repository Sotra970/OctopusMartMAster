package com.example.ahmed.octopusmart.InfiniteScroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Ahmed Naeem on 1/5/2018.
 */

public class InfiniteScrollRecyclerView extends RecyclerView {

    public final static String GRAVITY_TOP = "GRAVITY_TOP";
    public final static String GRAVITY_BOTTOM = "GRAVITY_BOTTOM";

    private String gravity = GRAVITY_TOP;


    private InfiniteScrollLoaderCallback scrollLoaderCallback;

    public void setScrollLoaderCallback(InfiniteScrollLoaderCallback scrollLoaderCallback) {
        this.scrollLoaderCallback = scrollLoaderCallback;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public InfiniteScrollRecyclerView(Context context) {
        super(context);
        init();
    }

    public InfiniteScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InfiniteScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(
                new OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        if(scrollLoaderCallback != null && getLayoutManager() != null){
                            boolean loadMore = false;


                            try {
                                switch (gravity){
                                    case GRAVITY_BOTTOM:
                                        Log.e("infinite", ((LinearLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition() + "");
                                        loadMore = ((LinearLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition() == getAdapter().getItemCount() - 1;
                                        break;

                                    case GRAVITY_TOP:
                                        Log.e("infinite", ((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition() + "");
                                        loadMore = ((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0;
                                        break;
                                }
                            }
                            catch (Exception ignored){
                                Log.e("infinite", ignored.getMessage() + "");
                            }

                            if(loadMore){
                                scrollLoaderCallback.onLoadMore();
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if(!(layout instanceof LinearLayoutManager)){
            throw new RuntimeException("Use a LinearLayoutManager!!!");
        }
    }

}
