package com.example.ui_02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class review extends AppCompatActivity {
    ListView mlv_review_list;
    ArrayList<String> al;
    RatingBarArrayAdapter ratingArrayAdapter;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
       al = new ArrayList<String>();
       al.add("테스트1");//여기에 영양제 이름이 들어가면 됨(찜한 영양제 줌)
       al.add("테스트2");//여기에 영양제 이름이 들어가면 됨
       ratingArrayAdapter = new RatingBarArrayAdapter(this);
       mlv_review_list = (ListView)findViewById(R.id.lv_review_list);
       mlv_review_list.setAdapter(ratingArrayAdapter);
   }

    class RatingBarArrayAdapter extends ArrayAdapter<String> {
        Activity context;
        public RatingBarArrayAdapter(Context context) {
            super(context, R.layout.row, al);
            // TODO Auto-generated constructor stub
            this.context = (Activity)context;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row = context.getLayoutInflater().inflate(R.layout.row, null);
            TextView tv = (TextView)row.findViewById(R.id.textview_name);
            tv.setText(al.get(position).toString());
            return row;

        }

    }

}