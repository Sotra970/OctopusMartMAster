package com.example.ahmed.octopusmart.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.example.ahmed.octopusmart.Util.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ahmed on 23/12/2017.
 */

public class EmailChangeActivity extends BaseActivity {

    @OnClick(R.id.activity_back)
    void onBack(){
        finish();
    }

    @BindView(R.id.activity)
    View theView;

    @BindView(R.id.activity_title)
    TextView title;

    @BindView(R.id.old_email)
    EditText oldEmail;
    @BindView(R.id.oldEmailLayout)
    TextInputLayout oldEmailLayout;

    @BindView(R.id.new_email)
    EditText newEmail;

    @BindView(R.id.new_email_layout)
    TextInputLayout newEmailLayout;

    @BindView(R.id.pass_confirm)
    EditText passEditText;

    @BindView(R.id.pass_confirm_layout)
    TextInputLayout passLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        ButterKnife.bind(this);

        title.setText(R.string.email_change);
    }

    @OnClick(R.id.confirm)
    void confirm(){
            if (!validate()) return;

            String newE = "";
            String pass = "";
            newE = newEmail.getEditableText().toString();
            pass = passEditText.getEditableText().toString();

            long userId = Appcontroler.getUserId();
            if(!newE.isEmpty() && !pass.isEmpty()){
                Call<ResponseBody> call =
                        Injector.Api().changeEmail(userId, newE, pass);

                call.enqueue(
                        new CallbackWithRetry<ResponseBody>(
                                call,
                                new onRequestFailure() {
                                    @Override
                                    public void onFailure() {
                                        confirm();
                                    }
                                }
                        ) {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()){
                                        if(response.isSuccessful()){
                                            showLongToast(theView, R.string.email_changed);
                                            supportFinishAfterTransition();
                                        }
                                        else{
                                            showLongToast(theView, R.string.toast_unexpected_error);
                                        }
                                    }
                            }
                        }
                );
            }
    }

    private boolean validate() {
        if (Validation.isEmailNotValid(newEmail,newEmailLayout)) return  false;
        if (Validation.isEmailNotValid(oldEmail,oldEmailLayout)) return false;
        if (Validation.isEditTextEmpty(passEditText,passLayout)) return false;
        return true ;
    }
}
