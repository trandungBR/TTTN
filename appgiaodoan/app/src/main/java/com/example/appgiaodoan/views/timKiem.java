package com.example.appgiaodoan.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View; // Thêm import View
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgiaodoan.R;
import com.example.appgiaodoan.adapters.quanAnAdapter;
import com.example.appgiaodoan.models.giaLap_trangChu;
import com.example.appgiaodoan.models.quanAn;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class timKiem extends AppCompatActivity {

    private static final String TAG = "TimKiem";

    private Toolbar toolbarTimKiem;
    private EditText etTimKiem;
    private ChipGroup chipGroupLoc;
    private Chip chipGanToi;
    private Chip chipDanhGia;
    private RecyclerView rvKetQuaTimKiem;

    private quanAnAdapter adapterKetQua;
    private List<quanAn> danhSachQuanAnGoc;
    private List<quanAn> danhSachHienThi;

    private String tuKhoaTimKiem = "";
    private int boLocDaChonId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timkiem);

        toolbarTimKiem = findViewById(R.id.toolbarTimKiem);
        etTimKiem = findViewById(R.id.etTimKiem);
        chipGroupLoc = findViewById(R.id.chipGroupLoc);
        chipGanToi = findViewById(R.id.chipGanToi);
        chipDanhGia = findViewById(R.id.chipDanhGia);
        rvKetQuaTimKiem = findViewById(R.id.rvKetQuaTimKiem);

        setSupportActionBar(toolbarTimKiem);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarTimKiem.setNavigationOnClickListener(v -> finish());

        giaLap_trangChu nguonDuLieu = new giaLap_trangChu();
        danhSachQuanAnGoc = nguonDuLieu.taoDuLieuGiaLap();
        tinhToanVaCapNhatKhoangCach(danhSachQuanAnGoc);

        danhSachHienThi = new ArrayList<>(danhSachQuanAnGoc);
        adapterKetQua = new quanAnAdapter(this, danhSachHienThi);
        rvKetQuaTimKiem.setLayoutManager(new LinearLayoutManager(this));
        rvKetQuaTimKiem.setAdapter(adapterKetQua);

        etTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tuKhoaTimKiem = s.toString().toLowerCase(Locale.getDefault()).trim();
                locVaCapNhatDanhSach();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        chipGroupLoc.setOnCheckedChangeListener((group, checkedId) -> {
            boLocDaChonId = (checkedId == View.NO_ID) ? -1 : checkedId;
            locVaCapNhatDanhSach();
        });

        locVaCapNhatDanhSach();
    }

    private void tinhToanVaCapNhatKhoangCach(List<quanAn> danhSach) {
        Random random = new Random();
        for (quanAn quanAnHienTai : danhSach) {
            double khoangCachKm = 0.1 + (10.0 - 0.1) * random.nextDouble();
            quanAnHienTai.setKhoangCach(String.format(Locale.US, "%.1f km", khoangCachKm));
        }
    }

    private void locVaCapNhatDanhSach() {
        Log.d(TAG, "Locating: Keyword='" + tuKhoaTimKiem + "', FilterID=" + boLocDaChonId);
        List<quanAn> ketQuaLocTamThoi = new ArrayList<>();

        // 1. Lọc theo từ khóa
        if (tuKhoaTimKiem.isEmpty()) {
            ketQuaLocTamThoi.addAll(danhSachQuanAnGoc);
        } else {
            for (quanAn qa : danhSachQuanAnGoc) {
                if (qa.getTenQuan().toLowerCase(Locale.getDefault()).contains(tuKhoaTimKiem)) {
                    ketQuaLocTamThoi.add(qa);
                }
            }
        }
        Log.d(TAG, ketQuaLocTamThoi.size() + " items after keyword filter.");

        // 2. Sắp xếp theo bộ lọc (nếu có)
        if (boLocDaChonId == R.id.chipGanToi) {
            Collections.sort(ketQuaLocTamThoi, Comparator.comparingDouble(qa ->
                    parseKhoangCach(qa.getKhoangCach())
            ));
            Log.d(TAG, "Sorted by distance.");
        } else if (boLocDaChonId == R.id.chipDanhGia) {
            Collections.sort(ketQuaLocTamThoi, (qa1, qa2) ->
                    Double.compare(parseDanhGia(qa2.getDanhGia()), parseDanhGia(qa1.getDanhGia()))
            );
            Log.d(TAG, "Sorted by rating.");
        } else {
            Log.d(TAG, "No sorting filter applied.");
        }

        // 3. Cập nhật RecyclerView
        danhSachHienThi.clear();
        danhSachHienThi.addAll(ketQuaLocTamThoi);
        adapterKetQua.notifyDataSetChanged();
        Log.d(TAG, "RecyclerView updated with " + danhSachHienThi.size() + " items.");

        // (Tùy chọn) Cuộn lên đầu danh sách sau khi lọc
        if (!danhSachHienThi.isEmpty()) {
            rvKetQuaTimKiem.scrollToPosition(0);
        }
    }

    private double parseKhoangCach(String khoangCachStr) {
        if (khoangCachStr == null || khoangCachStr.isEmpty()) return Double.MAX_VALUE;
        try {
            String numberStr = khoangCachStr.replace(" km", "").trim().replace(',', '.');
            return Double.parseDouble(numberStr);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error parsing distance: " + khoangCachStr, e);
            return Double.MAX_VALUE;
        }
    }

    private double parseDanhGia(String danhGiaStr) {
        if (danhGiaStr == null || danhGiaStr.isEmpty()) return 0.0;
        try {
            // Tìm số đầu tiên sau ký tự không phải số/dấu '.'
            int startIndex = 0;
            while (startIndex < danhGiaStr.length() && !Character.isDigit(danhGiaStr.charAt(startIndex)) && danhGiaStr.charAt(startIndex) != '.') {
                startIndex++;
            }
            if (startIndex >= danhGiaStr.length()) return 0.0; // Không tìm thấy số

            int endIndex = startIndex;
            while (endIndex < danhGiaStr.length() && (Character.isDigit(danhGiaStr.charAt(endIndex)) || danhGiaStr.charAt(endIndex) == '.')) {
                endIndex++;
            }
            String numberStr = danhGiaStr.substring(startIndex, endIndex).replace(',', '.');
            Log.v(TAG, "Parsing rating: '" + danhGiaStr + "' -> '" + numberStr + "'"); // Verbose log
            return Double.parseDouble(numberStr);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            Log.e(TAG, "Error parsing rating: " + danhGiaStr, e);
            return 0.0;
        }
    }
}