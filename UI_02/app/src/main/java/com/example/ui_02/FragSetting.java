package com.example.ui_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragSetting extends Fragment {
    private View view;
    private Button btn_modify;//내정보 수정 버튼
    private Button btn_tonic;//내 영양제 버튼
    private Button btn_review;//리뷰 버튼 -> 찜으로 바꿔야함
    private Button btn_inquiry;//문의하기 버튼
    private Button btn_setting;//앱 설정 버튼

    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragSetting newInstance(){
        FragSetting fragSetting = new FragSetting();
        return fragSetting;
    }
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag_setting,container,false);
        btn_modify = view.findViewById(R.id.btn_modify);//내정보 수정버튼
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),activity_modify.class);
                startActivity(intent);
            }
        });
        btn_tonic = view.findViewById(R.id.btn_tonic);//내 복용관리 /찜이랑 다름
        btn_tonic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),activity_tonic.class);
                startActivity(intent);
            }
        });
        btn_review = view.findViewById(R.id.btn_review);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), review.class);
                startActivity(intent);
            }
        });

        btn_inquiry = view.findViewById(R.id.btn_inquiry);
        btn_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                // email setting 배열로 해놔서 복수 발송 가능
                String[] address = {"hgw0105036@naver.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT,"hgw01050360586@gmail.com");
                email.putExtra(Intent.EXTRA_TEXT,"문의할 내용이 있습니다.");
                startActivity(email);
            }
        });

        btn_setting = view.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity_setting.class);
                startActivity(intent);
            }
        });
      return view;
    }

}
