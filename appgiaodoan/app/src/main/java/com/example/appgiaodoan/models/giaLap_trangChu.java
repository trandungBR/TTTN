package com.example.appgiaodoan.models;

import java.util.ArrayList;
import java.util.List;

public class giaLap_trangChu {

    public List<quanAn> taoDuLieuGiaLap() {
        List<quanAn> danhSachQuanAn = new ArrayList<>();

        danhSachQuanAn.add(new quanAn(
                "Cơm Tấm Phúc Lộc Thọ",
                "123 Nguyễn Văn Cừ, Q.5, TP.HCM",
                "⭐ 4.5 (1k+)",
                "https://placehold.co/600x400/FF5722/FFFFFF?text=Com+Tam"
        ));
        danhSachQuanAn.add(new quanAn(
                "Phở Thìn Bò Khô",
                "456 Lê Lợi, Q.1, TP.HCM",
                "⭐ 4.8 (500+)",
                "https://placehold.co/600x400/4CAF50/FFFFFF?text=Pho+Thin"
        ));
        danhSachQuanAn.add(new quanAn(
                "Bún Đậu Mẹt",
                "789 Trần Hưng Đạo, Q.3, TP.HCM",
                "⭐ 4.2 (200+)",
                "https://placehold.co/600x400/2196F3/FFFFFF?text=Bun+Dau"
        ));


        danhSachQuanAn.get(0).setKhoangCach("1.2 km");
        danhSachQuanAn.get(1).setKhoangCach("0.8 km");
        danhSachQuanAn.get(2).setKhoangCach("2.5 km");

        return danhSachQuanAn;
    }
}