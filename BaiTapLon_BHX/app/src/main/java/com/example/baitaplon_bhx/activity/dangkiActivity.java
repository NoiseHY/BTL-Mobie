package com.example.baitaplon_bhx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplon_bhx.R;

public class dangkiActivity extends AppCompatActivity {
    private EditText tendk, mkdk, hvt, sdt;
    private Button btDK;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);

        tendk = findViewById(R.id.tendangki);
        mkdk = findViewById(R.id.matkhau);
        hvt = findViewById(R.id.hvt);
        sdt = findViewById(R.id.sdt);
        btDK = findViewById(R.id.btDangKi);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE IF NOT EXISTS taikhoan ( tentk TEXT, mk TEXT, hvt TEXT, sdt TEXT, PRIMARY KEY(tentk) )";
            myDatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Lỗi", "Không thể tạo bảng: " + e.getMessage());
        }

        btDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tendk_dtb = tendk.getText().toString().trim();
                String mk_dtb = mkdk.getText().toString();
                String hvt_dtb = hvt.getText().toString();
                String sdt_dtb = sdt.getText().toString();

                if (validateInput(tendk_dtb, mk_dtb, hvt_dtb, sdt_dtb)) {
                    ContentValues myvalue = new ContentValues();
                    myvalue.put("tentk", tendk_dtb);
                    myvalue.put("mk", mk_dtb);
                    myvalue.put("hvt", hvt_dtb);
                    myvalue.put("sdt", sdt_dtb);

                    String msg = "";
                    try {
                        if (myDatabase.insertOrThrow("taikhoan", null, myvalue) != -1) {
                            msg = "Đăng kí tài khoản thành công!";
                        }
                    } catch (Exception e) {
                        msg = "Tên tài khoản hoặc mật khẩu sai";
                    }

                    Toast.makeText(dangkiActivity.this, msg, Toast.LENGTH_SHORT).show();
                    clearInputFields();
                }
            }
        });
    }

    private boolean validateInput(String tendk, String mk, String hvt, String sdt) {
        // Kiểm tra các trường nhập liệu trước khi thêm vào cơ sở dữ liệu
        if (tendk.isEmpty() || mk.isEmpty() || hvt.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        tendk.setText("");
        mkdk.setText("");
        hvt.setText("");
        sdt.setText("");
    }
}
