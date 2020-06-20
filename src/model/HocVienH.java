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
public class HocVienH {

    private int MaHV;
    private int MaKH;
    private String MaNH;
    private double Diem;

    @Override
    public String toString() {
        return this.toString();
    }

    public HocVienH() {
    }

    public HocVienH(int MaHV, int MaKH, String MaNH, double Diem) {
        this.MaHV = MaHV;
        this.MaKH = MaKH;
        this.MaNH = MaNH;
        this.Diem = Diem;
    }

    public int getMaHV() {
        return MaHV;
    }

    public void setMaHV(int MaHV) {
        this.MaHV = MaHV;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaNH() {
        return MaNH;
    }

    public void setMaNH(String MaNH) {
        this.MaNH = MaNH;
    }

    public double getDiem() {
        return Diem;
    }

    public void setDiem(double Diem) {
        this.Diem = Diem;
    }

}
