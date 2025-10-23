package com.example.appgiaodoan.models;

import android.util.Log;
import org.json.JSONObject;
import okhttp3.*;
import java.io.IOException;

public class database {

    private static final String SUPABASE_URL = "https://hkjqvbgrjqxenugjuhni.supabase.co";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhranF2YmdyanF4ZW51Z2p1aG5pIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk5MTkyOTksImV4cCI6MjA3NTQ5NTI5OX0.T5kJg5aMeXH17BUAf_BpmacgU-GC77y44V-s4xswi44";
    private final OkHttpClient client = new OkHttpClient();

    private boolean testMode = true;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public interface ModelCallback {
        void onSuccess(String message);
        void onError(String message);
    }

    private void runOnBackgroundThread(Runnable r) {
        new Thread(r).start();
    }

    public void guiOTP(String sdt, ModelCallback callback) {
        final String phone = sdt.startsWith("+") ? sdt : "+84" + sdt.substring(1);

        if (testMode) {
            runOnBackgroundThread(() -> {
                try {
                    Thread.sleep(500);
                    callback.onSuccess("Đã gửi OTP (Test Mode)");
                } catch (InterruptedException e) {
                    callback.onError("Lỗi test OTP!");
                }
            });
            return;
        }

        runOnBackgroundThread(() -> {
            try {
                JSONObject json = new JSONObject();
                json.put("phone", phone);
                RequestBody body = RequestBody.create(JSON, json.toString());

                Request request = new Request.Builder()
                        .url(SUPABASE_URL + "/auth/v1/otp")
                        .addHeader("apikey", SUPABASE_API_KEY)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        callback.onSuccess("Gửi OTP thành công!");
                    } else {
                        String errorBody = response.body().string();
                        Log.e("database", "Lỗi gửi OTP: " + errorBody);
                        callback.onError("Lỗi gửi OTP: Hết hạn ngạch.");
                    }
                }
            } catch (Exception e) {
                Log.e("database", "Lỗi gửi OTP: ", e);
                callback.onError("Lỗi kết nối máy chủ!");
            }
        });
    }

    public void xacThucOTP(String sdt, String token, ModelCallback callback) {
        final String phone = sdt.startsWith("+") ? sdt : "+84" + sdt.substring(1);

        if (testMode) {
            if ("123456".equals(token)) {
                callback.onSuccess("Xác thực OTP thành công");
            } else {
                callback.onError("OTP không đúng!");
            }
            return;
        }

        runOnBackgroundThread(() -> {
            try {
                JSONObject json = new JSONObject();
                json.put("phone", phone);
                json.put("token", token);
                RequestBody body = RequestBody.create(JSON, json.toString());

                Request request = new Request.Builder()
                        .url(SUPABASE_URL + "/auth/v1/verify")
                        .addHeader("apikey", SUPABASE_API_KEY)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        callback.onSuccess("Xác thực OTP thành công!");
                    } else {
                        callback.onError("OTP không đúng hoặc hết hạn!");
                    }
                }
            } catch (Exception e) {
                callback.onError("Lỗi kết nối hoặc dữ liệu!");
            }
        });
    }

    public void dangNhap(String sdt, String matKhau, ModelCallback callback) {
        final String phone = sdt.startsWith("+") ? sdt : "+84" + sdt.substring(1);
        final String dummyEmail = phone + "@dummy.app";

        runOnBackgroundThread(() -> {
            try {
                JSONObject json = new JSONObject();
                json.put("email", dummyEmail);
                json.put("password", matKhau);
                RequestBody body = RequestBody.create(JSON, json.toString());

                Request request = new Request.Builder()
                        .url(SUPABASE_URL + "/auth/v1/token?grant_type=password")
                        .addHeader("apikey", SUPABASE_API_KEY)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        callback.onSuccess("Đăng nhập thành công!");
                    } else {
                        String errorBody = response.body().string();
                        Log.e("database", "Lỗi Đăng nhập: " + errorBody);
                        callback.onError("Sai SĐT hoặc Mật khẩu!");
                    }
                }
            } catch (Exception e) {
                Log.e("database", "Lỗi Đăng nhập: ", e);
                callback.onError("Lỗi kết nối hoặc dữ liệu!");
            }
        });
    }

    public void dangKi(String sdt, String matKhau, ModelCallback callback) {
        final String phone = sdt.startsWith("+") ? sdt : "+84" + sdt.substring(1);
        final String dummyEmail = phone + "@dummy.app";

        runOnBackgroundThread(() -> {
            try {
                JSONObject jsonSignup = new JSONObject();
                jsonSignup.put("email", dummyEmail);
                jsonSignup.put("password", matKhau);
                RequestBody bodySignup = RequestBody.create(JSON, jsonSignup.toString());

                Request requestSignup = new Request.Builder()
                        .url(SUPABASE_URL + "/auth/v1/signup")
                        .addHeader("apikey", SUPABASE_API_KEY)
                        .addHeader("Content-Type", "application/json")
                        .post(bodySignup)
                        .build();

                try (Response responseSignup = client.newCall(requestSignup).execute()) {
                    if (responseSignup.isSuccessful()) {
                        String responseBody = responseSignup.body().string();
                        JSONObject userJson = new JSONObject(responseBody);

                        // *** ĐÂY LÀ CHỖ SỬA LỖI ***
                        // Lấy 'id' từ đối tượng 'user' lồng nhau
                        String userId = userJson.getJSONObject("user").getString("id");

                        if (userId == null || userId.isEmpty()) {
                            callback.onError("Lỗi: Không lấy được User ID sau khi đăng ký.");
                            return;
                        }

                        insertNguoiDungProfile(userId, phone, dummyEmail, matKhau, callback);

                    } else {
                        String errorBody = responseSignup.body().string();
                        Log.e("database", "Lỗi Signup: " + errorBody);
                        JSONObject errorJson = new JSONObject(errorBody);
                        String errorMsg = errorJson.optString("msg", "Lỗi không xác định");

                        if (errorMsg.contains("User already registered")) {
                            callback.onError("SĐT này đã được đăng ký!");
                        } else if (errorMsg.contains("Quota Exceeded")) {
                            callback.onError("Lỗi: Đã hết hạn ngạch gửi OTP!");
                        } else if (errorMsg.contains("rate limit")){
                            callback.onError("Bạn thao tác quá nhanh, vui lòng thử lại sau 1 phút.");
                        }
                        else {
                            callback.onError(errorMsg);
                        }
                    }
                }
            } catch (Exception e) {
                // Lỗi JSONException (No value for id) sẽ được bắt ở đây
                Log.e("database", "Lỗi Đăng ký (hoặc parse JSON): ", e);
                callback.onError("Lỗi kết nối hoặc dữ liệu!");
            }
        });
    }

    private void insertNguoiDungProfile(String userId, String sdt, String email, String matKhau, ModelCallback callback) {
        runOnBackgroundThread(() -> {
            try {
                JSONObject jsonProfile = new JSONObject();
                jsonProfile.put("idNguoiDung", userId);
                jsonProfile.put("soDienThoai", sdt);
                jsonProfile.put("eMail", email);
                jsonProfile.put("tenNguoiDung", "Người dùng mới");
                jsonProfile.put("matKhau", matKhau);

                RequestBody bodyProfile = RequestBody.create(JSON, jsonProfile.toString());

                Request requestProfile = new Request.Builder()
                        .url(SUPABASE_URL + "/rest/v1/NGUOIDUNG")
                        .addHeader("apikey", SUPABASE_API_KEY)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Prefer", "return=minimal")
                        .post(bodyProfile)
                        .build();

                try (Response responseProfile = client.newCall(requestProfile).execute()) {
                    if (responseProfile.isSuccessful()) {
                        callback.onSuccess("Đăng ký thành công!");
                    } else {
                        String errorBody = responseProfile.body().string();
                        Log.e("database", "Lỗi Insert Profile: " + errorBody);
                        callback.onError("Lỗi: Không thể tạo hồ sơ người dùng.");
                    }
                }
            } catch (Exception e) {
                Log.e("database", "Lỗi Insert Profile: ", e);
                callback.onError("Lỗi tạo hồ sơ người dùng!");
            }
        });
    }

    public void capNhatMatKhau(String sdt, String matKhauMoi, ModelCallback callback) {
        callback.onError("Chức năng 'Cập nhật Mật khẩu' (Quên mật khẩu) chưa được hỗ trợ API!");
    }
}