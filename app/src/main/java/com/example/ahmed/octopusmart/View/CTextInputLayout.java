package com.example.ahmed.octopusmart.View;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Ahmed on 10/27/2017.
 */

public class CTextInputLayout extends TextInputLayout {
    public CTextInputLayout(Context context) {
        super(context);
    }

    public CTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getBaseline() {
        EditText editText = getEditText();
        if(editText != null){
            return getMeasuredHeight() - (editText.getMeasuredHeight() - editText.getBaseline());
        }
        return super.getBaseline();
    }
}
