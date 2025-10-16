package com.example.appgiaodoan.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgiaodoan.R;
import com.example.appgiaodoan.controllers.authControl;

public class xacthucotp extends AppCompatActivity {

    private EditText etSDT;
    private Button bXacNhan;
    private ImageView btnBack;
    private authControl authController;
    private String sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        etSDT = findViewById(R.id.etSDT);
        bXacNhan = findViewById(R.id.bXacNhan);
        btnBack = findViewById(R.id.btnBack);
        authController = new authControl();

        sdt = getIntent().getStringExtra("phone_number");

        btnBack.setOnClickListener(v -> finish());

        bXacNhan.setOnClickListener(v -> {
            String otp = etSDT.getText().toString().trim();

            if (otp.isEmpty() || otp.length() != 6) {
                Toast.makeText(this, "Vui lòng nhập OTP 6 ký tự!", Toast.LENGTH_SHORT).show();
                return;
            }

            authController.xacThucOTP(sdt, otp, new authControl.traOTP() {
                @Override
                public void onSuccess() {
                    runOnUiThread(() -> {
                        Toast.makeText(xacthucotp.this, "OTP đúng!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(xacthucotp.this, nhapmatkhau.class);
                        intent.putExtra("phone_number", sdt);
                        startActivity(intent);
                    });
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(() ->
                            Toast.makeText(xacthucotp.this, message, Toast.LENGTH_LONG).show()
                    );
                }
            });
        });
    }
}
