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
public class NhanVienV {

    private String MaNV;
    private String MatKhau;
    private String HoTen;
    private Boolean VaiTro = false;

    @Override
    public String toString() {
        return this.HoTen;
    }

    public NhanVienV() {
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public Boolean getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(Boolean VaiTro) {
        this.VaiTro = VaiTro;
    }

}
