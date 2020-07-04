package com.example.ui_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private FragmentPagerAdapter fragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //뷰페이저 세팅
        ViewPager viewPager = findViewById(R.id.viewpager);
        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);//뷰페이저에다 프래그먼트어댑터를 연동해주고

        //탭레이아웃도 껴주기(하면 뷰페이저 세팅 완료)
        tabLayout.setupWithViewPager(viewPager);

        //로그인 액티비티에서 값을 가져오는 부분
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");

        UserInfo.setUserID(userID);
        UserInfo.setUserPassword(userPassword);

    }
}
