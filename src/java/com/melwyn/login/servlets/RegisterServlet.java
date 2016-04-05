package com.melwyn.login.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.Enumeration;
import org.json.simple.JSONObject;
 
public class RegisterServlet extends HttpServlet {
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
         
 
        //ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
        Enumeration paramNames = request.getParameterNames();
        String params[] = new String[2];
        int i = 0;
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
 
            //System.out.println(paramName);
            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
 
            //System.out.println(params[i]);
            i++;
 
        }
 
        String sql = "insert into android_users(uname, pass) values (?, ?)";
        Connection con = DbConnection.getConnection();
         
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, params[0]);
            ps.setString(2, params[1]);
            //ps.setString(1, "admin");
            //ps.setString(2, "123");
            //ResultSet rs = ps.executeQuery();
            int count = ps.executeUpdate();
            if (count > 0) {
                json.put("info", "success");
            } else {
                json.put("info", "fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(json);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println(json);
        System.out.println(json.toString());
        response.getWriter().write(json.toString());
    }
 
    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}