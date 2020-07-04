package com.example.ui_02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_modify extends AppCompatActivity {
    private TextView tv_id, tv_pw;
    private Button btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_pw = (TextView)findViewById(R.id.tv_pw);

        String userID = UserInfo.getUserID();
        String userPW = UserInfo.getUserPassword();
        tv_id.setText(userID);
        tv_pw.setText(userPW);

        btn_set = findViewById(R.id.btn_set);

        //종료버튼
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
