package com.example.ahmed.octopusmart.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.ahmed.octopusmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ahmed on 20/12/2017.
 */

public class WriteReviewDialog extends DialogFragment {

    @BindView(R.id.dialog_write_review_cancel)
    View cancel;

    @BindView(R.id.dialog_write_review_confirm)
    View confirm;

    @BindView(R.id.dialog_write_review_input)
    EditText editText;

    private WriteReviewCallback callback;

    public interface WriteReviewCallback{
        void onReviewSubmitted(String review);
    }

    public static WriteReviewDialog getInstance() {
        return new WriteReviewDialog();
    }

    public void setCallback(WriteReviewCallback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_write_review, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT ;
        window.setAttributes(lp);
    }

    @OnClick(R.id.dialog_write_review_confirm)
    void submit(){
        if(callback != null){
            String s = "";
            try {
                s = editText.getEditableText().toString();
            }
            catch (Exception e){}

            callback.onReviewSubmitted(s);

            dismissAllowingStateLoss();
        }
    }

    @OnClick(R.id.dialog_write_review_cancel)
    void can(){
        dismissAllowingStateLoss();
    }
}
