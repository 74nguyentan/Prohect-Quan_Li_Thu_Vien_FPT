/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Helper.DateHelper;
import java.util.Date;

/**
 *
 * @author Van Tan Exfick
 */
public class KhoaHocK {

    private int MaKH;
    private String MaCD;
    private Double HocPhi;
    private int ThoiLuong;
    private Date NgayKG;
    private String GhiChu;
    private String MaNV;
    private Date NgayTao = DateHelper.now();

    @Override
    public String toString() {
        return this.MaCD + " (" + this.NgayKG + ")";
    }

    public KhoaHocK() {
    }

    public KhoaHocK(int MaKH, String MaCD, Double HocPhi, int ThoiLuong, Date NgayKG, String GhiChu, String MaNV, Date NgayTao) {
        this.MaKH = MaKH;
        this.MaCD = MaCD;
        this.HocPhi = HocPhi;
        this.ThoiLuong = ThoiLuong;
        this.NgayKG = NgayKG;
        this.GhiChu = GhiChu;
        this.MaNV = MaNV;
        this.NgayTao = NgayTao;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
    }

    public Double getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(Double HocPhi) {
        this.HocPhi = HocPhi;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    public Date getNgayKG() {
        return NgayKG;
    }

    public void setNgayKG(Date NgayKG) {
        this.NgayKG = NgayKG;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

}
