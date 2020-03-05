package com.azizutku.hipo.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;

import com.azizutku.hipo.R;

public class MemberDetailsDialog implements DialogInterface {

    private Dialog mDialog;
    private Activity mActivity;

    private TextView mTxtName;
    private TextView mTxtAge;
    private TextView mTxtLocation;
    private TextView mTxtPosition;
    private TextView mTxtYears;
    private TextView mTxtGithub;
    private TextView mTxtButtonPositive;;

    public MemberDetailsDialog(Activity activity) {
        mActivity = activity;
        mDialog = new Dialog(activity);
        mDialog.setContentView(R.layout.dialog_member_details);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mTxtName = mDialog.findViewById(R.id.dialog_member_txt_name);
        mTxtAge = mDialog.findViewById(R.id.dialog_member_txt_age);
        mTxtLocation = mDialog.findViewById(R.id.dialog_member_txt_location);
        mTxtPosition = mDialog.findViewById(R.id.dialog_member_txt_position);
        mTxtName = mDialog.findViewById(R.id.dialog_member_txt_name);
        mTxtYears = mDialog.findViewById(R.id.dialog_member_txt_years);
        mTxtGithub = mDialog.findViewById(R.id.dialog_member_txt_github);
        mTxtButtonPositive = mDialog.findViewById(R.id.dialog_member_txt_positive_btn);

        mTxtButtonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    public MemberDetailsDialog setName(String name) {
        mTxtName.setText(name);
        return this;
    }

    public MemberDetailsDialog setAge(int age) {
        mTxtAge.setText(age + "");
        return this;
    }

    public MemberDetailsDialog setLocation(String location) {
        mTxtLocation.setText(location);
        return this;
    }

    public MemberDetailsDialog setPosition(String position) {
        mTxtPosition.setText(position);
        return this;
    }

    public MemberDetailsDialog setYears(int years) {
        mTxtYears.setText(years + "");
        return this;
    }
    public MemberDetailsDialog setGithub(final String github) {
        mTxtGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(mActivity, Uri.parse("https://github.com/".concat(github)));
            }
        });
        return this;
    }

    public MemberDetailsDialog setPositiveButton(CharSequence text, View.OnClickListener onClickListener) {
        mTxtButtonPositive.setVisibility(View.VISIBLE);
        mTxtButtonPositive.setText(text);
        mTxtButtonPositive.setOnClickListener(onClickListener);
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
