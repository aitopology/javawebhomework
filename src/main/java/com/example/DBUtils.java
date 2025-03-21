package com.example;

import java.sql.*;


public class DBUtils {
    //getConn

    public static Connection getConn() throws SQLException {
        Connection conn = null;
        try {
            //oracle
            Class.forName("oracle.jdbc.OracleDriver");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "123456");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle JDBC驱动加载失败", e);
        }
        return conn;
    }
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
