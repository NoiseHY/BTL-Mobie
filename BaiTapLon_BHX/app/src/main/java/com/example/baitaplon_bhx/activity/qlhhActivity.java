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

public class qlhhActivity extends AppCompatActivity {
    private EditText mahh, tenhh, motahh, slhh, dghh;
    private Button btthemhh, btxoahh, btsuahh, btlmhh;
    private ListView lv;
    private ArrayList<String> myList;
    private ArrayAdapter<String> myAdapter;
    private SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhh);

        mahh = findViewById(R.id.mahh);
        tenhh = findViewById(R.id.tenhh);
        motahh = findViewById(R.id.motahh);
        slhh = findViewById(R.id.slhh);
        dghh = findViewById(R.id.dghh);

        btthemhh = findViewById(R.id.btthemhh);
        btsuahh = findViewById(R.id.btsuahh);
        btxoahh = findViewById(R.id.btxoahh);
        btlmhh = findViewById(R.id.btlmhh);

        lv = findViewById(R.id.lvhh); // Replace with your actual ListView ID
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(myAdapter);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE IF NOT EXISTS hanghoa ( mahanghoa TEXT, tenhanghoa TEXT, mota TEXT, soluong INTEGER, dongia REAL ,PRIMARY KEY(mahanghoa) )";
            myDatabase.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(qlhhActivity.this, "Lỗi khi tạo hoặc mở cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
        }

        btthemhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaphh = mahh.getText().toString();
                String nhaptenhh = tenhh.getText().toString();
                // Assuming you have additional input fields for "mota," "soluong," and "dongia"
                String nhapmota = motahh.getText().toString();
                String nhapsoluong = slhh.getText().toString();
                String nhapdongia = dghh.getText().toString();

                ContentValues myvalue = new ContentValues();
                myvalue.put("mahanghoa", nhaphh);
                myvalue.put("tenhanghoa", nhaptenhh);
                myvalue.put("mota", nhapmota);

                // Validate and add additional fields
                if (!validateInput(nhaphh, nhaptenhh, nhapmota, nhapsoluong, nhapdongia)) {
                    return;
                }

                // Assuming soluong and dongia are numeric, you can convert them to appropriate types
                myvalue.put("soluong", Integer.parseInt(nhapsoluong));
                myvalue.put("dongia", Float.parseFloat(nhapdongia));



                String msg = "";
                try {
                    if (myDatabase.insertOrThrow("hanghoa", null, myvalue) != -1) {
                        msg = "Thêm hàng hóa thành công!";
                    }
                } catch (Exception e) {
                    msg = "Lỗi khi thêm hàng hóa";
                }
                Toast.makeText(qlhhActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btlmhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllItems();
            }
        });

        btsuahh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaphh = mahh.getText().toString();
                String nhaptenhh = tenhh.getText().toString();
                String nhapmota = motahh.getText().toString();
                String nhapsoluong = slhh.getText().toString();
                String nhapdongia = dghh.getText().toString();

                /*if (validateInput(nhaphh, nhaptenhh, nhapmota, nhapsoluong, nhapdongia)) {
                    return;
                }*/

                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qlhhActivity.this);
                builder.setTitle("Xác nhận cập nhật");
                builder.setMessage("Bạn có chắc chắn muốn cập nhật thông tin hàng hóa này?");
                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed update
                        ContentValues values = new ContentValues();
                        values.put("tenhanghoa", nhaptenhh);
                        values.put("mota", nhapmota);
                        values.put("soluong", Integer.parseInt(nhapsoluong));
                        values.put("dongia", Float.parseFloat(nhapdongia));

                        int rowsAffected = myDatabase.update("hanghoa", values, "mahanghoa=?", new String[]{nhaphh});
                        if (rowsAffected > 0) {
                            Toast.makeText(qlhhActivity.this, "Cập nhật thông tin hàng hóa thành công!", Toast.LENGTH_SHORT).show();
                            displayAllItems();
                        } else {
                            Toast.makeText(qlhhActivity.this, "Không thể cập nhật thông tin!", Toast.LENGTH_SHORT).show();
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

        btxoahh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhaphh = mahh.getText().toString();


                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qlhhActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa hàng hóa này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed deletion
                        int rowsAffected = myDatabase.delete("hanghoa", "mahanghoa=?", new String[]{nhaphh});
                        if (rowsAffected > 0) {
                            Toast.makeText(qlhhActivity.this, "Xóa hàng hóa thành công!", Toast.LENGTH_SHORT).show();
                            clearInputFields();
                            displayAllItems();
                        } else {
                            Toast.makeText(qlhhActivity.this, "Không thể xóa hàng hóa!", Toast.LENGTH_SHORT).show();
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

    private void displayAllItems() {
        myList.clear();
        Cursor c = myDatabase.query("hanghoa", null, null, null, null, null, null);
        int maHangHoaIndex = c.getColumnIndex("mahanghoa");
        int tenHangHoaIndex = c.getColumnIndex("tenhanghoa");
        int moTaIndex = c.getColumnIndex("mota");
        int soLuongIndex = c.getColumnIndex("soluong");
        int donGiaIndex = c.getColumnIndex("dongia");

        if (c.moveToFirst()) {
            do {
                String maHangHoa = (maHangHoaIndex != -1) ? c.getString(maHangHoaIndex) : "";
                String tenHangHoa = (tenHangHoaIndex != -1) ? c.getString(tenHangHoaIndex) : "";
                String moTa = (moTaIndex != -1) ? c.getString(moTaIndex) : "";
                int soLuong = (soLuongIndex != -1) ? c.getInt(soLuongIndex) : 0;
                float donGia = (donGiaIndex != -1) ? c.getFloat(donGiaIndex) : 0.0f;

                String itemInfo = "Mã hàng hóa: " + maHangHoa +
                        "\nTên hàng hóa: " + tenHangHoa +
                        "\nMô tả: " + moTa +
                        "\nSố lượng: " + soLuong +
                        "\nĐơn giá: " + donGia;

                myList.add(itemInfo);
            } while (c.moveToNext());
        }
        c.close();
        myAdapter.notifyDataSetChanged();
    }



    private boolean validateInput(String ma, String ten, String moTa, String soLuong, String donGia) {
        // Kiểm tra các trường nhập liệu trước khi thêm vào cơ sở dữ liệu
        if (ma.isEmpty() || ten.isEmpty() || moTa.isEmpty() || soLuong.isEmpty() || donGia.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Additional validation for numeric fields (soLuong and donGia)
        try {
            int intSoLuong = Integer.parseInt(soLuong);
            float floatDonGia = Float.parseFloat(donGia);

            // You can add further validation rules for these numeric fields if needed

        } catch (NumberFormatException e) {
            // Handle the case where parsing to integer or float fails
            Toast.makeText(this, "Số lượng và đơn giá phải là số", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void clearInputFields() {
        mahh.setText("");
        tenhh.setText("");
        slhh.setText("");
        motahh.setText("");
        dghh.setText("");

    }
}