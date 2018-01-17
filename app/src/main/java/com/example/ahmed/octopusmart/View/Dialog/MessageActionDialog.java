package com.example.ahmed.octopusmart.View.Dialog;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed on 9/17/2016.
 */
public class MessageActionDialog extends BaseDialog{

    String action ;
    String title_string ;
    ActionClick actionClick ;
    boolean loading = true ;

    @BindView(R.id.dialog_action)
    Button dialog_action ;
    @BindView(R.id.dialog_header_text_view)
    TextView title ;
    @BindView(R.id.loading_dialog_container_view)
    View loading_dialog_container_view ;

    public  static  final  int SHOW = 0;
    public  static  final  int RETRY = 1 ;
    public  static  final  int HIDE = 2 ;


    public void setAction(String action) {
        this.action = action;
    }

    public void setTitle_string(String title_string) {
        this.title_string = title_string;
    }

    public void setDialog_action(Button dialog_action) {
        this.dialog_action = dialog_action;
    }

    public void setActionClick(ActionClick actionClick) {
        this.actionClick = actionClick;
    }
    private static MessageActionDialog mInstance;
    @NonNull
    public static synchronized MessageActionDialog getInstance( String title  , String action  , ActionClick actionClick) {
        MessageActionDialog messageActionDialog = new MessageActionDialog();
        messageActionDialog.title_string = title ;
        messageActionDialog.action = action ;
        messageActionDialog.actionClick = actionClick ;
        mInstance  = messageActionDialog ;
        return mInstance;
    }

    public static synchronized MessageActionDialog getInstance( String title  , String action  , boolean loading , ActionClick actionClick) {
        MessageActionDialog messageActionDialog = new MessageActionDialog();
        messageActionDialog.title_string = title ;
        messageActionDialog.action = action ;
        messageActionDialog.actionClick = actionClick ;
        messageActionDialog.loading = loading ;
        mInstance  = messageActionDialog ;
        return mInstance;
    }

    @Override
    protected View createDialogView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_action, null , false);
        ButterKnife.bind(this , v) ;
        title.setText(title_string);
        dialog_action.setText(action);
        dialog_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionClick.onClick(mInstance);
            }
        });
        loading(loading);
        return v;
    }


    public  void loading(boolean show){
        dialog_action.setVisibility(show ? View.GONE:View.VISIBLE);
        title.setVisibility(show ? View.GONE:View.VISIBLE);
        loading_dialog_container_view.setVisibility(show ? View.VISIBLE:View.GONE);
    }

    public  interface  ActionClick{
        void onClick(MessageActionDialog messageActionDialog);
    }



}
