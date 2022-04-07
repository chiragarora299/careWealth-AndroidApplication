package com.example.carewellth;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;

public class LoadingDailog {
    Activity activity;
    AlertDialog dailog;

    LoadingDailog(Activity myActivity){
        activity= myActivity;
    }
    void startLoadingDailog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progess_dailog,null));
        builder.setCancelable(true);

        dailog= builder.create();
        dailog.show();
    }

    void dismissDailog(){
        dailog.dismiss();
    }
}

