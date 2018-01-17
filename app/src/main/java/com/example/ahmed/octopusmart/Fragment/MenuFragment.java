package com.example.ahmed.octopusmart.Fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.RequestActivity;
import com.example.ahmed.octopusmart.Activity.LoginActivity;
import com.example.ahmed.octopusmart.Activity.OrderCaseActivity;
import com.example.ahmed.octopusmart.Activity.SettingsActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Model.ServiceModels.CatModel;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.MenuCategoryAdapter;
import com.example.ahmed.octopusmart.Utils.transition.FabTransform;
import com.example.ahmed.octopusmart.holders.TabTitleHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ahmed.octopusmart.Activity.Base.BaseActivity.history_code;
import static com.example.ahmed.octopusmart.Activity.Base.BaseActivity.login_code;
import static com.example.ahmed.octopusmart.Activity.Base.BaseActivity.traking_code;

/**
 * Created by ahmed on 12/7/2017.
 */

public class MenuFragment extends BaseFragment {

    @Nullable@BindView(R.id.user_img)
    ImageView userImg;

    @Nullable@BindView(R.id.user_name_text)
    TextView userNameText;

    @BindView(R.id.track_order_img_shared)
    View track_order_img;

    @BindView(R.id.history_img_shared)
    View history_img;

    @BindView(R.id.settings_card)
    CardView settingsCard;

    @BindView(R.id.logout_card)
    View logoutCard;

    MenuTabsListener menuTabsListener ;


    public  static MenuFragment getInstance(MenuTabsListener menuTabsListener){
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.menuTabsListener = menuTabsListener ;
        return menuFragment ;
    }

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu,container,false);
        ButterKnife.bind(this,view);
        getCategories();
        tab_setub();
        bind_data();

        return view;
    }






    void bind_data(){
        if (Appcontroler.isUserSigned()){
            UserModel user = Appcontroler.getInstance().getPrefManager().getUser();
           logoutCard.setVisibility(View.VISIBLE);
           settingsCard.setVisibility(View.VISIBLE);
            userNameText.setText(user.getName());

        }else {
            logoutCard.setVisibility(View.GONE);
            settingsCard.setVisibility(View.GONE);
            userNameText.setText(getString(R.string.login_text));
        }

    }

    @BindView(R.id.menu_categories_list)
    RecyclerView categories_list ;


    void getCategories(){
        getBaseActivity().getCategories(new RequestActivity.CategoriesListener() {
            @Override
            public void onFinish(ArrayList<CatModel> models) {

                Log.e("models" , models.size() +"");
                MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter(models, getActivity(), new CategoryMenuListener() {
                    @Override
                    public void onCategoryClicked(CatModel catModel, View shared) {
                        hideMenu();
                        getBaseActivity().startCategory(catModel,null  , getBaseActivity());
                    }

                    @Override
                    public void onAllCategoryClicked(View shared) {
                        getBaseActivity().startCategory(null  , getBaseActivity());
                    }
                });

                categories_list.setLayoutManager(new GridLayoutManager(getContext() , 3));
                categories_list.setAdapter(menuCategoryAdapter);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @OnClick(R.id.history_img)
    void showHistory(){
        if (Appcontroler.isUserSigned()){
            hideMenu();
            showStates(OrderStateFragment.OrderCases.history);
        }

        else startLogin(history_img, R.drawable.white_radius, R.color.white, history_code);

    }


    @OnClick(R.id.track_order_img)
    void showTraking(){
        if (Appcontroler.isUserSigned()){
            showStates(OrderStateFragment.OrderCases.tracking);
            hideMenu();
        }
        else startLogin(track_order_img, R.drawable.white_radius, R.color.white, traking_code);

    }

    void showStates(final OrderStateFragment.OrderCases  order_case){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getBaseActivity()._orderCase = order_case ;
                startActivity(new Intent(getContext() , OrderCaseActivity.class));
            }
        },150);

    }

    @BindView(R.id.tab_layout)
    TabLayout tabLayout ;
    ArrayList<TabTitleHolder> holders = new ArrayList<>() ;
    void tab_setub (){
        TabTitleHolder home  = new TabTitleHolder(getString(R.string.home) , R.drawable.ic_home_black_24dp , getContext()) ;
        TabTitleHolder cart  = new TabTitleHolder(getString(R.string.cart_text) , R.drawable.ic_shopping_cart_black_24dp , getContext()) ;
        TabTitleHolder fav  = new TabTitleHolder(getString(R.string.favorite_text) , R.drawable.ic_favorite_black_48dp , getContext()) ;
        TabTitleHolder menu  = new TabTitleHolder(getString(R.string.menu_text) , R.drawable.ic_menu_black_24dp , getContext()) ;

        holders.add(home);
        holders.add(cart);
        holders.add(fav);
        holders.add(menu);

        home.select();
        tabLayout.addTab(tabLayout.newTab().setCustomView(home.getView()));

        tabLayout.addTab(tabLayout.newTab().setCustomView(cart.getView()));
        tabLayout.addTab(tabLayout.newTab().setCustomView(fav.getView()));
        tabLayout.addTab(tabLayout.newTab().setCustomView(menu.getView()));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                holders.get(tab.getPosition()).select();
                if (menuTabsListener !=null) menuTabsListener.onMenuTabSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                holders.get(tab.getPosition()).unSelect();
                if (menuTabsListener !=null) menuTabsListener.onMenuTabUnSelected(tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (menuTabsListener !=null) menuTabsListener.onMenuTabReSelected(tab.getPosition());

            }
        });
    }

   public  interface  MenuTabsListener{
        void onMenuTabSelected(int tab);
        void onMenuTabUnSelected(int tab);
        void onMenuTabReSelected(int tab);
    }

    @OnClick(R.id.user_img_layout)
    void startProfile(){
       if (!Appcontroler.isUserSigned()){
           startLogin(userImg, R.drawable.ic_account_circle_black_48dp, R.color.colorPrimary ,login_code);
       }
    }


   void  startLogin(View shared , int icon , int color ,int code){
        if (!Appcontroler.isUserSigned()){
            Intent intent = new Intent(getContext() , LoginActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    shared, getString(R.string.circle_morph_tansition));
            FabTransform.addExtras(intent,
                    ContextCompat.getColor(getActivity(), color),icon);
            getBaseActivity().startActivityForResult(intent,code , options.toBundle() );
        }
    }




    void hideMenu(){
        if (menuTabsListener !=null) menuTabsListener.onMenuTabSelected(3);
    }

    public  interface  CategoryMenuListener{
        void onCategoryClicked(CatModel catModel , View shared );
        void onAllCategoryClicked(View shared);
    }



    @OnClick(R.id.settings_card)
    void opinsettings(){
        hideMenu();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity() , SettingsActivity.class));
            }
        },300);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("MenuFragment" , "onActivityResult");
        if (resultCode == 200)
       switch (requestCode){
           case login_code : {
               bind_data();
               hideMenu();
               break;
           }
           case history_code : {
               hideMenu();
               showStates(OrderStateFragment.OrderCases.history);
               break;
           }
           case traking_code : {
               hideMenu();
               showStates(OrderStateFragment.OrderCases.tracking);
               break;
           }
       }
    }

    @OnClick(R.id.logout_card)
    void logout(){
        Appcontroler.getInstance().getPrefManager().clear(false);
        hideMenu();
        bind_data();
    }

}
