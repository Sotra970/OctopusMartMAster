package com.example.ahmed.octopusmart.Dialog;

import android.widget.TextView;

import com.example.ahmed.octopusmart.Dialog.GenricDialogFragment.GenricDialogFragment;

import java.util.ArrayList;

/**
 * Created by ahmed on 24/11/2017.
 */

public class LanguageDialog extends GenricDialogFragment<String> {

    static  public LanguageDialog getInstance(ArrayList<String> data,
                                              GenricDialogFragment.GenricDialogFragmentClickListener<String> clickListener)
    {
        LanguageDialog languageDialog = new LanguageDialog();
        languageDialog.setData(data);
        languageDialog.setAddressListClickListener(clickListener);
        return languageDialog ;
    }

    @Override
    public void GenericDrawText(TextView textView, String object) {
        textView.setText(object);
    }
}
