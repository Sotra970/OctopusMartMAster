package com.example.ahmed.octopusmart.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SignUpFragment extends BaseLoginFragment {

    @BindView(R.id.name_edit_text)
    TextInputEditText nameEditText;

    @BindView(R.id.layout_name)
    TextInputLayout nameLayout;

    @BindView(R.id.repeat_password_edit_text)
    TextInputEditText repeatPasswordEditText;

    @BindView(R.id.layout_repeat_password)
    TextInputLayout repeatPasswordLayout;

    @BindView(R.id.phone_edit_text)
    TextInputEditText phoneEditText;

    @BindView(R.id.layout_phone)
    TextInputLayout phoneLayout;


    @BindView(R.id.password_edit_text)
    TextInputEditText passwordEditText;
    @BindView(R.id.layout_password)
    TextInputLayout passwordLayout;

    @BindView(R.id.email_edit_text)
    TextInputEditText emailEditText;
    @BindView(R.id.layout_email)
    TextInputLayout emailLayout;



    View view;



    public static SignUpFragment getInstance() {
        SignUpFragment signUpFragment = new SignUpFragment();
        return signUpFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null)
        {
            view = inflater.inflate(R.layout.fragment_signup,container,false);
            ButterKnife.bind(this,view);


        }

        return view;
    }


    @OnClick(R.id.reg_btn)
    void login(){
        UserModel userModel = validate();
        if (null ==userModel){
            return;
        }
        showLoading(show , null);
        Call<UserModel> call = Injector.Api().register(userModel);
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
                    if (response.code()==409){
                        emailLayout.setError(getString(R.string.already_exist));
                    }else if (response.code() == 410){
                        phoneLayout.setError(getString(R.string.already_exist));
                    }
                }
            }
        });
    }

    UserModel validate(){
        if (Validation.isEditTextEmpty(nameEditText , nameLayout)) return null;
        if (Validation.isEmailNotValid(emailEditText , emailLayout)) return null;
        if (Validation.isEditTextEmpty(passwordEditText , passwordLayout)) return null;
        if (Validation.validatePasswordMatch(repeatPasswordEditText , repeatPasswordLayout , passwordEditText)) return null;
        if (Validation.validatePhone(phoneEditText , phoneLayout)) return null;

        UserModel userModel = new UserModel();
        userModel.setName(nameEditText.getText().toString());
        userModel.setEmail(emailEditText.getText().toString());
        userModel.setPassword(passwordEditText.getText().toString());
        userModel.setPhone(phoneEditText.getText().toString());
        userModel.setToken(Appcontroler.getInstance().getToken());
        return  userModel ;
    }
}
