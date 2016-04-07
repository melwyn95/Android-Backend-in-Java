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
 
public class DetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONArray jarray = new JSONArray();
        JSONObject json = null;
        Enumeration paramNames = request.getParameterNames();
        String sql = null;
        String params[] = new String[2];
        int i = 0;
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
            i++;
        }
        if (params[0].equals("1"))
            sql = "select * from android_batsman where player_id = ?";
        else if (params[0].equals("2"))
            sql = "select * from android_bowler where player_id = ?";
        
        Connection con = DbConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(params[1]));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                json = new JSONObject();
                
                switch (params[0]) {
                    case "1":
                        json.put("player_id", rs.getInt("player_id"));
                        json.put("player_runs", rs.getInt("player_runs"));
                        json.put("player_matches", rs.getInt("player_matches"));
                        json.put("player_avg", rs.getInt("player_avg"));
                        json.put("player_sr", rs.getInt("player_sr"));
                        json.put("player_best", rs.getInt("player_best"));
                        json.put("player_catches", rs.getInt("player_catches"));
                        json.put("player_stumping", rs.getInt("player_stumping"));
                        break;
                    case "2":
                        json.put("player_id", rs.getInt("player_id"));
                        json.put("player_wickets", rs.getInt("player_wickets"));
                        json.put("player_overs", rs.getInt("player_overs"));
                        json.put("player_matches", rs.getInt("player_matches"));
                        json.put("player_avg", rs.getInt("player_avg"));
                        json.put("player_economy", rs.getInt("player_economy"));
                        json.put("player_best", rs.getString("player_best"));
                        json.put("player_maidens", rs.getInt("player_maidens"));
                        json.put("player_runs", rs.getInt("player_runs"));
                        break;
                }
                
                jarray.add(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JSONArray newjsonarray = jarray;
        Iterator<JSONObject> j = newjsonarray.iterator();
        while (j.hasNext()) {
            System.out.println("Team Name : " + j.next().get("player_id"));
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