/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Van Tan Exfick
 */

import model.KhoaHocK;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhoaHocDAO {

    public void insertKH(KhoaHocK model) {
        String sql = "INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?,?)";
        jdbcDao.executeUpdate(sql,model.getMaCD(),model.getHocPhi(),model.getThoiLuong(), model.getNgayKG(),model.getGhiChu(),model.getMaNV());
    }

    public void updateKH(KhoaHocK model) {
        String sql = "UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH =  ? ";
        jdbcDao.executeUpdate(sql, model.getMaCD(), model.getHocPhi(),model.getThoiLuong(),model.getNgayKG(),model.getGhiChu(), model.getMaNV(), model.getMaKH());
    }

    public void deleteKH(Integer MaKH) {
        String sql = "DELETE FROM KhoaHoc WHERE MaKH=?";
        jdbcDao.executeUpdate(sql, MaKH);
    }

    public List<KhoaHocK> selectKH() {
        String sql = "SELECT * FROM KhoaHoc";
        return select(sql);
    }

    public KhoaHocK findByIdKH(Integer makh) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaKH=?";
        List<KhoaHocK> list = select(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    public KhoaHocK findByMaCD_KH(String macd) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaCD=?";
        List<KhoaHocK> list = select(sql, macd);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<KhoaHocK> select(String sql, Object... args) {
        List<KhoaHocK> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcDao.executeQuery(sql, args);
                while (rs.next()) {
                    KhoaHocK model = readFromResultSet(rs);
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

    private KhoaHocK readFromResultSet(ResultSet rs) throws SQLException {
        KhoaHocK model = new KhoaHocK();
        model.setMaKH(rs.getInt("MaKH"));
        model.setHocPhi(rs.getDouble("HocPhi"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        model.setNgayKG(rs.getDate("NgayKG"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayTao(rs.getDate("NgayTao"));
        model.setMaCD(rs.getString("MaCD"));

        return model;
    }
}
