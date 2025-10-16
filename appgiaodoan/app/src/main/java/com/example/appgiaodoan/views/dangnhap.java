package com.example.appgiaodoan.views;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgiaodoan.R;

public class dangnhap extends AppCompatActivity{
    private EditText etMailOrSDT, etPass;
    private Button bDangNhap;
    private TextView tvDangKi;
    String SUPABASE_URL = "https://hkjqvbgrjqxenugjuhni.supabase.co";
    String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhranF2YmdyanF4ZW51Z2p1aG5pIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk5MTkyOTksImV4cCI6MjA3NTQ5NTI5OX0.T5kJg5aMeXH17BUAf_BpmacgU-GC77y44V-s4xswi44";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        etMailOrSDT = findViewById(R.id.etMailOrSDT);
        bDangNhap = findViewById(R.id.bDangNhap);
        tvDangKi = findViewById(R.id.tvDangKi);
        etPass= findViewById(R.id.etPass);
        bDangNhap.setOnClickListener(v ->{
            String tenDangNhap = etMailOrSDT.getText().toString().trim();

            if (tenDangNhap.isEmpty()){
                Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
            }
        });
        tvDangKi.setOnClickListener(view -> {
            Toast.makeText(this, "Chuyển sang trang đăng kí", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(dangnhap.this, dangki.class);
            startActivity(i);
        });

    }
}
