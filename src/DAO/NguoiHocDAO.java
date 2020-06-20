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
import model.NguoiHocN;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguoiHocDAO {

    public void insertNH(NguoiHocN model) {
        String sql = "INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(model.getMaNV());
        jdbcDao.executeUpdate(sql, model.getMaNH(), model.getHoTen(), model.getNgaySinh(), model.getGioiTinh(), model.getDienThoai(), model.getEmail(), model.getGhiChu(), model.getMaNV());
    }

    public void updateNH(NguoiHocN model) {
        String sql = "UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?,MaNV=? WHERE MaNH=?";
        jdbcDao.executeUpdate(sql, model.getHoTen(), model.getNgaySinh(), model.getGioiTinh(), model.getDienThoai(), model.getEmail(), model.getGhiChu(), model.getMaNV(), model.getMaNH());
    }

    public void deleteNH(String id) {
        String sql = "DELETE FROM NguoiHoc WHERE MaNH=?";
        jdbcDao.executeUpdate(sql, id);
    }

    public List<NguoiHocN> selectNH() {
        String sql = "SELECT * FROM NguoiHoc";
        return select(sql);
    }

    public List<NguoiHocN> selectByKeywordNH(String keyword) {
        String sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

    public List<NguoiHocN> selectByCourseNH(Integer makh) {
        String sql = "SELECT * FROM NguoiHoc WHERE MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
        return select(sql, makh);
    }

    public NguoiHocN findByIdNH(String manh) {
        String sql = "SELECT * FROM NguoiHoc WHERE MaNH=?";
        List<NguoiHocN> list = select(sql, manh);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NguoiHocN> select(String sql, Object... args) {
        List<NguoiHocN> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcDao.executeQuery(sql, args);
                while (rs.next()) {
                    NguoiHocN model = readFromResultSet(rs);
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

    private NguoiHocN readFromResultSet(ResultSet rs) throws SQLException {
        NguoiHocN model = new NguoiHocN();
        model.setMaNH(rs.getString("MaNH"));
        model.setHoTen(rs.getString("HoTen"));
        model.setNgaySinh(rs.getDate("NgaySinh"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        model.setDienThoai(rs.getString("DienThoai"));
        model.setEmail(rs.getString("Email"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayDK(rs.getDate("NgayDK"));
        return model;
    }
}
