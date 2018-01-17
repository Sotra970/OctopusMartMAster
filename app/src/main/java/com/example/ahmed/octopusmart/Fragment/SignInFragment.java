package com.example.ahmed.octopusmart.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity;
import com.example.ahmed.octopusmart.Activity.LoginActivity;
import com.example.ahmed.octopusmart.App.Appcontroler;
import com.example.ahmed.octopusmart.Interfaces.LoadingActionClick;
import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.Service.CallbackWithRetry;
import com.example.ahmed.octopusmart.Service.Injector;
import com.example.ahmed.octopusmart.Service.onRequestFailure;
import com.example.ahmed.octopusmart.Util.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.fail;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.hide;
import static com.example.ahmed.octopusmart.Activity.Base.LoadingDialogActivity.LoadingCases.show;

/**
 * Created by ahmed on 12/21/2017.
 */

public class SignInFragment extends BaseLoginFragment {

    @BindView(R.id.password_edit_text)
    TextInputEditText passwordEditText;
    @BindView(R.id.layout_password)
    TextInputLayout passwordLayout;
    @BindView(R.id.email_edit_text)
    TextInputEditText emailEditText;

    @BindView(R.id.layout_email)
    TextInputLayout emailLayout;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_signin, container, false);
            ButterKnife.bind(this,view);
        }

        return view;
    }

    public static SignInFragment getInstance() {
        SignInFragment signInFragment = new SignInFragment();
        return signInFragment;
    }
    @OnClick(R.id.login_btn)
    void login(){
        if (!validate()){
            return;
        }
        showLoading(show , null);
        Call<UserModel> call = Injector.Api().login(emailEditText.getText().toString() , passwordEditText.getText().toString() , Appcontroler.getInstance().getToken());
        call.enqueue(new CallbackWithRetry<UserModel>(call, new onRequestFailure() {
            @Override
            public void onFailure() {
               showLoading(fail, new LoadingActionClick() {
                    @Override
                    public void OnClick() {
                        login();
                    }
                });
            }
        }) {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    Appcontroler.getInstance().getPrefManager().storeUser(response.body());
                    getActivity().setResult(200);
                    getActivity().supportFinishAfterTransition();
                }else {
                   showLoading(hide , null);
                    if (response.code()==404){
                        emailLayout.setError(getString(R.string.not_valid));
                    }else if (response.code() == 403){
                        passwordEditText.setError(getString(R.string.not_valid));
                    }
                }
            }
        });
    }


    boolean validate(){
        if (Validation.isEditTextEmpty(emailEditText , emailLayout)) return false;
        if (Validation.isEditTextEmpty(passwordEditText , passwordLayout)) return false;
        return  true ;
    }

}
