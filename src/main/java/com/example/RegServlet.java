package com.example;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("username="+username+",password="+password);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConn();
            String sql = "select * from udata where username=?";
            ps = conn.prepareStatement(sql);//预编译sql语句，减少sql执行
            ps.setString(1, username);//设置参数
            rs = ps.executeQuery();//执行sql语句

            if (rs.next()) {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                PrintWriter pw = response.getWriter();
                pw.println("用户名已存在");
                pw.close();
            }else {
                String sql2 = "insert into udata values(?,?)";
                ps = conn.prepareStatement(sql2);
                ps.setString(1, username);
                ps.setString(2, password);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    response.setContentType("text/html;charset=UTF-8");
                    response.sendRedirect(request.getContextPath()+"/login.html");
                }
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常，请检查日志");
            e.printStackTrace();
        }finally {
            DBUtils.close(conn, ps, rs);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
