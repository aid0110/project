package com.example.ui_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragHome extends Fragment {//extends AppCompatActivity
    private View view;

    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragHome newInstance(){
        FragHome fragHome = new FragHome();
        return fragHome;
    }
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag_home,container,false);

      return view;
    }

}
