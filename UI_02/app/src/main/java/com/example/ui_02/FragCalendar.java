package com.example.ui_02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragCalendar extends Fragment {
    private View view;

    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragCalendar newInstance(){
        FragCalendar fragCalendar = new FragCalendar();
        return fragCalendar;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_calendar,container,false);

        return view;
    }
}
