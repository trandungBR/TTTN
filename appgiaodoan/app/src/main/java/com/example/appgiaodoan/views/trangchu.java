package com.example.appgiaodoan.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView; // *** THÊM IMPORT ***
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgiaodoan.R;
import com.example.appgiaodoan.adapters.quanAnAdapter;
import com.example.appgiaodoan.models.giaLap_trangChu;
import com.example.appgiaodoan.models.quanAn;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class trangchu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private static final String TAG = "";

    private RecyclerView recyclerViewQuanAn;
    private quanAnAdapter adapterQuanAn;
    private List<quanAn> danhSachQuanAnGoc;
    private List<quanAn> danhSachHienThi;
    private DrawerLayout drawerLayoutMenu;
    private NavigationView navigationViewMenu;
    private ImageView imageViewMenu;
    private ImageView imageViewAvatarNav;
    private TextView textViewTenNguoiDungNav;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        recyclerViewQuanAn = findViewById(R.id.recyclerViewQuanAn);
        drawerLayoutMenu = findViewById(R.id.drawer_layout);
        navigationViewMenu = findViewById(R.id.nav_view);
        imageViewMenu = findViewById(R.id.imageViewMenu);
        searchView = findViewById(R.id.searchView);

        recyclerViewQuanAn.setLayoutManager(new LinearLayoutManager(this));

        giaLap_trangChu nguonDuLieuQuanAn = new giaLap_trangChu();
        danhSachQuanAnGoc = nguonDuLieuQuanAn.taoDuLieuGiaLap();
        danhSachHienThi = new ArrayList<>(danhSachQuanAnGoc);

        tinhToanVaCapNhatKhoangCach(0.0, 0.0);

        adapterQuanAn = new quanAnAdapter(this, danhSachHienThi);
        recyclerViewQuanAn.setAdapter(adapterQuanAn);

        imageViewMenu.setOnClickListener(v -> {
            Log.d(TAG, "imageViewMenu clicked!");
            if (drawerLayoutMenu != null) {
                if (drawerLayoutMenu.isDrawerOpen(GravityCompat.END)) {
                    drawerLayoutMenu.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayoutMenu.openDrawer(GravityCompat.END);
                }
            } else {
                Log.e(TAG, "drawerLayoutMenu is NULL!");
            }
        });

        searchView.setOnQueryTextListener(this);

        if (navigationViewMenu != null) {
            View headerView = navigationViewMenu.getHeaderView(0);
            if (headerView != null) {
                imageViewAvatarNav = headerView.findViewById(R.id.ivAvatar);
                textViewTenNguoiDungNav = headerView.findViewById(R.id.tvTenNguoiDungNav);

                if (textViewTenNguoiDungNav != null) {
                    textViewTenNguoiDungNav.setText("Nguyen Van Test");
                    textViewTenNguoiDungNav.setOnClickListener(v -> {
                        Toast.makeText(trangchu.this, "Bấm vào Tên!", Toast.LENGTH_SHORT).show();
                    });
                }
                if (imageViewAvatarNav != null) {
                    imageViewAvatarNav.setOnClickListener(v -> {
                        Toast.makeText(trangchu.this, "Bấm vào Avatar!", Toast.LENGTH_SHORT).show();
                    });
                }

            }
            navigationViewMenu.setNavigationItemSelectedListener(this);
        } else {
            Log.e(TAG, "navigationViewMenu is NULL!");
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayoutMenu != null && drawerLayoutMenu.isDrawerOpen(GravityCompat.END)) {
                    drawerLayoutMenu.closeDrawer(GravityCompat.END);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void tinhToanVaCapNhatKhoangCach(double viDoNguoiDung, double kinhDoNguoiDung) {
        Random random = new Random();
        for (quanAn quanAnHienTai : danhSachQuanAnGoc) {
            double khoangCachKm = 0.5 + (5.0 - 0.5) * random.nextDouble();
            quanAnHienTai.setKhoangCach(String.format("%.1f km", khoangCachKm));
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        locDanhSachQuanAn(newText);
        return true;
    }

    private void locDanhSachQuanAn(String tuKhoa) {
        String tuKhoaTimKiem = tuKhoa.toLowerCase(Locale.getDefault()).trim();
        danhSachHienThi.clear();

        if (tuKhoaTimKiem.isEmpty()) {
            danhSachHienThi.addAll(danhSachQuanAnGoc);
        } else {
            for (quanAn qa : danhSachQuanAnGoc) {
                if (qa.getTenQuan().toLowerCase(Locale.getDefault()).contains(tuKhoaTimKiem)) {
                    danhSachHienThi.add(qa);
                }
            }
        }
        adapterQuanAn.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_lich_su) {
            Toast.makeText(this, "Chức năng Lịch sử", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_yeu_thich) {
            Toast.makeText(this, "Chức năng Yêu thích", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_cai_dat) {
            Toast.makeText(this, "Chức năng Cài đặt", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_dang_xuat) {
            Toast.makeText(this, "Đăng xuất...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, dangnhap.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        if (drawerLayoutMenu != null) {
            drawerLayoutMenu.closeDrawer(GravityCompat.END);
        }
        return true;
    }
}