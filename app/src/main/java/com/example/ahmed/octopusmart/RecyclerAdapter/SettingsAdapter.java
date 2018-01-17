package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.holders.SettingItemViewHolder;

import java.util.ArrayList;

/**
 * Created by ahmed on 23/12/2017.
 */

public class SettingsAdapter extends GenericAdapter<String> {

    public SettingsAdapter(ArrayList<String> items,
                           Context context,
                           GenericItemClickCallback<String> adapterItemClickCallbacks)
    {
        super(items, context, adapterItemClickCallbacks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.settings_item, parent, false);
        return new SettingItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String item = getItem(position);
        if(item != null && !item.isEmpty()){
            SettingItemViewHolder viewHolder = (SettingItemViewHolder) holder;
            viewHolder.title.setText(item);
        }
    }
}
