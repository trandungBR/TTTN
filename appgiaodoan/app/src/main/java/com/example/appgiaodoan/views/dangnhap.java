package com.example.appgiaodoan.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgiaodoan.R;
import com.example.appgiaodoan.controllers.mainControllers;

public class dangnhap extends AppCompatActivity implements mainControllers.AuthViewListener {

    private EditText etSDT, etPass;
    private Button bDangNhap;
    private TextView tvDangKi, tvOTP;

    private mainControllers mMainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        etSDT = findViewById(R.id.etSDT);
        etPass = findViewById(R.id.etPass);
        bDangNhap = findViewById(R.id.bDangNhap);
        tvDangKi = findViewById(R.id.tvDangKi);
        tvOTP = findViewById(R.id.tvOTP);

        mMainController = new mainControllers();

        bDangNhap.setOnClickListener(v -> {
            String sdt = etSDT.getText().toString().trim();
            String matKhau = etPass.getText().toString().trim();

            mMainController.dangNhap(sdt, matKhau, this);
        });

        tvDangKi.setOnClickListener(view -> {
            mMainController.chuyenTrangDangKi(this);
        });

        tvOTP.setOnClickListener(view -> {
            boolean dangLaOTPMode = mMainController.chinhTrangThaiDangNhap();
            chinhGiaoDienOTP(dangLaOTPMode);
        });
    }

    private void chinhGiaoDienOTP(boolean laOTPMode) {
        if (laOTPMode) {
            etPass.setVisibility(View.GONE);
            tvOTP.setText("Đăng nhập bằng mật khẩu");
            etPass.setText("");
        } else {
            etPass.setVisibility(View.VISIBLE);
            tvOTP.setText("Đăng nhập bằng otp");
        }
    }
    private void runOnUIThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override
    public void showLoading(boolean isLoading) {
        runOnUIThread(() -> {
            bDangNhap.setEnabled(!isLoading);
            bDangNhap.setText(isLoading ? "Đang xử lý..." : "Đăng nhập");
        });
    }

    @Override
    public void showError(String message) {
        runOnUIThread(() ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public void showSuccess(String message) {
        runOnUIThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            dieuHuong(trangchu.class);
        });
    }

    @Override
    public void dieuHuong(Class<?> activityClass, String... extras) {
        runOnUIThread(() -> {
            Intent intent = new Intent(this, activityClass);
            if (extras.length > 0 && extras.length % 2 == 0) {
                for (int i = 0; i < extras.length; i += 2) {
                    intent.putExtra(extras[i], extras[i + 1]);
                }
            }
            if (activityClass == trangchu.class) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
            startActivity(intent);
        });
    }
}