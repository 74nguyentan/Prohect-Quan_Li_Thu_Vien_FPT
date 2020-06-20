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
import model.ChuyenDeD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChuyenDeDAO {

    public void insertCD(ChuyenDeD model) {
        String sql = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcDao.executeUpdate(sql, model.getMaCD(), model.getTenCD(), model.getHocPhi(), model.getThoiLuong(), model.getHinh(), model.getMoTa());
    }

    public void updateCD(ChuyenDeD model) {
        String sql = "UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
        jdbcDao.executeUpdate(sql, model.getTenCD(), model.getHocPhi(), model.getThoiLuong(), model.getHinh(), model.getMoTa(), model.getMaCD());
    }

    public void deleteCD(String MaCD) {
        String sql = "DELETE FROM ChuyenDe WHERE MaCD=?";
        jdbcDao.executeUpdate(sql, MaCD);
    }

    public List<ChuyenDeD> selectCD() {
        String sql = "SELECT * FROM ChuyenDe";
        return select(sql);
    }

    public ChuyenDeD findByIdCD(String macd) {
        String sql = "SELECT * FROM ChuyenDe WHERE MaCD=?";
        List<ChuyenDeD> list = select(sql, macd);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<ChuyenDeD> select(String sql, Object... args) {
        List<ChuyenDeD> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcDao.executeQuery(sql, args);
                while (rs.next()) {
                    ChuyenDeD model = readFromResultSet(rs);
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

    private ChuyenDeD readFromResultSet(ResultSet rs) throws SQLException {
        ChuyenDeD model = new ChuyenDeD();
        model.setMaCD(rs.getString("MaCD"));
        model.setHinh(rs.getString("Hinh"));
        model.setHocPhi(rs.getDouble("HocPhi"));
        model.setMoTa(rs.getString("MoTa"));
        model.setTenCD(rs.getString("TenCD"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        return model;
    }
}
