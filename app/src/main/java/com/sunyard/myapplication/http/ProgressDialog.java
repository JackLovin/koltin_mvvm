package com.example.myapplication.http;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

import com.example.myapplication.R;


public class ProgressDialog extends AppCompatDialog {

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public static class Builder {

        private ProgressDialog dialog;

        public Builder(Context context) {
            dialog = new ProgressDialog(context);
            dialog.getWindow().setDimAmount(0f);


        }

        public Builder noClose() {
            dialog.setCancelable(false);
            return this;
        }

        public Builder setOnDismissListener(final OnDismissListener listener) {
            dialog.setOnDismissListener(listener);
            return this;
        }

        public ProgressDialog get() {
            return dialog;
        }
    }



    public ProgressDialog(Context context) {
        this(context, R.style.customDialog);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.base_custrom_toast);
    }
}
