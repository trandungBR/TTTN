package com.example.appgiaodoan.models;

public class quanAn {
    private String tenQuan;
    private String diaChi;
    private String danhGia;
    private String hinhAnhUrl;


    private String khoangCach = "";

    public quanAn(String tenQuan, String diaChi, String danhGia, String hinhAnhUrl) {
        this.tenQuan = tenQuan;
        this.diaChi = diaChi;
        this.danhGia = danhGia;
        this.hinhAnhUrl = hinhAnhUrl;
    }

    public String getTenQuan() { return tenQuan; }
    public String getDiaChi() { return diaChi; }
    public String getDanhGia() { return danhGia; }
    public String getHinhAnhUrl() { return hinhAnhUrl; }

    public String getKhoangCach() { return khoangCach; }
    public void setKhoangCach(String khoangCach) {
        this.khoangCach = khoangCach;
    }
}