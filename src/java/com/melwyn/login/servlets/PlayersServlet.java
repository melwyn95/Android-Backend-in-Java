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
 
public class PlayersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        String sql = "select * from android_player where team_id = ?";
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(params[0]));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                json = new JSONObject();
                
                json.put("player_id", rs.getInt("player_id"));
                json.put("team_id", rs.getInt("team_id"));
                json.put("player_name", rs.getString("player_name"));
                json.put("player_skill", rs.getInt("player_skill"));
                
                jarray.add(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JSONArray newjsonarray = jarray;
        Iterator<JSONObject> j = newjsonarray.iterator();
        while (j.hasNext()) {
            System.out.println("Team Name : " + j.next().get("player_name"));
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