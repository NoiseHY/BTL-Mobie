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

public class qlloaihhActivity extends AppCompatActivity {
    private EditText maloaihh, tenloaihh;
    private Button btthemloaihh, btxoaloaihh, btsualoaihh, btlmloaihh;
    private ListView lv;
    private ArrayList<String> myList;
    private ArrayAdapter<String> myAdapter;
    private SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlloaihh);

        maloaihh = findViewById(R.id.maloaihh);
        tenloaihh = findViewById(R.id.tenloaihh);

        btlmloaihh = findViewById(R.id.btlmloaihh);
        btsualoaihh = findViewById(R.id.btsualoaihh);
        btthemloaihh = findViewById(R.id.btthemloaihh);
        btxoaloaihh = findViewById(R.id.btxoaloaihh);

        lv = findViewById(R.id.lvloaihh);
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(myAdapter);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE IF NOT EXISTS loaihanghoa ( mahanghoa TEXT, tenhanghoa TEXT, PRIMARY KEY(mahanghoa) )";
            myDatabase.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(qlloaihhActivity.this, "Lỗi khi tạo hoặc mở cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
        }

        btthemloaihh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaploaihh = maloaihh.getText().toString();
                String nhaptenloaihh = tenloaihh.getText().toString();

                ContentValues myvalue = new ContentValues();
                myvalue.put("mahanghoa", nhaploaihh);
                myvalue.put("tenhanghoa", nhaptenloaihh);

                if (ktraMa(nhaploaihh)) {
                    Toast.makeText(qlloaihhActivity.this, "Mã hàng hóa đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!validateInput(nhaploaihh, nhaptenloaihh)){
                    return;
                }

                String msg = "";
                try {
                    if (myDatabase.insertOrThrow("loaihanghoa", null, myvalue) != -1) {
                        msg = "Thêm loại hàng hóa thành công!";
                    }
                }catch (Exception e) {
                    msg = "Tên tài khoản đã trùng";
                }
                Toast.makeText(qlloaihhActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btlmloaihh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllItems();
            }
        });

        btsualoaihh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaploaihh = maloaihh.getText().toString();
                String nhaptenloaihh = tenloaihh.getText().toString();


                if (!validateInput(nhaploaihh, nhaptenloaihh)) {
                    Toast.makeText(qlloaihhActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qlloaihhActivity.this);
                builder.setTitle("Xác nhận cập nhật");
                builder.setMessage("Bạn có chắc chắn muốn cập nhật thông tin loại hàng hóa này?");
                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        values.put("tenhanghoa", nhaptenloaihh);

                        int rowsAffected = myDatabase.update("loaihanghoa", values, "mahanghoa=?", new String[]{nhaploaihh});
                        if (rowsAffected > 0) {
                            Toast.makeText(qlloaihhActivity.this, "Cập nhật thông tin loại hàng hóa thành công!", Toast.LENGTH_SHORT).show();
                            displayAllItems();
                        } else {
                            Toast.makeText(qlloaihhActivity.this, "Không thể cập nhật thông tin!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Display the dialog
                builder.show();
            }
        });


        btxoaloaihh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaploaihh = maloaihh.getText().toString();


                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qlloaihhActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa loại hàng hóa này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed deletion
                        int rowsAffected = myDatabase.delete("loaihanghoa", "mahanghoa=?", new String[]{nhaploaihh});
                        if (rowsAffected > 0) {
                            Toast.makeText(qlloaihhActivity.this, "Xóa loại hàng hóa thành công!", Toast.LENGTH_SHORT).show();
                            clearInputFields();
                            displayAllItems();
                        } else {
                            Toast.makeText(qlloaihhActivity.this, "Không thể xóa loại hàng hóa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Display the dialog
                builder.show();
            }
        });

    }

    private void displayAllItems() {
        myList.clear();
        Cursor c = myDatabase.query("loaihanghoa", null, null, null, null, null, null);
        int maHangHoaIndex = c.getColumnIndex("mahanghoa");
        int tenHangHoaIndex = c.getColumnIndex("tenhanghoa");

        if (c.moveToFirst()) {
            do {
                String maHangHoa = (maHangHoaIndex != -1) ? c.getString(maHangHoaIndex) : "";
                String tenHangHoa = (tenHangHoaIndex != -1) ? c.getString(tenHangHoaIndex) : "";

                String itemInfo = "Mã hàng hóa: " + maHangHoa +
                        "\nTên hàng hóa: " + tenHangHoa;

                myList.add(itemInfo);
            } while (c.moveToNext());
        }
        c.close();
        myAdapter.notifyDataSetChanged();
    }

    private boolean ktraMa(String maHangHoa) {
        Cursor cursor = myDatabase.query("loaihanghoa", null, "mahanghoa=?", new String[]{maHangHoa}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }

        return exists;
    }
    private boolean validateInput(String ma, String ten) {
        // Kiểm tra các trường nhập liệu trước khi thêm vào cơ sở dữ liệu
        if (ma.isEmpty() || ten.isEmpty() ) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        maloaihh.setText("");
        tenloaihh.setText("");

    }

}