package com.example.appgiaodoan.controllers;

import java.util.regex.Pattern;

public class ktPass {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // ít nhất 1 số
                    "(?=.*[A-Z])" +         // ít nhất 1 chữ hoa
                    "(?=.*[@#$%^&+=!])" +   // ít nhất 1 ký tự đặc biệt
                    "(?=\\S+$)" +           // không có khoảng trắng
                    ".{6,}" +               // tối thiểu 6 ký tự
                    "$");

    public interface callBack {
        void onSuccess();
        void onError(String message);
    }

    public void ktPasswords(String matKhau, String xacNhanMatKhau, callBack goi) {
        if (matKhau.isEmpty() || xacNhanMatKhau.isEmpty()) {
            goi.onError("Vui lòng nhập đầy đủ mật khẩu và xác nhận mật khẩu!");
            return;
        }

        if (!matKhau.equals(xacNhanMatKhau)) {
            goi.onError("Mật khẩu xác nhận không khớp!");
            return;
        }

        if (matKhau.length() < 6) {
            goi.onError("Mật khẩu phải có tối thiểu 6 ký tự!");
            return;
        }

        boolean coChuHoa = !matKhau.equals(matKhau.toLowerCase());
        boolean coKyTuDacBiet = !matKhau.matches("[A-Za-z0-9 ]*");

        if (!coChuHoa) {
            goi.onError("Mật khẩu phải chứa ít nhất 1 chữ viết hoa!");
            return;
        }

        if (!coKyTuDacBiet) {
            goi.onError("Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt!");
            return;
        }
        goi.onSuccess();
    }
}