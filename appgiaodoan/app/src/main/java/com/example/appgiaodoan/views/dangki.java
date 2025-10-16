package com.example.appgiaodoan.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgiaodoan.R;
import com.example.appgiaodoan.controllers.authControl;

public class dangki extends AppCompatActivity {
    private EditText etSDT;
    private Button bDangKi;
    private authControl authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangki);

        etSDT = findViewById(R.id.etSDT);
        bDangKi = findViewById(R.id.bDangKi);
        authController = new authControl();

        bDangKi.setOnClickListener(v -> {
            String sdt = etSDT.getText().toString().trim();

            if (sdt.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }

            authController.guiOTP(sdt, new authControl.traOTP() {
                @Override
                public void onSuccess() {
                    runOnUiThread(() -> {
                        Toast.makeText(dangki.this, "Đã gửi mã OTP, vui lòng kiểm tra điện thoại!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(dangki.this, xacthucotp.class);
                        intent.putExtra("phone_number", sdt);
                        startActivity(intent);
                    });
                }

                @Override
                public void onError(String message) {
                    runOnUiThread(() ->
                            Toast.makeText(dangki.this, message, Toast.LENGTH_LONG).show()
                    );
                }
            });
        });
    }
}
