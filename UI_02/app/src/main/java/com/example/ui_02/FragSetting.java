package com.example.ui_02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragSetting extends Fragment {//extends AppCompatActivity
    private View view;

    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragSetting newInstance(){
        FragSetting fragSetting = new FragSetting();
        return fragSetting;
    }
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag_setting,container,false);

      return view;
    }

}
