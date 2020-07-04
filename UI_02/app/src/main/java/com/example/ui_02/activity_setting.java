package com.example.ui_02;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class activity_setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch sw_1 = (Switch)findViewById(R.id.switch1);//알림
        Switch sw_2 = (Switch)findViewById(R.id.switch2);//방해시간 설정
        Switch sw_3 = (Switch)findViewById(R.id.switch3);//진동
        sw_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치 버튼이 체크되었는지 검사하여 텍스트뷰에 각 경우에 맞게 출력합니다.
                if (isChecked){
                    Uri  notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),notification);
                    ringtone.play();
                }else{
                    Toast t =  Toast.makeText(getApplicationContext(),"해제",Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });

        sw_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast t =  Toast.makeText(getApplicationContext(),"방해금지 실행 끌때까지 지속됩니다.",Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });

        sw_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vib.vibrate(6000);
                }
                else{
                    Toast t =  Toast.makeText(getApplicationContext(),"해제",Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }
}