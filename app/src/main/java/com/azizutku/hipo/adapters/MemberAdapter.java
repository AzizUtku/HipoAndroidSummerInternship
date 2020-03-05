package com.azizutku.hipo.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azizutku.hipo.R;
import com.azizutku.hipo.models.Member;
import com.azizutku.hipo.views.MemberDetailsDialog;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Member> mMembers;
    private Activity mActivity;

    public MemberAdapter(Activity activity, ArrayList<Member> members) {
        mMembers = members;
        mActivity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View memberView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        MemberHolder holder = new MemberHolder(memberView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Member member = mMembers.get(position);

        MemberHolder memberHolder = (MemberHolder) holder;
        memberHolder.setName(member.getName());

        memberHolder.rltMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberDetailsDialog memberDetailsDialog = new MemberDetailsDialog(mActivity);
                memberDetailsDialog.setName(member.getName());
                memberDetailsDialog.setAge(member.getAge());
                memberDetailsDialog.setLocation(member.getLocation());
                memberDetailsDialog.setPosition(member.getHipo().getPosition());
                memberDetailsDialog.setYears(member.getHipo().getYearsInHipo());
                memberDetailsDialog.setGithub(member.getGithub());
                memberDetailsDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MemberHolder extends RecyclerView.ViewHolder {


        public RelativeLayout rltMain;

        private TextView mTxtName;
        private ImageView mImgDetails;

        public MemberHolder(@NonNull View itemView) {
            super(itemView);

            mTxtName = itemView.findViewById(R.id.item_member_txt_name);
            mImgDetails = itemView.findViewById(R.id.item_member_img_details);
            rltMain = itemView.findViewById(R.id.item_member_rlt_main);

        }

        public void setName(String name) {
            mTxtName.setText(name);
        }
    }
}
