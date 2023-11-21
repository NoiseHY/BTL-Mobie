package com.example.baitaplon_bhx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon_bhx.R;

public class MainActivity extends AppCompatActivity {
    private EditText tendangnhap, matkhau;
    private Button btDangNhap;
    private TextView dkText;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tendangnhap = findViewById(R.id.tendangnhap);
        matkhau = findViewById(R.id.matkhau);
        btDangNhap = findViewById(R.id.btDangNhap);
        dkText = findViewById(R.id.dkText);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);

        btDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tendk_dtb = tendangnhap.getText().toString().trim();
                String mk_dtb = matkhau.getText().toString();
                if (tendangnhap.getText().toString().equals("admin") && matkhau.getText().toString().equals("123456")) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);

                } else {
                    // Kiểm tra đăng nhập từ cơ sở dữ liệu
                    if (validateInputLogin(tendk_dtb, mk_dtb) && performLogin(tendk_dtb, mk_dtb)) {
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        // Add code để chuyển đến màn hình chính hoặc làm gì đó khác tùy thuộc vào ứng dụng của bạn
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean validateInputLogin(String ten, String mk) {
                // Kiểm tra các trường nhập liệu trước khi thực hiện đăng nhập
                if (ten.isEmpty() || mk.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            private boolean performLogin(String tendk, String mk) {
                // Thực hiện truy vấn kiểm tra đăng nhập
                Cursor cursor = myDatabase.rawQuery("SELECT * FROM taikhoan WHERE tentk=? AND mk=?", new String[]{tendk, mk});

                // Kiểm tra xem có bản ghi nào khớp hay không
                boolean success = cursor.moveToFirst();

                // Đóng con trỏ sau khi sử dụng
                cursor.close();

                return success;
            }
        });

        dkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, dangkiActivity.class);
                startActivity(intent);
            }
        });
    }
}
