package com.example.ahmed.octopusmart.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
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

public class ChangePhoneActivity extends BaseActivity {

    @OnClick(R.id.activity_back)
    void onBack(){
        finish();
    }

    @BindView(R.id.activity_title)
    TextView title;

    @BindView(R.id.old_phone)
    EditText oldPhone;

    @BindView(R.id.activity)
    View theView;

    @BindView(R.id.new_phone)
    EditText newPhone;

    @BindView(R.id.pass_confirm_layout)
    TextInputLayout passLayout;

    @BindView(R.id.old_phone_layout)
    TextInputLayout oldPhoneLayout;

    @BindView(R.id.pass_confirm)
    EditText passEditText;

    @BindView(R.id.new_phone_layout)
    TextInputLayout newPhoneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);

        title.setText(R.string.change_phone);
    }

    @OnClick(R.id.confirm)
    void confirm(){
        if(validate()){
            String newP = "";
            String pass = "";
            try{
                newP = newPhone.getEditableText().toString();
                pass = passEditText.getEditableText().toString();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            if(!newP.isEmpty() && !pass.isEmpty()){
                long userId = Appcontroler.getUserId();
                Call<ResponseBody> call = Injector.Api().changePhone(
                        userId, newP, pass
                );

                showLoading(LoadingCases.show, null);

                call.enqueue(
                        new CallbackWithRetry<ResponseBody>(
                                call,
                                new onRequestFailure() {
                                    @Override
                                    public void onFailure() {
                                        showLoading(LoadingCases.fail,
                                                new LoadingActionClick() {
                                                    @Override
                                                    public void OnClick() {
                                                        confirm();
                                                    }
                                                });

                                    }
                                }
                        ) {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                showLoading(LoadingCases.hide, null);

                                if(response.isSuccessful()){
                                    showLongToast(theView, R.string.phone_changed);
                                    supportFinishAfterTransition();
                                }

                                else{
                                    // TODO: 1/21/2018 show fail reason i don't know all failure codes
                                }
                            }
                        }
                );
            }

        }

    }

    private boolean validate() {
        return !Validation.isEditTextEmpty(oldPhone, oldPhoneLayout) &&
                !Validation.isEditTextEmpty(newPhone, newPhoneLayout) &&
                !Validation.isEditTextEmpty(passEditText, passLayout);
    }
}
