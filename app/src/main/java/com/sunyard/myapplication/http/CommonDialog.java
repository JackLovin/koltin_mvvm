package com.example.myapplication.http;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dahuatech.dl.common.R;

/**
 * @author 331209
 * @package com.dahuatech.common
 * @email chen_shengjie@dahuatech.com
 * @time 2022/8/26 15:59
 * @describe 通用对话框
 */
public class CommonDialog extends Dialog {

    private TextView tvDialogTitle;
    private TextView tvDialogContent;
    private Button btnDialogSure;
    private Button btnDialogCancel;
    private String title;
    private String content;
    private static com.dahuatech.common.CommonDialog instance;

    public static com.dahuatech.common.CommonDialog getInstance(Context context) {
        if (instance == null) {
            instance = new com.dahuatech.common.CommonDialog(context);
        }

        return instance;
    }

    public interface DialogBtnCallBack {

        void clickSure();

        void clickCancel();
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public void setContent(String content) {
        this.content = content;
    }

    DialogBtnCallBack dialogBtnCallBack;

    public void setClickLister(DialogBtnCallBack dialogBtnCallBack) {

        this.dialogBtnCallBack = dialogBtnCallBack;
    }

    public CommonDialog(@NonNull Context context) {
        super(context);
        if (title == null) {
            title = "提示";
        }
    }

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        //背景透明处理
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
//                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_common);
        tvDialogTitle = findViewById(R.id.tv_dialog_title);
        tvDialogContent = findViewById(R.id.tv_dialog_content);
        btnDialogSure = findViewById(R.id.btn_dialog_sure);
        btnDialogCancel = findViewById(R.id.btn_dialog_cancel);
        tvDialogTitle.setText(title);
        tvDialogContent.setText(content);
        btnDialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBtnCallBack.clickSure();
            }
        });
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBtnCallBack.clickCancel();
            }
        });
    }
}
