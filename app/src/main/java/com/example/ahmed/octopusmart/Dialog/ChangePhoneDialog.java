package com.example.ahmed.octopusmart.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ahmed on 23/12/2017.
 */

public class ChangePhoneDialog extends DialogFragment {

    public interface PhoneDialogCallbacks{
        void onPhoneChanged(String phone);
        void onPhoneAdded(String phone);
    }

    private PhoneDialogCallbacks phoneDialogCallbacks;

    public static ChangePhoneDialog getInstance(UserModel userModel, PhoneDialogCallbacks callbacks) {
        ChangePhoneDialog dialog =  new ChangePhoneDialog();
        dialog.phoneDialogCallbacks = callbacks;
        return dialog;
    }

    @OnClick(R.id.dialog_confirm)
    void confirm(){

    }

    @OnClick(R.id.dialog_cancel)
    void cancel(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_change_phone, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}
