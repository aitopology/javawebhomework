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


public class LoginServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConn();
            String sql = "select * from udata where "+
                    "username=? and password=?";//拼接sql语句
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");

                // 输出提示信息（可选）
                PrintWriter out = response.getWriter();
                out.println("<h3>页面将在3秒后自动跳转...</h3>");
                //response.sendRedirect(request.getContextPath()+"/index.html");

                // 设置Refresh头，3秒后跳转到目标URL
                String targetUrl = request.getContextPath()+"/index.html"; // 替换为实际URL
                response.setHeader("Refresh", "3; url=" + targetUrl);// 设置3秒后跳转的URL


                //PrintWriter pw = response.getWriter();
                //pw.println("登录成功");
                //pw.close();
            }else {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                PrintWriter pw = response.getWriter();
                pw.println("用户名或密码错误");
                pw.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
