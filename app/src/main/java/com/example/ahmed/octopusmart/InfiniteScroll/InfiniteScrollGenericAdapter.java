package com.example.ahmed.octopusmart.InfiniteScroll;


import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.RecyclerAdapter.GenericAdapter;

import java.util.ArrayList;

/**
 * Created by Ahmed Naeem on 1/5/2018.
 */

public abstract class InfiniteScrollGenericAdapter<T> extends GenericAdapter<T> {

    public InfiniteScrollGenericAdapter(ArrayList<T> items,
                                        Context context,
                                        GenericItemClickCallback<T> adapterItemClickCallbacks)
    {
        super(items, context, adapterItemClickCallbacks);
    }

    public void addItem(T item)
    {
        ArrayList<T> items = getItems();
        if(items == null){
            items = new ArrayList<>();
        }

        items.add(0, item);
        notifyItemInserted(0);
    }

    public void addItemsToBottom(final ArrayList<T> newItems){
        if(newItems != null && !newItems.isEmpty()){
            ArrayList<T> items = getItems();
            if(items == null){
                updateData(newItems);
                return;
            }
            try {
                final int start = items.size();
                items.addAll(newItems);
                notifyItemRangeInserted(start, newItems.size());
            }
            catch (Exception ignored){
                Log.e("addItemsToBottom", ignored.getMessage() + "");
            }
        }
    }


}
