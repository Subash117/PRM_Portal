package com.portal.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;


public class GetAllQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pid=request.getParameter("pid");
		
		PrintWriter out=response.getWriter();
		
		response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
		
        try
        {
        	JSONObject main=new JSONObject();
        	
        	Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			PreparedStatement p=con.prepareStatement("select id,qndesc from question where pid=?");
			p.setInt(1,Integer.parseInt(pid));
			
			ResultSet rs=p.executeQuery();
			
			JSONArray ja=new JSONArray();
			
			while(rs.next())
			{
				JSONObject jo=new JSONObject();
				
				jo.put("id", rs.getInt("id"));
				jo.put("question", rs.getString("qndesc"));
				
				ja.put(jo);
			}
			main.put("questions", ja);
			
			out.print(main);
        }
        catch(Exception e)
        {
        	System.out.println(e);
        }
	}

}
