package com.azizutku.hipo.views;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.azizutku.hipo.R;
import com.azizutku.hipo.interfaces.OnSortButtonClickedListener;
import com.azizutku.hipo.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SortDialog implements DialogInterface {

    private Activity mActivity;

    private Button[] mButtons = new Button[3];
    private boolean[] mSelectedStates = new boolean[3];
    private int mSelectedState = -1;
    private boolean isAscending = true;

    private BottomSheetDialog mBottomSheetDialog;


    public SortDialog(Activity activity, final OnSortButtonClickedListener onSortButtonClickedListener) {
        mActivity = activity;

        View bottomSheetView =  LayoutInflater.from(activity).inflate(R.layout.bottom_sheet_sort, null, false);
        mBottomSheetDialog = new BottomSheetDialog(activity);
        mBottomSheetDialog.setContentView(bottomSheetView);

        final ImageView imgSortBy = bottomSheetView.findViewById(R.id.bottom_sheet_img_sort_by);
        TextView txtClean = bottomSheetView.findViewById(R.id.bottom_sheet_txt_clean);
        Button btnSortByName = bottomSheetView.findViewById(R.id.bottom_sheet_btn_name);
        Button btnSortByAge = bottomSheetView.findViewById(R.id.bottom_sheet_btn_age);
        Button btnSortByLocation = bottomSheetView.findViewById(R.id.bottom_sheet_btn_location);

        imgSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAscending = !isAscending;

                if (mSelectedState >= 0) {
                    onSortButtonClickedListener.onSortButtonClicked(mSelectedState, isAscending);
                }

                if (isAscending) {
                    imgSortBy.setImageResource(R.drawable.ic_ascending);
                } else {
                    imgSortBy.setImageResource(R.drawable.ic_descending);
                }
            }
        });

        txtClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedState = -1;
                onSortButtonClickedListener.onSortButtonClicked(Constants.SORT_CLEAR, isAscending);
                isAscending = true;
                imgSortBy.setImageResource(R.drawable.ic_ascending);

                for (int i = 0; i < mButtons.length; i++) {
                    mSelectedStates[i] = false;
                    changeButtonState(mButtons[i], i, mSelectedStates[i]);
                }
            }
        });

        mButtons = new Button[] { btnSortByName, btnSortByAge, btnSortByLocation };

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get tag of view
                // Tag should be in range 0..2
                int tag = Integer.parseInt(v.getTag().toString());
                onSortButtonClickedListener.onSortButtonClicked(tag, isAscending);

                for (int i = 0; i < mButtons.length; i++) {
                    if (tag != i) {
                        mSelectedStates[i] = false;
                        changeButtonState(mButtons[i], i, mSelectedStates[i]);
                    } else {
                        mSelectedState = i;
                        mSelectedStates[i] = true;
                        changeButtonState(mButtons[i], i, mSelectedStates[i]);
                    }
                }
            }
        };

        for (Button button : mButtons) {
            button.setOnClickListener(onClickListener);
        }
    }

    private void changeButtonState(Button button, int tag, boolean selected) {
        if (selected) {
            mButtons[tag].setBackground(mActivity.getResources().getDrawable(R.drawable.bg_btn_sort_selected));
            mButtons[tag].setTextColor(mActivity.getResources().getColor(R.color.colorWhite));

        } else {
            mButtons[tag].setBackground(mActivity.getResources().getDrawable(R.drawable.bg_btn_sort_unselected));
            mButtons[tag].setTextColor(mActivity.getResources().getColor(R.color.colorBlack));
        }
    }

    public void show() {
        mBottomSheetDialog.show();
    }

    public void hide() {
        mBottomSheetDialog.hide();
    }

    @Override
    public void cancel() {
        mBottomSheetDialog.cancel();
    }

    @Override
    public void dismiss() {
        mBottomSheetDialog.dismiss();
    }
}
