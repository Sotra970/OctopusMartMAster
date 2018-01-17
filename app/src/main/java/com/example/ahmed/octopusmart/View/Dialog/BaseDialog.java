package com.example.ahmed.octopusmart.View.Dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ahmed.octopusmart.R;


/**
 * Created by Ahmed on 9/17/2016.
 */
public abstract class BaseDialog extends DialogFragment {

    private OnDialogCancelCallback cancelCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(STYLE_NO_TITLE);
        View v = createDialogView();
        v.setBackgroundColor(
                ResourcesCompat.getColor(
                        getResources(),
                        R.color.white, null
                )
        );

        dialog.setContentView(v);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT ;
        window.setAttributes(lp);
    }

    protected abstract View createDialogView();

    public void cancel(){
        if(cancelCallback != null){
            cancelCallback.onDialogCancelled();
        }
        dismiss();
    }

    public void setCancelCallback(OnDialogCancelCallback cancelCallback) {
        this.cancelCallback = cancelCallback;
    }

    public OnDialogCancelCallback getCancelCallback() {
        return cancelCallback;
    }

    public interface OnDialogCancelCallback{
        void onDialogCancelled();
    }
}
