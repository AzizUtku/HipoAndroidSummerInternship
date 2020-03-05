package com.azizutku.hipo.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azizutku.hipo.R;

public class MessageDialog implements DialogInterface {

    private Context mContext;
    private Dialog mDialog;

    private TextView mTxtTitle;
    private TextView mTxtMessage;
    private TextView mTxtButtonPositive;
    private TextView mTxtButtonNegative;
    private RelativeLayout mRltHeader;
    private ImageView mImgHeader;

    public MessageDialog(Context context) {
        mContext = context;
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_message);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mTxtTitle = mDialog.findViewById(R.id.dialog_txt_title);
        mTxtMessage = mDialog.findViewById(R.id.dialog_txt_message);
        mTxtButtonPositive = mDialog.findViewById(R.id.dialog_txt_positive_btn);
        mTxtButtonNegative = mDialog.findViewById(R.id.dialog_txt_negative_btn);
        mRltHeader = mDialog.findViewById(R.id.dialog_rlt_header);
        mImgHeader = mDialog.findViewById(R.id.dialog_img_header);
    }

    public MessageDialog setTitle(String title) {
        mTxtTitle.setText(title);
        return this;
    }

    public MessageDialog setMessage(String message) {
        mTxtMessage.setText(message);
        return this;
    }

    public MessageDialog setHeaderColor(int color) {
        mRltHeader.setBackgroundColor(color);
        return this;
    }

    public MessageDialog setHeaderImage(int resId) {
        mImgHeader.setImageResource(resId);
        return this;
    }


    public MessageDialog setPositiveButton(CharSequence text, View.OnClickListener onClickListener) {
        mTxtButtonPositive.setVisibility(View.VISIBLE);
        mTxtButtonPositive.setText(text);
        mTxtButtonPositive.setOnClickListener(onClickListener);
        return this;
    }

    public MessageDialog setNegativeButton(CharSequence text, View.OnClickListener onClickListener) {
        mTxtButtonNegative.setVisibility(View.VISIBLE);
        mTxtButtonNegative.setText(text);
        mTxtButtonNegative.setOnClickListener(onClickListener);
        return this;
    }

    public MessageDialog setPositiveButton(CharSequence text) {
        mTxtButtonPositive.setVisibility(View.VISIBLE);
        mTxtButtonPositive.setText(text);
        mTxtButtonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }

    public MessageDialog setNegativeButton(CharSequence text) {
        mTxtButtonNegative.setVisibility(View.VISIBLE);
        mTxtButtonNegative.setText(text);
        mTxtButtonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }


    public MessageDialog setOnCancelListener(OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
        return this;
    }

    public MessageDialog setOnDismissListener(OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
        return this;
    }

    public MessageDialog setOnShowListener(OnShowListener listener) {
        mDialog.setOnShowListener(listener);
        return this;
    }

    public MessageDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public MessageDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public void show(){
        mDialog.show();
    }


    @Override
    public void cancel() {
        mDialog.cancel();
    }

    @Override
    public void dismiss() {
        mDialog.dismiss();
    }

}
