package com.example.baitaplon_bhx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;


import com.example.baitaplon_bhx.R;

import java.util.ArrayList;

public class qltkActivity extends AppCompatActivity {
    private EditText tentk, mktk, hvttk, sdttk;
    private Button btthemtk, btxoatk, btsuatk, btlmtk;
    private ListView lv;
    private ArrayList<String> myList;
    private ArrayAdapter<String> myAdapter;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltk);

        tentk = findViewById(R.id.tentk);
        mktk = findViewById(R.id.matkhautk);
        hvttk = findViewById(R.id.hvttk);
        sdttk = findViewById(R.id.sdttk);

        btthemtk = findViewById(R.id.btthemtk);
        btlmtk = findViewById(R.id.btlmtk);
        btsuatk = findViewById(R.id.btsuatk);
        btxoatk = findViewById(R.id.btxoatk);

        lv = findViewById(R.id.lvtk);
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(myAdapter);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);


        try {
            String sql = "CREATE TABLE IF NOT EXISTS taikhoan ( tentk TEXT, mk TEXT, hvt TEXT, sdt TEXT, PRIMARY KEY(tentk) )";
            myDatabase.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ tại đây
            Toast.makeText(qltkActivity.this, "Lỗi khi tạo hoặc mở cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
        }


        btthemtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaptk = tentk.getText().toString();
                String nhapmk = mktk.getText().toString();
                String nhapsdt = sdttk.getText().toString();
                String nhaphvt = hvttk.getText().toString();

                if (!validateInput(nhaptk, nhapmk, nhaphvt, nhapsdt)) {
                    return;
                }

                ContentValues myvalue =new ContentValues();
                myvalue.put("tentk", nhaptk);
                myvalue.put("mk", nhapmk);
                myvalue.put("hvt", nhapsdt);
                myvalue.put("sdt", nhaphvt);

                String msg = "";
                try {
                    if (myDatabase.insertOrThrow("taikhoan", null, myvalue) != -1) {
                        msg = "Thêm tài khoản thành công!";
                    }
                } catch (Exception e) {
                    msg = "Tên tài khoản đã trùng";
                }

                Toast.makeText(qltkActivity.this, msg, Toast.LENGTH_SHORT).show();
                displayAllAccounts();
            }
        });

        btlmtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllAccounts();
            }
        });


        btsuatk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaptk = tentk.getText().toString();
                String nhapmk = mktk.getText().toString();
                String nhapsdt = sdttk.getText().toString();
                String nhaphvt = hvttk.getText().toString();

                if (!validateInput(nhaptk, nhapmk, nhaphvt, nhapsdt)) {
                    return;
                }

                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qltkActivity.this);
                builder.setTitle("Xác nhận cập nhật");
                builder.setMessage("Bạn có chắc chắn muốn cập nhật thông tin tài khoản này?");
                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed update
                        ContentValues values = new ContentValues();
                        values.put("mk", nhapmk);
                        values.put("hvt", nhaphvt);
                        values.put("sdt", nhapsdt);

                        int rowsAffected = myDatabase.update("taikhoan", values, "tentk=?", new String[]{nhaptk});
                        if (rowsAffected > 0) {
                            Toast.makeText(qltkActivity.this, "Cập nhật thông tin tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            displayAllAccounts();
                        } else {
                            Toast.makeText(qltkActivity.this, "Không thể cập nhật thông tin tài khoản!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled update
                        dialog.dismiss();
                    }
                });

                // Display the dialog
                builder.show();
            }
        });


        btxoatk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaptk = tentk.getText().toString();

                if (!ktraMa(nhaptk)) {
                    return;
                }

                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qltkActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed deletion
                        int rowsAffected = myDatabase.delete("taikhoan", "tentk=?", new String[]{nhaptk});
                        if (rowsAffected > 0) {
                            Toast.makeText(qltkActivity.this, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            clearInputFields();
                            displayAllAccounts();
                        } else {
                            Toast.makeText(qltkActivity.this, "Không thể xóa tài khoản!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled deletion
                        dialog.dismiss();
                    }
                });

                // Display the dialog
                builder.show();
            }
        });

    }

    private void displayAllAccounts() {
        myList.clear();
        Cursor c = myDatabase.query("taikhoan", null, null, null, null, null, null);
        int tenTKIndex = c.getColumnIndex("tentk");
        int matKhauIndex = c.getColumnIndex("mk");
        int hoVaTenIndex = c.getColumnIndex("hvt");
        int soDienThoaiIndex = c.getColumnIndex("sdt");

        if (c.moveToFirst()) {
            do {
                String tenTK = (tenTKIndex != -1) ? c.getString(tenTKIndex) : "";
                String matKhau = (matKhauIndex != -1) ? c.getString(matKhauIndex) : "";
                String hoVaTen = (hoVaTenIndex != -1) ? c.getString(hoVaTenIndex) : "";
                String soDienThoai = (soDienThoaiIndex != -1) ? c.getString(soDienThoaiIndex) : "";

                String accountInfo = "Tên tài khoản: " + tenTK +
                        "\nMật khẩu: " + matKhau +
                        "\nHọ và tên: " + hoVaTen +
                        "\nSố điện thoại: " + soDienThoai;

                myList.add(accountInfo);
            } while (c.moveToNext());
        }
        c.close();
        myAdapter.notifyDataSetChanged();
    }

    private boolean validateInput(String tendk, String mk, String hvt, String sdt) {
        // Kiểm tra các trường nhập liệu trước khi thêm vào cơ sở dữ liệu
        if (tendk.isEmpty() || mk.isEmpty() || hvt.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra định dạng số điện thoại
        if (!isValidPhoneNumber(sdt)) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private  boolean ktraMa(String ma){
        if (ma.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập mã tài khoản để xóa", Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Xoá khoảng trắng từ số điện thoại
        phoneNumber = phoneNumber.replaceAll("\\s", "");

        // Kiểm tra xem số điện thoại có chứa toàn bộ là chữ số không
        if (!phoneNumber.matches("\\d+")) {
            return false;
        }

        // Kiểm tra độ dài của số điện thoại (ví dụ: có 10 chữ số)
        if (phoneNumber.length() != 10) {
            return false;
        }

        // Các điều kiện kiểm tra khác có thể được thêm vào tại đây dựa trên yêu cầu thực tế

        return true;
    }

    private void clearInputFields() {
        tentk.setText("");
        mktk.setText("");
        hvttk.setText("");
        sdttk.setText("");
    }

}
