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

import model.HocVienH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HocVienDAO {

    public void insertHV(HocVienH model) {
        String sql = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
        jdbcDao.executeUpdate(sql,model.getMaKH(),model.getMaNH(),model.getDiem());
        System.out.println(model.getMaKH());
        System.out.println(model.getMaNH());
        System.out.println(model.getDiem());
    }

    public void updateHV(HocVienH model) {
        String sql = "UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
        jdbcDao.executeUpdate(sql,model.getMaKH(), model.getMaNH(), model.getDiem(),model.getMaHV());
    }

    public void deleteHV(Integer MaHV) {
        String sql = "DELETE FROM HocVien WHERE MaHV=?";
        jdbcDao.executeUpdate(sql, MaHV);
    }

    public List<HocVienH> selectHV() {
        String sql = "SELECT * FROM HocVien";
        return select(sql);
    }

    public HocVienH findByIdHV(Integer mahv) {
        String sql = "SELECT * FROM HocVien WHERE MaHV=?";
        List<HocVienH> list = select(sql, mahv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HocVienH> select(String sql, Object... args) {
        List<HocVienH> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcDao.executeQuery(sql, args);
                while (rs.next()) {
                    HocVienH model = readFromResultSet(rs);
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

    private HocVienH readFromResultSet(ResultSet rs) throws SQLException {
        HocVienH model = new HocVienH();
        model.setMaHV(rs.getInt("MaHV"));
        model.setMaKH(rs.getInt("KH"));
        model.setMaNH(rs.getString("MaNH"));
        model.setDiem(rs.getDouble("Diem"));
        return model;
    }
}
