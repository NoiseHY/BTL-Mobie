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


public class qlnccActivity extends AppCompatActivity {
    private EditText mancc, tenncc, diachincc;
    private Button btthemncc, btxoancc, btsuancc, btlmncc;
    private ListView lv;
    private ArrayList<String> myList;
    private ArrayAdapter<String> myAdapter;
    private SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlncc);

        mancc = findViewById(R.id.mancc);
        tenncc = findViewById(R.id.tenncc);
        diachincc = findViewById(R.id.diachincc);
        btthemncc = findViewById(R.id.btthemncc);
        btlmncc = findViewById(R.id.btlmncc);
        btsuancc = findViewById(R.id.btsuancc);
        btxoancc = findViewById(R.id.btxoancc);

        lv = findViewById(R.id.lvncc); // Replace with your actual ListView ID
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(myAdapter);
        myDatabase = openOrCreateDatabase("BTL_BHX", MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE IF NOT EXISTS nhacungcap ( manhacungcap TEXT, tennhacungcap TEXT, diachi TEXT ,PRIMARY KEY(manhacungcap) )";
            myDatabase.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(qlnccActivity.this, "Lỗi khi tạo hoặc mở cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
        }

        btthemncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhapncc = mancc.getText().toString();
                String nhaptenncc = tenncc.getText().toString();
                // Assuming you have additional input fields for "diachi"
                String nhapdiachi = diachincc.getText().toString();

                ContentValues myvalue = new ContentValues();
                myvalue.put("manhacungcap", nhapncc);
                myvalue.put("tennhacungcap", nhaptenncc);
                myvalue.put("diachi", nhapdiachi);

                // Validate input
                if (!validateInput(nhapncc, nhaptenncc, nhapdiachi)) {
                    return;
                }

                String msg = "";
                try {
                    if (myDatabase.insertOrThrow("nhacungcap", null, myvalue) != -1) {
                        msg = "Thêm nhà cung cấp thành công!";
                    }
                } catch (Exception e) {
                    msg = "Lỗi khi thêm nhà cung cấp";
                }
                Toast.makeText(qlnccActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btlmncc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllItems();
            }
        });

        btsuancc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhapncc = mancc.getText().toString();
                String nhaptenncc = tenncc.getText().toString();
                String nhapdiachi = diachincc.getText().toString();

                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qlnccActivity.this);
                builder.setTitle("Xác nhận cập nhật");
                builder.setMessage("Bạn có chắc chắn muốn cập nhật thông tin nhà cung cấp này?");
                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed update
                        ContentValues values = new ContentValues();
                        values.put("tennhacungcap", nhaptenncc);
                        values.put("diachi", nhapdiachi);

                        int rowsAffected = myDatabase.update("nhacungcap", values, "manhacungcap=?", new String[]{nhapncc});
                        if (rowsAffected > 0) {
                            Toast.makeText(qlnccActivity.this, "Cập nhật thông tin nhà cung cấp thành công!", Toast.LENGTH_SHORT).show();
                            displayAllItems();
                        } else {
                            Toast.makeText(qlnccActivity.this, "Không thể cập nhật thông tin!", Toast.LENGTH_SHORT).show();
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

        btxoancc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nhapncc = mancc.getText().toString();

                // Show a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(qlnccActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa nhà cung cấp này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed deletion
                        int rowsAffected = myDatabase.delete("nhacungcap", "manhacungcap=?", new String[]{nhapncc});
                        if (rowsAffected > 0) {
                            Toast.makeText(qlnccActivity.this, "Xóa nhà cung cấp thành công!", Toast.LENGTH_SHORT).show();
                            clearInputFields();
                            displayAllItems();
                        } else {
                            Toast.makeText(qlnccActivity.this, "Không thể xóa nhà cung cấp!", Toast.LENGTH_SHORT).show();
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
        Cursor c = myDatabase.query("nhacungcap", null, null, null, null, null, null);
        int maNhaCungCapIndex = c.getColumnIndex("manhacungcap");
        int tenNhaCungCapIndex = c.getColumnIndex("tennhacungcap");
        int diaChiIndex = c.getColumnIndex("diachi");

        if (c.moveToFirst()) {
            do {
                String maNhaCungCap = (maNhaCungCapIndex != -1) ? c.getString(maNhaCungCapIndex) : "";
                String tenNhaCungCap = (tenNhaCungCapIndex != -1) ? c.getString(tenNhaCungCapIndex) : "";
                String diaChi = (diaChiIndex != -1) ? c.getString(diaChiIndex) : "";

                String itemInfo = "Mã nhà cung cấp: " + maNhaCungCap +
                        "\nTên nhà cung cấp: " + tenNhaCungCap +
                        "\nĐịa chỉ: " + diaChi;

                myList.add(itemInfo);
            } while (c.moveToNext());
        }
        c.close();
        myAdapter.notifyDataSetChanged();
    }
    private boolean validateInput(String ma, String ten, String diachi) {
        // Kiểm tra các trường nhập liệu trước khi thêm vào cơ sở dữ liệu
        if (ma.isEmpty() || ten.isEmpty() || diachi.isEmpty() ) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearInputFields() {
        mancc.setText("");
        tenncc.setText("");
        diachincc.setText("");


    }
}