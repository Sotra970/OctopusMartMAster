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

public class ChooseAddressActivity extends BaseActivity{

    @OnClick(R.id.activity_back)
    void onBack(){
        finish();
    }

    @BindView(R.id.activity)
    View theView;

    @BindView(R.id.appt_number_edit_text)
    EditText appt_number_edit_text;

    @BindView(R.id.layout_appt_number)
    TextInputLayout layout_appt_number;


    @BindView(R.id.floor_edit_text)
    EditText floor_edit_text;

    @BindView(R.id.layout_floor)
    TextInputLayout layout_floor;



    @BindView(R.id.district_edit_text)
    EditText district_edit_text;

    @BindView(R.id.layout_district)
    TextInputLayout layout_district;


    @BindView(R.id.city_edit_text)
    EditText city_edit_text;

    @BindView(R.id.layout_city)
    TextInputLayout layout_city;


    @BindView(R.id.activity_title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        ButterKnife.bind(this);
        title.setText(R.string.shipping_address);
    }

    @OnClick(R.id.confirm)
    void confirm(){
        if(!validate()) return;
        Call<ResponseBody> call = Injector.Api().changeAddress(
                Appcontroler.getUserId(),
                get_address()
        );

        call.enqueue(new CallbackWithRetry<ResponseBody>(call, new onRequestFailure() {
            @Override
            public void onFailure() {
                confirm();
            }
        }) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    showLongToast(theView, R.string.address_added);
                    supportFinishAfterTransition();
                }
            }
        });

    }


    private boolean validate() {
        if (Validation.isEditTextEmpty(appt_number_edit_text, layout_appt_number))return false;
        if (Validation.isEditTextEmpty(floor_edit_text, layout_floor))return false;
        if (Validation.isEditTextEmpty(district_edit_text, layout_district))return false;
        return !Validation.isEditTextEmpty(city_edit_text, layout_city);
    }

    private  String get_address(){
        String prefix = " - " ;
        String address =  appt_number_edit_text.getText().toString().trim()
                +prefix
                +floor_edit_text.getText().toString().trim()
                +prefix
                +district_edit_text.getText().toString().trim()
                +prefix
                +city_edit_text.getText().toString().trim();
        return address ;
    }
}
