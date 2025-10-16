package com.example.appgiaodoan.views;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgiaodoan.R;

public class nhapmatkhau extends AppCompatActivity {
    private EditText etNhapMatKhau;
    private EditText etXacNhanMatKhau;
    private Button bXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapmatkhau);

        etNhapMatKhau = findViewById(R.id.etnhapmatkhau);
        etXacNhanMatKhau = findViewById(R.id.etxacnhanmatkhau);
    }
}
