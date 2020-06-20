/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.NhanVienV;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public void insertNV(NhanVienV model) {
        String sql = "INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)";
        jdbcDao.executeUpdate(sql,
                model.getMaNV(),
                model.getMatKhau(),
                model.getHoTen(),
                model.getVaiTro());
    }

    public void updateNV(NhanVienV model) {
        String sql = "UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
        jdbcDao.executeUpdate(sql,
                model.getMatKhau(),
                model.getHoTen(),
                model.getVaiTro(),
                model.getMaNV());
    }
    
     public void UpdateMK(NhanVienV model) {
        String sql = " update Userr set MatKhau=? where MaNV =?";
        jdbcDao.executeUpdate(sql, model.getMatKhau(), model.getMaNV());
    }

    public void deleteNV(String MaNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        jdbcDao.executeUpdate(sql, MaNV);
    }

    public List<NhanVienV> selectNV() {
        String sql = "SELECT * FROM NhanVien";
        return select(sql); 
    }

    public NhanVienV findByIdNV(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVienV> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhanVienV> select(String sql, Object... args) {
        List<NhanVienV> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcDao.executeQuery(sql, args);
                while (rs.next()) {
                    NhanVienV model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private NhanVienV readFromResultSet(ResultSet rs) throws SQLException {
        NhanVienV model = new NhanVienV();
        model.setMaNV(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setHoTen(rs.getString("HoTen"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        return model;
    }
}
