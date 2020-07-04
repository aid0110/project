package com.example.ui_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class FragBest extends Fragment {//extends AppCompatActivity
    private View view;
    private ChipGroup cgSort;
    private Chip Fatigue, Memory, Immunity;//피로, 기억력, 면역력
    private Button btnApply;
    private TextView tvResult;
    //선택 chip 저장
    private ArrayList<String> selectedChipData;

    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragBest newInstance(){
        FragBest fragHome = new FragBest();
        return fragHome;
    }
    @Nullable

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag_best,container,false);
        cgSort = view.findViewById(R.id.cgSort); //칩그룹
        tvResult = view.findViewById(R.id.tvResult);//결과

    /*    cgSort.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                if (chipGroup.getCheckedChipId() == R.id.Fatigue) {
                    Toast.makeText(getActivity(), "chip is " + Fatigue.getText(), Toast.LENGTH_SHORT).show();
                } else if (chipGroup.getCheckedChipId() == R.id.Memory) {
                    Toast.makeText(getActivity(), "chip is " + Memory.getText(), Toast.LENGTH_SHORT).show();
                } else if (chipGroup.getCheckedChipId() == R.id.Immunity) {
                    Toast.makeText(getActivity(), "chip is " + Immunity.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        Fatigue = view.findViewById(R.id.Fatigue);//피로
        Memory = view.findViewById(R.id.Immunity);//기억력
        Immunity = view.findViewById(R.id.Memory);//면역력

        Fatigue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tvResult.append("Fatigue");
                Toast.makeText(getActivity(), "Fatigue is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.append("Memory");
                Toast.makeText(getActivity(), "Memory is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Immunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.append("Immunity");
                Toast.makeText(getActivity(), "Immunity is Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        selectedChipData = new ArrayList<>();
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    selectedChipData.add(compoundButton.getText().toString());
                }
                else{
                    selectedChipData.remove(compoundButton.getText().toString());
                }
            }
        };

        btnApply = view.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_combination.class);
                intent.putExtra("data",selectedChipData.toString());
                startActivity(intent);
            }
        });
        return view;
    }

}
