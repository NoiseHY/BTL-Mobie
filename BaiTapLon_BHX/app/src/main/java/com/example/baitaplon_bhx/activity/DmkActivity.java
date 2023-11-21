package com.example.baitaplon_bhx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplon_bhx.R;

public class DmkActivity extends AppCompatActivity {
    private EditText doitentk, doimktk, xacnhandoimk, mkmoi;
    private Button btdoi;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmk);

        doimktk = findViewById(R.id.doimatkhautk);
        doitentk = findViewById(R.id.doitentk);
        xacnhandoimk = findViewById(R.id.xacnhandoimk);
        mkmoi = findViewById(R.id.mkmoi);

        btdoi = findViewById(R.id.btdoimk);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE IF NOT EXISTS taikhoan ( tentk TEXT, mk TEXT, hvt TEXT, sdt TEXT, PRIMARY KEY(tentk) )";
            myDatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Lỗi", "Không thể tạo bảng: " + e.getMessage());
        }

        btdoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentk = doitentk.getText().toString();
                String mktkCu = doimktk.getText().toString();
                String xacnhanmk = xacnhandoimk.getText().toString();
                String nhapmkmoi = mkmoi.getText().toString();

                if (validateInput(tentk, mktkCu, xacnhanmk, nhapmkmoi)) {
                    // Kiểm tra mật khẩu cũ trước khi đổi mật khẩu
                    if (performLogin(tentk, mktkCu)) {
                        ContentValues myvalue = new ContentValues();
                        myvalue.put("mk", xacnhanmk);

                        // Update mật khẩu trong cơ sở dữ liệu
                        int rowsAffected = myDatabase.update("taikhoan", myvalue, "tentk=?", new String[]{tentk});

                        String msg;
                        if (rowsAffected > 0) {
                            msg = "Đổi mật khẩu thành công!";
                        } else {
                            msg = "Đổi mật khẩu thất bại. Kiểm tra lại mật khẩu cũ.";
                        }

                        Toast.makeText(DmkActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DmkActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateInput(String id, String mkCu, String mkMoi, String nhapLaiMkMoi) {
        // Kiểm tra các trường nhập liệu trước khi thêm vào cơ sở dữ liệu
        if (id.isEmpty() || mkCu.isEmpty() || mkMoi.isEmpty() || nhapLaiMkMoi.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }




        // Kiểm tra mật khẩu mới
        if (!mkMoi.equals(nhapLaiMkMoi)) {
            Toast.makeText(this, "Mật khẩu mới không khớp với nhập lại mật khẩu mới", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }

    // Phương thức kiểm tra mật khẩu cũ
    private boolean performLogin(String tendk, String mk) {
        // Thực hiện truy vấn kiểm tra đăng nhập
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM taikhoan WHERE tentk=? AND mk=?", new String[]{tendk, mk});

        // Kiểm tra xem có bản ghi nào khớp hay không
        boolean success = cursor.moveToFirst();

        // Đóng con trỏ sau khi sử dụng
        cursor.close();

        return success;
    }
}
