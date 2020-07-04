package com.example.ui_02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoseAdapter extends RecyclerView.Adapter<DoseAdapter.CustomViewHolder> {
    private ArrayList<DoseInfo> mList = null;
    private Activity acontext = null;

    public DoseAdapter(Activity context,ArrayList<DoseInfo> list){
        this.acontext = context;
        this.mList = list;
    }
    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView d_code, userID, d_date, n_code,n_name;

        public CustomViewHolder(View view) {
            super(view);
            this.d_code= (TextView)view.findViewById(R.id.textView_d_code);
            this.userID = (TextView)view.findViewById(R.id.textView_userID);
            this.d_date = (TextView)view.findViewById(R.id.textView_d_date);
            this.n_code = (TextView)view.findViewById(R.id.textView_n_code);
            this.n_name = (TextView)view.findViewById(R.id.textView_n_name);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mylist, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.d_code.setText(mList.get(position).getD_code());
        holder.userID.setText(mList.get(position).getUserID());
        holder.d_date.setText(mList.get(position).getD_date());
        holder.n_code.setText(mList.get(position).getN_code());
        holder.n_name.setText(mList.get(position).getN_name());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}