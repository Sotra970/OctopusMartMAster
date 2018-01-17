package com.example.ahmed.octopusmart.Fragment;

import android.view.View;

import com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.fail;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.show;

/**
 * Created by sotra on 12/22/2017.
 */

public class BaseLoginFragment  extends  BaseFragment{


    LoadingDialogActivity.LoadingCases loadingCase ;
    public  void loading(LoadingDialogActivity.LoadingCases loadingCase , LoadingActionClick actionClick) {
        this.actionClick = actionClick ;
        this.loadingCase = loadingCase ;
        setup();
    }

    LoadingActionClick actionClick ;

    void setup(){
        if (loadingCase == show){
            showLoad();
        }else if (loadingCase == fail){
            showFail(this.actionClick);
        }
    }

    @BindView(R.id.load_view)
    View load_view ;

    @BindView(R.id.fail_view)
    View fail_view ;

    void showLoad(){
        load_view.setVisibility(View.VISIBLE);
        fail_view.setVisibility(View.GONE);
    }

    public  void showFail(LoadingActionClick actionClick){
        this.actionClick = actionClick ;
        fail_view.setVisibility(View.VISIBLE);
        load_view.setVisibility(View.GONE);
    }


    @OnClick(R.id.fail_view_button)
    void onFailViewClicked(){
        showLoad();
        if (actionClick != null)
            actionClick.OnClick();
    }



    @BindView(R.id.loagin_loading_view)
    View loagin_loading_view ;
    @BindView(R.id.body_container)
    View login_fragments_container ;
    protected void showLoading(LoadingDialogActivity.LoadingCases loadingCase, LoadingActionClick loadingActionClick) {
        switch (loadingCase){
            case show :{
                loading(loadingCase , loadingActionClick);
                loagin_loading_view.setVisibility(View.VISIBLE);
                login_fragments_container.setVisibility(View.GONE);

                break;
            }
            case hide:{
                login_fragments_container.setVisibility(View.VISIBLE);
                loagin_loading_view.setVisibility(View.GONE);
                break;
            }
            case fail: {

                loading(loadingCase , loadingActionClick);
                loagin_loading_view.setVisibility(View.VISIBLE);
                login_fragments_container.setVisibility(View.GONE);
                break;
            }
        }
    }


}
