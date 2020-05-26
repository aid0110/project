package com.example.ui_02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class activity_register extends AppCompatActivity {
    private EditText et_name;
    private EditText et_Id;
    private EditText et_Pw;
    private EditText et_Age;

    private Button btn_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_ok = (Button)findViewById(R.id.btn_ok);
        et_name = (EditText)findViewById(R.id.et_name);
        et_Id = (EditText) findViewById(R.id.et_id);
        et_Pw = (EditText) findViewById(R.id.et_pw);
        et_Age = (EditText) findViewById(R.id.et_age);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_Id.getText().toString();
                String userPassword = et_Pw.getText().toString();
                String userName = et_name.getText().toString();
                int userAge = Integer.parseInt(et_Age.getText().toString());


                //4. 콜백 처리부분(volley 사용을 위한 ResponseListener 구현 부분)

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    //서버로부터 여기서 데이터를 받음
                    @Override
                    public void onResponse(String response) {
                        try {
                            //서버로부터 받는 데이터는 JSON타입의 객체이다.

                            JSONObject jsonResponse = new JSONObject(response);

                            //그중 Key값이 “success”인 것을 가져온다.

                            boolean success = jsonResponse.getBoolean("success");

                            //회원 가입 성공시 success값이 true임

                            if(success){

                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                                //알림상자를 만들어서 보여줌
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity_register.this);
                                builder.setMessage("회원가입 성공")
                                .setPositiveButton("ok", null)
                                        .create()
                                        .show();
                                //그리고 첫화면으로 돌아감
                                Intent intent = new Intent(activity_register.this, activity_login.class);
                                activity_register.this.startActivity(intent);
                            }
                            //회원 가입 실패시 success값이 false임
                            else{
                                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                                //알림상자를 만들어서 보여줌
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity_register.this);
                                builder.setMessage("실패")
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };//responseListener 끝

                //volley 사용법
                //1. RequestObject를 생성한다. 이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
                //2. RequestQueue를 생성한다.
                RequestQueue queue = Volley.newRequestQueue(activity_register.this);
                //3. RequestQueue에 RequestObject를 넘겨준다.
                queue.add(registerRequest);
            }

        });
    }

}