package com.example.baitaplon_bhx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baitaplon_bhx.R;

import java.util.ArrayList;

public class timkiemsanpham extends AppCompatActivity {
    private EditText edttensp;
    private Button bttksp;
    private ListView lv;
    private ArrayList<String> myList;
    private ArrayAdapter<String> myAdapter;
    private SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiemsanpham);

        edttensp = findViewById(R.id.tensp);
        bttksp = findViewById(R.id.bttksp);

        lv = findViewById(R.id.lvtksp);
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(myAdapter);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String data = bundle.getString("tensp");
            if (data != null) {
                edttensp.setText(data);
            } else {
                Toast.makeText(this, "Dữ liệu trống hoặc không tồn tại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không có dữ liệu được chuyển đến", Toast.LENGTH_SHORT).show();
        }

        bttksp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuKhoaTimKiem = edttensp.getText().toString();

                // Tìm kiếm sản phẩm dựa trên từ khóa nhập vào và lấy toàn bộ thông tin từ hàng hóa
                Cursor cursor = myDatabase.rawQuery("SELECT * FROM hanghoa WHERE tenhanghoa LIKE '%" + tuKhoaTimKiem + "%'", null);

                // Xử lý kết quả tìm kiếm
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        myList.clear(); // Xóa dữ liệu cũ trong ArrayList
                        do {
                            // Lấy thông tin từ cursor và xử lý
                            String maHangHoa = cursor.getString(cursor.getColumnIndexOrThrow("mahanghoa"));
                            String tenHangHoa = cursor.getString(cursor.getColumnIndexOrThrow("tenhanghoa"));
                            String moTa = cursor.getString(cursor.getColumnIndexOrThrow("mota"));
                            int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("soluong"));
                            float donGia = cursor.getFloat(cursor.getColumnIndexOrThrow("dongia"));

                            // Tạo chuỗi thông tin sản phẩm từ dữ liệu lấy được và thêm vào danh sách
                            String thongTinSanPham = "Mã: " + maHangHoa + "\nTên: " + tenHangHoa + "\nMô tả: " + moTa + "\nSố lượng: " + soLuong + "\nĐơn giá: " + donGia;
                            myList.add(thongTinSanPham); // Thêm thông tin sản phẩm vào ArrayList
                        } while (cursor.moveToNext());

                        cursor.close();

                        // Thông báo cho ArrayAdapter rằng dữ liệu đã thay đổi
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }
}