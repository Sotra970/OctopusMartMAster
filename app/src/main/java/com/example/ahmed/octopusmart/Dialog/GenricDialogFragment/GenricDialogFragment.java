package com.example.ahmed.octopusmart.Dialog.GenricDialogFragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.ahmed.octopusmart.Decorator.DividerItemDecoration;
import com.example.ahmed.octopusmart.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class  GenricDialogFragment<T> extends DialogFragment {

    ArrayList<T> data  = new ArrayList<>();

    GenricDialogFragmentClickListener<T> addressListClickListener ;

    public void setAddressListClickListener(GenricDialogFragmentClickListener addressListClickListener) {
        this.addressListClickListener = addressListClickListener;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public GenricDialogFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.address_listview)
    RecyclerView listView ;
    GenricDialogArrayAdapter<T> genricDialogArrayAdapter;
    public abstract void GenericDrawText(TextView textView, T object);




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(STYLE_NO_TITLE);
           View v =  LayoutInflater.from(getActivity()).inflate(R.layout.fragment_address, null, false);
            ButterKnife.bind(this , v) ;
            if (savedInstanceState !=null)
                data = (ArrayList<T>) savedInstanceState.getSerializable("data");
            setup();
        dialog.setContentView(v);
        return dialog;
    }



    void setup(){

        genricDialogArrayAdapter = new GenricDialogArrayAdapter<T>(getContext() , data) {
            @Override
            public void drawText(TextView textView, T object) {
                GenericDrawText(textView,object);
            }
        };
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext());
        dividerItemDecoration.setActivated(true);
        listView.addItemDecoration(dividerItemDecoration);

        genricDialogArrayAdapter.setGenricDialogArrayAdapterClickListenr(addressListClickListener);
        listView.setAdapter(genricDialogArrayAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT ;
        window.setAttributes(lp);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data" , data);
    }

    public  interface GenricDialogFragmentClickListener<T> {
        void onGenericDialogItemClicked(T child) ;
    }
}
