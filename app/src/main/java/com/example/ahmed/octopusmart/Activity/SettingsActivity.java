package com.example.ahmed.octopusmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.Activity.Base.BaseActivity;
import com.example.ahmed.octopusmart.Decorator.DividerItemDecoration;
import com.example.ahmed.octopusmart.Dialog.GenricDialogFragment.GenricDialogFragment;
import com.example.ahmed.octopusmart.Dialog.LanguageDialog;
import com.example.ahmed.octopusmart.Interfaces.GenericItemClickCallback;
import com.example.ahmed.octopusmart.R;
import com.example.ahmed.octopusmart.RecyclerAdapter.SettingsAdapter;
import com.example.ahmed.octopusmart.Util.LangUtils;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ahmed.octopusmart.Fragment.OrderStateFragment.OrderCases.history;

/**
 * Created by ahmed on 23/12/2017.
 */

public class SettingsActivity extends BaseActivity implements GenericItemClickCallback<String> {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private SettingsAdapter settingsAdapter;

    private LanguageDialog languageDialog;

    @BindView(R.id.activity_title)
    TextView activity_title ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
        activity_title.setText( getString(R.string.settings_text) );

        init();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);
    }


    private void init() {
        settingsAdapter = new SettingsAdapter(getSettings(), this, this);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this);
        dividerItemDecoration.setActivated(true);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(settingsAdapter);
    }


    public ArrayList<String> getSettings() {
        ArrayList<String> settings = new ArrayList<>();
        settings.add(getString(R.string.settings_language));
        settings.add(getString(R.string.settings_change_passowrd));
        settings.add(getString(R.string.settings_address));
        settings.add(getString(R.string.settings_phone));
        settings.add(getString(R.string.settings_email));
        return settings;
    }

    @Override
    public void onItemClicked(String item) {
        if(Objects.equals(item, getString(R.string.settings_address))){
            changeAddress();
        }
        else if(Objects.equals(item, getString(R.string.settings_change_passowrd))){
            changePassword();
        }
        else if(Objects.equals(item, getString(R.string.settings_language))){
            changeLanguage();
        }
        else if(Objects.equals(item, getString(R.string.settings_phone))){
            changePhone();
        }
        else if(Objects.equals(item, getString(R.string.settings_email))){
            changeEmail();
        }

    }

    private void changePassword() {
        Intent i = new Intent(this, PasswordActivity.class);
        startActivity(i);
    }

    private void changeLanguage() {
        final ArrayList<String> langs = new ArrayList<>();
        langs.add(getString(R.string.language_arabic));
        langs.add(getString(R.string.language_english));
        languageDialog = LanguageDialog.getInstance(
                langs,
                new GenricDialogFragment.GenricDialogFragmentClickListener<String>() {
                    @Override
                    public void onGenericDialogItemClicked(String child) {
                        if(Objects.equals(child, getString(R.string.language_arabic))){
                            LangUtils.changeLang(LangUtils.LANGUAGE_AR);
                        }
                        else if(Objects.equals(child, getString(R.string.language_english))){
                            LangUtils.changeLang(LangUtils.LANGUAGE_EN);
                        }

                        if(languageDialog != null){
                            languageDialog.dismiss();
                        }
                    }
                }
        );

        languageDialog.show(getSupportFragmentManager(), "lang");
    }

    private void changePhone() {
        Intent i = new Intent(this, ChangePhoneActivity.class);
        startActivity(i);
    }

    private void changeAddress() {
        Intent i = new Intent(this, ChooseAddressActivity.class);
        startActivity(i);
    }

    private void changeEmail(){
        Intent i = new Intent(this, EmailChangeActivity.class);
        startActivity(i);
    }
}
