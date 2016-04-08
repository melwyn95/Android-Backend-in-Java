package com.melwyn.login.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.Enumeration;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class TeamsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //JSONObject json = new JSONObject();
        //JSONArray jarray = new JSONArray();
        JSONArray jarray = new JSONArray();
        JSONObject json = null;
        Enumeration paramNames = request.getParameterNames();
        String params[] = new String[2];
        int i = 0;
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
            i++;
        }
        //String sql = "select * from android_team where team_id = ?";
        String sql = "select * from android_team order by team_points desc";
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            //ps.setInt(1, Integer.parseInt(params[0]));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                json = new JSONObject();
                json.put("team_id", rs.getInt("team_id"));
                System.out.println("Team " + rs.getInt("team_id"));
                json.put("team_name", rs.getString("team_name"));
                json.put("team_points", rs.getInt("team_points"));
                jarray.add(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JSONArray newjsonarray = jarray;
        Iterator<JSONObject> j = newjsonarray.iterator();
        while (j.hasNext()) {
            System.out.println("Team Name : " + j.next().get("team_name"));
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println(jarray.toJSONString());
        System.out.println(jarray.toString());
        response.getWriter().write(jarray.toString());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}