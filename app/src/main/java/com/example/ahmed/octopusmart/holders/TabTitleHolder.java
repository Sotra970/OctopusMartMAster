package com.example.ahmed.octopusmart.holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.octopusmart.R;


/**
 * Created by Sotraa on 6/13/2016.
 */
public class TabTitleHolder {
    private final View view;
    LayoutInflater inflater ;
    ImageView tabImage ;
    TextView title ;
    Context context ;
    public TabTitleHolder(String text  , int icon , Context context){
        this.context = context ;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.tab_item, null);
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        view.setLayoutParams(new ViewGroup.LayoutParams(new Spec(context).WindowRec().x, ViewGroup.LayoutParams.MATCH_PARENT));
          title = view.findViewById(R.id.tab_tile);
        title.setText(text);

        tabImage = view.findViewById(R.id.tab_iccon);
        tabImage.setImageResource(icon);
    }
    public View getView (){
        return view;
    }

    public  void select(){
        title.setTextColor(ContextCompat.getColor(context , R.color.colorAccent));
        tabImage.setColorFilter(ContextCompat.getColor(context , R.color.colorAccent));
    }


    public  void unSelect(){
        title.setTextColor(ContextCompat.getColor(context , R.color.colorPrimary));
        tabImage.setColorFilter(ContextCompat.getColor(context , R.color.colorPrimary));
    }
}
