package com.example.ahmed.octopusmart.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.ahmed.octopusmart.Activity.CategoryActivity;
import com.example.ahmed.octopusmart.BadgeConfig.Utils;
import com.example.ahmed.octopusmart.Interfaces.FilterListener;
import com.example.ahmed.octopusmart.Model.ServiceModels.FilterModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.SubFilterModel;
import com.example.ahmed.octopusmart.R;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
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
                                        int groupPosition, int childPosition, long id)
            {

                Log.e("category_filter", "onChildClick(): called");

                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                expandableListView.setItemChecked(index, true);

                Log.e("nav_index", index + " ");
                expandableListAdapter.setChild_checked_index(groupPosition , childPosition);

                CheckBox checkBox = v.findViewById(R.id.sub_filter_check_box) ;
                SubFilterModel subFilterModel =   filterModels.get(groupPosition).getSubFilters().get(childPosition) ;

                if (checkBox.isChecked()){
                    checkBox.setChecked(false);

                    onItemChecked(subFilterModel, false, groupPosition, childPosition);
                }

                else if (!checkBox.isChecked()){
                    checkBox.setChecked(true);

                    onItemChecked(subFilterModel, true, groupPosition, childPosition);
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
        SubFilterModel subFilterModel =   filterModels.get(groupPosition).getSubFilters().get(childPosition) ;
        subFilterModel.setChecked(false);
        filterModels.get(groupPosition).getSubFilters().set(childPosition,subFilterModel);
        expandableListAdapter.notifyDataSetChanged();
    }

    @BindView(R.id.filters)
    FlowLayout filtersLayout;

    ArrayList<View> activeFilters = null;

    void onItemChecked(SubFilterModel subFilterModel, boolean checked,  int group, int item) {
        Log.e("category_filter", "onItemChecked(): called");

        Log.e("category_filter", "onItemChecked():  checked == " + checked);

        if(subFilterModel != null){
            Log.e("category_filter", "onItemChecked(): subFilterModel != null");

            if(checked){
                addActiveFilter(subFilterModel, group, item);
            }

            else{
                removeActiveFilter(subFilterModel);
            }
        }

        else{
            Log.e("category_filter", "onItemChecked(): subFilterModel == null");

        }

        Log.e("category_filter", "-------------------------------------------------------------------------" );
    }

    private void addActiveFilter(SubFilterModel subFilterModel, final int group, final int item) {
        Log.e("category_filter", "addActiveFilter(): called");

        if(activeFilters == null){
            activeFilters = new ArrayList<>();
        }

        final View v = LayoutInflater.from(getContext()).inflate(R.layout.active_filter_item, null);
        v.setTag(subFilterModel.getId());

        TextView textView = v.findViewById(R.id.title);
        View remove = v.findViewById(R.id.remove);

        textView.setText(subFilterModel.getName());
        remove.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(activeFilters != null){
                            filtersLayout.removeView(v);
                            activeFilters.remove(v);

                            filtersLayout.post(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            if(categoryActivity() != null){
                                                if(filtersLayout.getChildCount() == 0){
                                                    //filtersLayout.getLayoutParams().height = 0;
                                                    categoryActivity().reCalculatePeekValue(0);
                                                }
                                                else{
                                                    categoryActivity().reCalculatePeekValue(filtersLayout.getHeight());
                                                }
                                            }
                                        }
                                    }
                            );

                            removeCheck(group, item);
                        }
                    }
                }
        );

        activeFilters.add(v);

        filtersLayout.addView(v);


        filtersLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if(categoryActivity() != null){
                            categoryActivity().reCalculatePeekValue(filtersLayout.getHeight());

                        }
                    }
                }
        );
    }

    private CategoryActivity categoryActivity(){
        try {
            return ((CategoryActivity) getActivity());
        }
        catch (Exception e){
            return null;
        }
    }

    private void removeActiveFilter(SubFilterModel subFilterModel) {
        if(activeFilters != null && !activeFilters.isEmpty()){

            View toRemove = null;

            for (View v : activeFilters){
                if(v != null && Objects.equals(v.getTag(), subFilterModel.getId())){
                    toRemove = v;
                    break;
                }
            }

            if(toRemove != null){
                filtersLayout.removeView(toRemove);
                activeFilters.remove(toRemove);

                Log.e("filterFragment", "view found removing");

                if(filtersLayout.getChildCount() == 0){
                    Log.e("filterFragment", "no filters remained");

                    if(categoryActivity() != null){
                        categoryActivity().reCalculatePeekValue(0);
                    }
                }

                else{
                    filtersLayout.post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    if(categoryActivity() != null){
                                        Log.e("filterFragment", "activity != null recalculating height");
                                        categoryActivity().reCalculatePeekValue(filtersLayout.getHeight());
                                    }
                                    else{
                                        Log.e("filterFragment", "activity == null");
                                    }
                                }
                            }
                    );
                }
            }
            else{
                Log.e("filterFragment", "didn't find view to remove");
            }
        }
    }



}