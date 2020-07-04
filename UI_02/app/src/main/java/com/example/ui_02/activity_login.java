package com.example.ui_02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class activity_login extends AppCompatActivity {
    Button btn_sign,btn_login;
    EditText et_id,et_pw;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //강제적으로 네트워크 접속

        //회원가입 버튼을 눌렀을때 Activity_register 액티비티로 이동
        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_register.class);
                startActivity(intent);
            }
        });
            //로그인 버튼을 눌렀을때  MainActivity로 이동
            btn_login = (Button)findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_id = (EditText)findViewById(R.id.et_id);
                    et_pw = (EditText)findViewById(R.id.et_pw);
                    final String userID = et_id.getText().toString();
                    String userPassword = et_pw.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject=new JSONObject(response);
                                boolean success=jasonObject.getBoolean("success");
                                if (success) {//회원등록 성공한 경우
                                    String userID = jasonObject.getString("userID");
                                    String userPassword = jasonObject.getString("userPassword");
                                    Intent intent = new Intent(activity_login.this, MainActivity.class);
                                    intent.putExtra("log", "User");
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPassword",userPassword);
                                    startActivity(intent);
                                }
                                else{//회원등록 실패한 경우
                                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest=new LoginRequest(userID,userPassword,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(activity_login.this);
                    queue.add(loginRequest);
                }
            });
    }//onCreate 끝
}