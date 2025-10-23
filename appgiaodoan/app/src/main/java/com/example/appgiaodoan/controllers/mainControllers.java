package com.example.appgiaodoan.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.appgiaodoan.models.database;
import com.example.appgiaodoan.views.dangki;
import com.example.appgiaodoan.views.nhapmatkhau;
import com.example.appgiaodoan.views.xacthucotp;
// Giả sử bạn có một màn hình chính tên là HomeActivity
// import com.example.appgiaodoan.views.HomeActivity;

// Tên lớp được đổi thành mainControllers theo yêu cầu của bạn
public class mainControllers {

    private final database mDatabase;
    private final ktPass mKtPass;
    private boolean OTPMode = false;

    public interface AuthViewListener {
        void showLoading(boolean isLoading);
        void showError(String message);
        void showSuccess(String message);
        void dieuHuong(Class<?> activityClass, String... extras);
    }

    public mainControllers() {
        this.mDatabase = new database();
        this.mKtPass = new ktPass();
    }

    public boolean isOTPMode() {
        return OTPMode;
    }

    public boolean chinhTrangThaiDangNhap() {
        OTPMode = !OTPMode;
        return OTPMode;
    }

    public void dangNhap(String sdt, String matKhau, AuthViewListener listener) {
        listener.showLoading(true);

        if (!OTPMode) {
            if (sdt.isEmpty() || matKhau.isEmpty()) {
                listener.showError("Vui lòng nhập đầy đủ SĐT và Mật khẩu!");
                listener.showLoading(false);
                return;
            }
            mDatabase.dangNhap(sdt, matKhau, new database.ModelCallback() {
                @Override
                public void onSuccess(String message) {
                    listener.showSuccess(message);
                    listener.showLoading(false);
                    // listener.dieuHuong(HomeActivity.class);
                }
                @Override
                public void onError(String message) {
                    listener.showError(message);
                    listener.showLoading(false);
                }
            });
        }
        else {
            if (sdt.isEmpty()) {
                listener.showError("Vui lòng nhập Số điện thoại!");
                listener.showLoading(false);
                return;
            }
            guiOTP(sdt, listener, xacthucotp.class);
        }
    }

    public void chuyenTrangDangKi(AuthViewListener listener) {
        listener.dieuHuong(dangki.class);
    }

    public void guiOTP(String sdt, AuthViewListener listener, Class<?> classRedirect) {
        if (sdt.isEmpty()) {
            listener.showError("Vui lòng nhập số điện thoại!");
            return;
        }
        listener.showLoading(true);
        mDatabase.guiOTP(sdt, new database.ModelCallback() {
            @Override
            public void onSuccess(String message) {
                listener.showSuccess(message);
                listener.showLoading(false);
                listener.dieuHuong(classRedirect, "phone_number", sdt);
            }
            @Override
            public void onError(String message) {
                listener.showError(message);
                listener.showLoading(false);
            }
        });
    }

    public void xacThucOTP(String sdt, String otp, AuthViewListener listener, Class<?> classRedirect) {
        if (otp.isEmpty() || otp.length() != 6) {
            listener.showError("Vui lòng nhập OTP 6 ký tự!");
            return;
        }
        listener.showLoading(true);
        mDatabase.xacThucOTP(sdt, otp, new database.ModelCallback() {
            @Override
            public void onSuccess(String message) {
                listener.showSuccess(message);
                listener.showLoading(false);
                listener.dieuHuong(classRedirect, "phone_number", sdt);
            }
            @Override
            public void onError(String message) {
                listener.showError(message);
                listener.showLoading(false);
            }
        });
    }

    public void dangKiTaiKhoanMoi(String sdt, String matKhau, String xacNhanMatKhau, AuthViewListener listener) {
        listener.showLoading(true);

        mKtPass.ktPasswords(matKhau, xacNhanMatKhau, new ktPass.callBack() {
            @Override
            public void onSuccess() {
                mDatabase.dangKi(sdt, matKhau, new database.ModelCallback() {
                    @Override
                    public void onSuccess(String message) {
                        listener.showSuccess(message);
                        listener.showLoading(false);
                    }

                    @Override
                    public void onError(String message) {
                        listener.showError(message);
                        listener.showLoading(false);
                    }
                });
            }

            @Override
            public void onError(String message) {
                listener.showError(message);
                listener.showLoading(false);
            }
        });
    }
}