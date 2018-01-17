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

import butterknife.ButterKnife;

/**
 * Created by ahmed on 23/12/2017.
 */

public class ChangeAddressDialog extends DialogFragment {

    private ChangeAddressCallbacks changeAddressCallbacks;

    public interface ChangeAddressCallbacks{
        void onAddressChanged(String address);
    }

    public static ChangeAddressDialog getInstance(UserModel userModel,
                                                  ChangeAddressCallbacks changeAddressCallbacks)
    {
        ChangeAddressDialog dialog = new ChangeAddressDialog();
        dialog.changeAddressCallbacks = changeAddressCallbacks;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_addess, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}
