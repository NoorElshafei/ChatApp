package com.example.chatapp1.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class Dialog {
    private static ProgressDialog dialog;


    public static void showDialog(Context context, String Message){
        //dialog progress
        dialog = new ProgressDialog(context);
        dialog.setMessage(Message);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        //
    }
    public static void dismiss(){
        dialog.dismiss();
    }

}
