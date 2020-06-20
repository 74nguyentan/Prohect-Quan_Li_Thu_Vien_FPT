/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author exfic
 */
public class jdbcDao {

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement preparedStatement(String sql, Object... args) throws SQLException {
        Connection connect = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DuAn1;", "sa", "songlong");
        PreparedStatement pstmt = null;
        if (sql.trim().startsWith("")) {   // "{"
            pstmt = connect.prepareCall(sql);
        } else {
            pstmt = connect.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static void executeUpdate(String sql, Object... args) {
        try {
            PreparedStatement pre = preparedStatement(sql, args);
            try {
                pre.executeUpdate();
            } finally {
                pre.getConnection().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement pre = preparedStatement(sql, args);
            return pre.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
