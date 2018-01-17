package com.example.ahmed.octopusmart.RecyclerAdapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Model.ServiceModels.FilterModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<FilterModel> filterModels;
    int Groub_checked_index = 0 ;
    HashMap<Integer , Integer> child_checked_index = new HashMap<>();


    public int getGroub_checked_index() {
        return Groub_checked_index;
    }

    public void setGroub_checked_index(int groub_checked_index) {
        Groub_checked_index = groub_checked_index;
    }


    public void setChild_checked_index(int group_checked_index ,  int child_checked_index) {
        this.child_checked_index.put(group_checked_index , child_checked_index);
    }

    public FilterAdapter(Context context, ArrayList<FilterModel> expandableList){
        this.context = context;
        this.filterModels = expandableList;
    }

    @Override
    public SubFilterModel getChild(int listPosition, int expandedListPosition) {
        return this.filterModels.get(listPosition).getSubFilters().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = getChild(listPosition, expandedListPosition).getName();

        Log.e("child txt " , expandedListText);
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.sub_filter_item, null);

            TextView expandedListTextView = (TextView) convertView
                    .findViewById(R.id.expandedListItem);
            expandedListTextView.setText(expandedListText);


            CheckBox sub_filter_check_box = convertView.findViewById(R.id.sub_filter_check_box) ;
        sub_filter_check_box.setChecked(getChild(listPosition, expandedListPosition).isChecked());

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.filterModels.get(listPosition).getSubFilters()
                .size();
    }

    @Override
    public FilterModel getGroup(int listPosition) {
        return this.filterModels.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.filterModels.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }


    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle ;

            listTitle = filterModels.get(listPosition).getName();


        LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.filter_item, null, false);


            final ImageView indicator = (ImageView) convertView.findViewById(R.id.List_Group_indicator);


            if (isExpanded) {
                if (indicator.getRotation() == 0)
                    indicator.animate().rotation(180).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            indicator.setRotation(180);
                        }
                    });
            }
            else
                if (indicator.getRotation()==180)
                    indicator.animate().rotation(0).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        indicator.setRotation(0);
                    }
                });


            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.listTitle);
            listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


}