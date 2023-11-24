package com.example.baitaplon_bhx.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.baitaplon_bhx.R;
import com.example.baitaplon_bhx.adapter.AdapterViewPager;
import com.example.baitaplon_bhx.fragment.CaidatFragment;
import com.example.baitaplon_bhx.fragment.GiohangFragment;
import com.example.baitaplon_bhx.fragment.TrangchuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class MainMenu extends AppCompatActivity {

    private boolean isScrolling = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewPager2 page_main;
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        BottomNavigationView bottom_nav;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        page_main = findViewById(R.id.page_main);
        bottom_nav = findViewById(R.id.bottom_nav);

        fragmentArrayList.add(new TrangchuFragment());
        fragmentArrayList.add(new GiohangFragment());
        fragmentArrayList.add(new CaidatFragment());

        AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);
        // set adapter
        page_main.setAdapter(adapterViewPager);
        page_main.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottom_nav.setSelectedItemId(R.id.ithome);
                        break;
                    case 1:
                        bottom_nav.setSelectedItemId(R.id.itGiohang);
                        break;
                    case 2:
                        bottom_nav.setSelectedItemId(R.id.itCaidat);
                        break;
                }
                super.onPageSelected(position);
            }
        });
//        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.ithome) {
//                    page_main.setCurrentItem(0);
//                } else if (item.getItemId() == R.id.itGiohang) {
//                    page_main.setCurrentItem(1);
//                } else if (item.getItemId() == R.id.itCaidat) {
//                    page_main.setCurrentItem(2);
//                }
//                return true;
//            }
//        });
        // Trong phần xử lý của setOnNavigationItemSelectedListener trong MainMenu
        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.ithome) {
                    page_main.setCurrentItem(0);
                } else if (item.getItemId() == R.id.itGiohang) {
                    page_main.setCurrentItem(1);
                } else if (item.getItemId() == R.id.itCaidat) {
                    page_main.setCurrentItem(2);

                    // Nhận dữ liệu từ Intent
                    String tenTK = getIntent().getStringExtra("tenTK");

                    // Tạo Bundle để chuyển dữ liệu đến fragment CaidatFragment
                    Bundle bundle = new Bundle();
                    bundle.putString("tenTK", tenTK);

                    // Lấy instance của CaidatFragment từ ViewPager2
                    Fragment caidatFragment = adapterViewPager.createFragment(2);

                    if (caidatFragment instanceof CaidatFragment) {
                        // Chuyển dữ liệu qua fragment CaidatFragment
                        caidatFragment.setArguments(bundle);
                    }
                }
                return true;
            }
        });


    }


}