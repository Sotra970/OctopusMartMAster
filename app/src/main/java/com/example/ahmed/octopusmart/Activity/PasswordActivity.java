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

public class PasswordActivity extends BaseActivity {

    @OnClick(R.id.activity_back)
    void onBack(){
        finish();
    }

    @BindView(R.id.activity)
    View theView;

    @BindView(R.id.activity_title)
    TextView title;

    @BindView(R.id.new_pass)
    EditText newPass;

    @BindView(R.id.old_pass)
    EditText oldPass;

    @BindView(R.id.pass_confirm)
    EditText confirmPass;

    @BindView(R.id.new_pass_layout)
    TextInputLayout newPassLayout;

    @BindView(R.id.old_pass_layout)
    TextInputLayout oldPassLayout;

    @BindView(R.id.pass_confirm_layout)
    TextInputLayout confirmPassLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);

        title.setText(R.string.change_pass);
    }

    @OnClick(R.id.confirm)
    void confirm(){
        if(validate()){
            String oldP = "";
            String newP = "";
            try{
                oldP = oldPass.getEditableText().toString();
                newP = newPass.getEditableText().toString();
            }

            catch (Exception e){
                e.printStackTrace();
            }

            long userId = Appcontroler.getUserId();

            Call<ResponseBody> call =
                    Injector.Api().changePassword(userId, oldP, newP);

            showLoading(LoadingCases.show, null);

            call.enqueue(
                    new CallbackWithRetry<ResponseBody>(
                            call,
                            new onRequestFailure() {
                                @Override
                                public void onFailure() {
                                    showLoading(LoadingCases.show,
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
                                showLongToast(theView, R.string.password_changed);
                                supportFinishAfterTransition();
                            }

                            else{
                                showLongToast(theView, R.string.password_change_error);
                            }
                        }
                    }
            );
        }
    }

    private boolean validate() {
        return !Validation.isEditTextEmpty(oldPass, oldPassLayout) &&
                !Validation.isEditTextEmpty(newPass, newPassLayout) &&
                !Validation.isEditTextEmpty(confirmPass, confirmPassLayout) &&
                Validation.isPasswordsTheSame(newPass, confirmPassLayout, confirmPass);

    }
}
