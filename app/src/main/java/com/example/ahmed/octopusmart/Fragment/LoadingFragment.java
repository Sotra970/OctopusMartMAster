package com.example.ahmed.octopusmart.Fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.fail;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.show;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends DialogFragment {

    private static LoadingFragment mInstance;
    LoadingDialogActivity.LoadingCases loadingCase ; 
    public static synchronized LoadingFragment getInstance(LoadingDialogActivity.LoadingCases loadingCase , LoadingActionClick actionClick) {
        if (mInstance == null){
            mInstance = new LoadingFragment();
        }
        mInstance.actionClick = actionClick ;
        mInstance.loadingCase = loadingCase ;
        return mInstance;
    }

    LoadingActionClick actionClick ;
    public LoadingFragment() {
        // Required empty public constructor
    }


//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//    }


    View view ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            if (container != null) {
                container.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.fragment_loading, container, false);
        } catch (InflateException e) {

        }


            ButterKnife.bind(this,view);
            setup();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NO_FRAME,android.R.style.Theme_Holo_Light);
        super.onCreate(savedInstanceState);
    }

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

    @Override
    public void onStart() {
        super.onStart();
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        Window window = getDialog().getWindow();
//        lp.copyFrom(window.getAttributes());
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT ;
//        window.setAttributes(lp);
    }




}
