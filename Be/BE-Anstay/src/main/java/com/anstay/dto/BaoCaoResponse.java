package com.anstay.dto;

public class BaoCaoResponse {

    private long soLuongTruyCap=1125;
    private long soNguoiBan;
    private long soNguoiBanMoi;
    private long tongSoSanPham;
    private long soSanPhamMoi;
    private long soLuongGiaoDich;
    private long tongSoDonHangThanhCong;
    private long tongSoDongHangKhongThanhCong;
    private long tongGiaTriGiaoDich;

    // Getter and Setter
    public long getSoLuongTruyCap() {
        return soLuongTruyCap;
    }

    public void setSoLuongTruyCap(long soLuongTruyCap) {
        this.soLuongTruyCap = soLuongTruyCap;
    }

    public long getSoNguoiBan() {
        return soNguoiBan;
    }

    public void setSoNguoiBan(long soNguoiBan) {
        this.soNguoiBan = soNguoiBan;
    }

    public long getSoNguoiBanMoi() {
        return soNguoiBanMoi;
    }

    public void setSoNguoiBanMoi(long soNguoiBanMoi) {
        this.soNguoiBanMoi = soNguoiBanMoi;
    }

    public long getTongSoSanPham() {
        return tongSoSanPham;
    }

    public void setTongSoSanPham(long tongSoSanPham) {
        this.tongSoSanPham = tongSoSanPham;
    }

    public long getSoSanPhamMoi() {
        return soSanPhamMoi;
    }

    public void setSoSanPhamMoi(long soSanPhamMoi) {
        this.soSanPhamMoi = soSanPhamMoi;
    }

    public long getSoLuongGiaoDich() {
        return soLuongGiaoDich;
    }

    public void setSoLuongGiaoDich(long soLuongGiaoDich) {
        this.soLuongGiaoDich = soLuongGiaoDich;
    }

    public long getTongSoDonHangThanhCong() {
        return tongSoDonHangThanhCong;
    }

    public void setTongSoDonHangThanhCong(long tongSoDonHangThanhCong) {
        this.tongSoDonHangThanhCong = tongSoDonHangThanhCong;
    }

    public long getTongSoDongHangKhongThanhCong() {
        return tongSoDongHangKhongThanhCong;
    }

    public void setTongSoDongHangKhongThanhCong(long tongSoDongHangKhongThanhCong) {
        this.tongSoDongHangKhongThanhCong = tongSoDongHangKhongThanhCong;
    }

    public long getTongGiaTriGiaoDich() {
        return tongGiaTriGiaoDich;
    }

    public void setTongGiaTriGiaoDich(long tongGiaTriGiaoDich) {
        this.tongGiaTriGiaoDich = tongGiaTriGiaoDich;
    }
}
