package com.example.ahmed.octopusmart.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;


import com.example.ahmed.octopusmart.Activity.CategoryActivity;
import com.example.ahmed.octopusmart.Interfaces.FilterListener;
import com.example.ahmed.octopusmart.Model.ServiceModels.FilterModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    ExpandableListView expandableListView;
    com.example.ahmed.octopusmart.RecyclerAdapter.FilterAdapter expandableListAdapter;
    ArrayList<FilterModel> filterModels = new ArrayList<>();
    View res;
    String TAG = this.getClass().getSimpleName();
    com.example.ahmed.octopusmart.Interfaces.FilterListener navigationItemClickedListener;

    public void setNavigationItemClickedListener(com.example.ahmed.octopusmart.Interfaces.FilterListener navigationItemClickedListener) {
        this.navigationItemClickedListener = navigationItemClickedListener;
    }

    CategoryActivity.FilterFragmentListener filterFragmentListener ;

    public void setFilterFragmentListener(CategoryActivity.FilterFragmentListener filterFragmentListener) {
        this.filterFragmentListener = filterFragmentListener;
    }

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment getInstance(ArrayList<FilterModel> filterModels,CategoryActivity.FilterFragmentListener filterFragmentListener, FilterListener navigationItemClickedListener){
        FilterFragment navigationFragment = new FilterFragment();
        navigationFragment.filterModels = filterModels ;
        navigationFragment.setNavigationItemClickedListener(navigationItemClickedListener);
        navigationFragment.setFilterFragmentListener(filterFragmentListener);
        return  navigationFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        res = inflater.inflate(R.layout.fragment_category_filter, container, false);
        ButterKnife.bind(this , res) ;
        expandableListView = (ExpandableListView) res.findViewById(R.id.expandableListView);

        expandableListAdapter = new com.example.ahmed.octopusmart.RecyclerAdapter.FilterAdapter(getContext(), filterModels);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView paren, View view, int i, long l) {

//                int index = (int) expandableListView.getPackedPositionForGroup(i);
////                int index = expandableListView.getFlatListPosition(expandableListView.getPackedPositionForGroup(i));
//
//
//                Log.e("nav_index", index + " ");
//
//                expandableListAdapter.setGroub_checked_index(i);
//                MenuListModel menuListModel =     expandableListAdapter.getGroup(i);
//                if (!menuListModel.isHasChildes())
//                    navigationItemClickedListener.itemClicked(menuListModel);
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                expandableListView.setItemChecked(index, true);

                Log.e("nav_index", index + " ");
                expandableListAdapter.setChild_checked_index(groupPosition , childPosition);

                CheckBox checkBox = v.findViewById(R.id.sub_filter_check_box) ;
                SubFilterModel subFilterModel =   filterModels.get(groupPosition).getSubFilters().get(childPosition) ;
                if (checkBox.isChecked()){
                    checkBox.setChecked(false);

                }else
                if (!checkBox.isChecked()){
                    checkBox.setChecked(true);
                }
                subFilterModel.setChecked(checkBox.isChecked());
                subFilterModel.setGroup(groupPosition);
                subFilterModel.setChild(childPosition);
                filterModels.get(groupPosition).getSubFilters().set(childPosition,subFilterModel);

                navigationItemClickedListener.itemChildClicked(
                        subFilterModel ,
                        checkBox.isChecked()
                );

                return false;
            }
        });

    return res ;
    }


    @OnClick(R.id.sheet_header)
    void switch_sheet(){
        Log.e("category_filter" , "header clicked") ;
        filterFragmentListener.switch_sheet();
    }


    void removeCheck(int groupPosition ,int  childPosition ){
        int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
        CheckBox checkBox  = expandableListView.getChildAt(index).findViewById(R.id.sub_filter_check_box) ;
        checkBox.setChecked(false);
        SubFilterModel subFilterModel =   filterModels.get(groupPosition).getSubFilters().get(childPosition) ;
        subFilterModel.setChecked(checkBox.isChecked());
        filterModels.get(groupPosition).getSubFilters().set(childPosition,subFilterModel);

    }



}