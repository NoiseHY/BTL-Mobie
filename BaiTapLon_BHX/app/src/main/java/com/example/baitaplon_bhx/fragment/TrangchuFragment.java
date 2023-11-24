package com.example.baitaplon_bhx.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.baitaplon_bhx.R;
import com.example.baitaplon_bhx.activity.chitietbia;
import com.example.baitaplon_bhx.activity.chitietbuoidaxanh;
import com.example.baitaplon_bhx.activity.chitietraumuc;
import com.example.baitaplon_bhx.activity.chitietspActivity;
import com.example.baitaplon_bhx.activity.timkiemsanpham;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangchuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangchuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangchuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangchuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangchuFragment newInstance(String param1, String param2) {
        TrangchuFragment fragment = new TrangchuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        ImageView imageTao = view.findViewById(R.id.pic);
        ImageView imgbia = view.findViewById(R.id.pic111);
        ImageView imgdua = view.findViewById(R.id.pic101);
        ImageView imgmuc = view.findViewById(R.id.pic100);
        ImageView imgtk = view.findViewById(R.id.imgtk);

        EditText tksp = view.findViewById(R.id.edtTKSPTC);
        // Đặt sự kiện OnClickListener cho ImageView
        imageTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở NewActivity khi ảnh được click
                Intent intent = new Intent(getActivity(), chitietspActivity.class);
                startActivity(intent);
            }
        });

        imgbia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở NewActivity khi ảnh được click
                Intent intent = new Intent(getActivity(), chitietbia.class);
                startActivity(intent);
            }
        });
        imgdua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity khi ảnh được click
                Intent intent = new Intent(getActivity(), chitietbuoidaxanh.class);
                startActivity(intent);
            }
        });
        imgmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở YourNewActivity khi ảnh được click
                Intent intent = new Intent(getActivity(), chitietraumuc.class);
                startActivity(intent);
            }
        });

        imgtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tksp != null) {
                    String data = tksp.getText().toString();
                    // Mở timkiemsanpham Activity khi ảnh được click
                    Intent intent = new Intent(getActivity(), timkiemsanpham.class);
                    intent.putExtra("tensp", data);
                    // Khởi chạy hoạt động mới
                    startActivity(intent);
                } else {
                    // Xử lý khi tksp không được ánh xạ thành công
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}