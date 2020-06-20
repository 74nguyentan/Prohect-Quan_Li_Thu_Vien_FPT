/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Van Tan Exfick
 */
public class ChuyenDeD {

    private String MaCD;
    private String TenCD;
    private double HocPhi;
    private int ThoiLuong;
    private String Hinh;
    private String MoTa;

    @Override
    public String toString() {
        return this.TenCD;
    }

    public ChuyenDeD() {
    }

    public ChuyenDeD(String MaCD, String TenCD, double HocPhi, int ThoiLuong, String Hinh, String MoTa) {
        this.MaCD = MaCD;
        this.TenCD = TenCD;
        this.HocPhi = HocPhi;
        this.ThoiLuong = ThoiLuong;
        this.Hinh = Hinh;
        this.MoTa = MoTa;
    }

    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
    }

    public String getTenCD() {
        return TenCD;
    }

    public void setTenCD(String TenCD) {
        this.TenCD = TenCD;
    }

    public double getHocPhi() {
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

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

}
