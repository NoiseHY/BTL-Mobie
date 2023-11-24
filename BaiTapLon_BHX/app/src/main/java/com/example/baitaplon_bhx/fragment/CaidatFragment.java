package com.example.baitaplon_bhx.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.baitaplon_bhx.activity.DmkActivity;
import com.example.baitaplon_bhx.activity.HTActivity;
import com.example.baitaplon_bhx.R;
import com.example.baitaplon_bhx.activity.MainActivity;
import com.example.baitaplon_bhx.activity.QuanlyActivity;
import com.example.baitaplon_bhx.activity.timkiemsanpham;

import androidx.fragment.app.FragmentTransaction;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaidatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaidatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CaidatFragment() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaidatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaidatFragment newInstance(String param1, String param2) {
        CaidatFragment fragment = new CaidatFragment();
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
        //return inflater.inflate(R.layout.fragment_caidat, container, false);
        View view = inflater.inflate(R.layout.fragment_caidat, container, false);
        Button btAdmin = view.findViewById(R.id.btADMIN);
        Button btDmk = view.findViewById(R.id.btDMk);
        Button bthotro = view.findViewById(R.id.btHT);
        Button btDangxuat = view.findViewById(R.id.btDX);
        Button bttkhh = view.findViewById(R.id.btTKHH);

        btAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

        // Kiểm tra dữ liệu từ Intent
        Bundle bundle = getArguments();
        if (bundle != null) {
            String userType = bundle.getString("tenTK");

            // Kiểm tra nếu userType là "admin", ẩn button đổi mật khẩu
            if ("admin".equals(userType)) {

                btAdmin.setVisibility(View.VISIBLE); // Hiển thị button đổi mật khẩu
            } else {


                btAdmin.setVisibility(View.GONE); // Ẩn button đổi mật khẩu
            }
        }

        btDmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDmk();
            }
        });

        bthotro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHT();

            }
        });
        btDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        bttkhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tkhh();
            }
        });
        return view;

    }

    public void openNewActivity() {
        // Create an intent to start the NewActivity
        Intent intent = new Intent(getActivity(), QuanlyActivity.class);
        startActivity(intent);
    }

    public void openDmk() {
        // Create an intent to start the NewActivity
//        Intent intent = new Intent(getActivity(), DmkActivity.class);
//        startActivity(intent);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String data = bundle.getString("tenTK");

            // Chuyển dữ liệu đến Activity
            Intent intent = new Intent(getActivity(), DmkActivity.class);

            // Đưa dữ liệu vào Intent
            intent.putExtra("tenTK", data);

            // Chạy Activity
            startActivity(intent);
        }

    }

    public void openHT(){
        Intent intent = new Intent(getActivity(), HTActivity.class);
        startActivity(intent);
    }
    public void tkhh(){
        Intent intent = new Intent(getActivity(), timkiemsanpham.class);
        startActivity(intent);
    }
    public void exit(){
        // Trong CaidatFragment
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
    public static CaidatFragment newInstance(String userType) {
        CaidatFragment fragment = new CaidatFragment();
        Bundle args = new Bundle();
        args.putString("userType", userType);
        fragment.setArguments(args);
        return fragment;
    }


}