package com.example.arochta.technews.Controller;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * This class handles the process dialog and make a progress bar with a custom message.
 * Created by tal on 01-Aug-17.
 */

public class MyProgressBar{

    ProgressDialog dialog;

    public MyProgressBar(Context context){
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void setDialogMessage(String message){
        Log.d("pb", "message"+message);
        dialog.setMessage(message);
        Log.d("pb", "show");
        dialog.show();
        Log.d("pb", "end");
    }

    public void dismissDialog(){
        dialog.dismiss();
    }


}
