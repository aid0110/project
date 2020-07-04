package com.example.ui_02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//프래그먼트 교체를 보여주는 처리를 구현
        switch (position){
            case 0:
                return FragSearch.newInstance();
            case 1:
                return FragBest.newInstance();
            case 2:
                return FragSetting.newInstance();
            default:
                return null;
        }
    }
//갯수 명시(포지션)
    @Override
    public int getCount() {
        return 3;
    }

    //상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Search";
            case 1:
                return "Best";
            case 2:
                return "Setting";
            default:
                return null;
        }
    }
}
