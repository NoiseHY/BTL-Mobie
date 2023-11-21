package com.example.baitaplon_bhx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.baitaplon_bhx.R;

public class QuanlyActivity extends AppCompatActivity {
    private Button btqltk, btqlhh, btqlloaihh, btqlncc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly);

        btqlhh = findViewById(R.id.btHH);
        btqltk = findViewById(R.id.btTk);
        btqlloaihh = findViewById(R.id.btLoaiHH);
        btqlncc = findViewById(R.id.btNCC);

        btqltk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(QuanlyActivity.this, qltkActivity.class);
                startActivity(it);
            }
        });
        btqlhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(QuanlyActivity.this, qlhhActivity.class);
                startActivity(it);
            }
        });
        btqlloaihh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(QuanlyActivity.this, qlloaihhActivity.class);
                startActivity(it);
            }
        });
        btqlncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(QuanlyActivity.this, qlnccActivity.class);
                startActivity(it);
            }
        });
    }
}