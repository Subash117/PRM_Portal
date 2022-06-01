package com.portal.servlets;

import java.io.IOException;
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

public class GetResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);
		
		String id=session.getAttribute("id").toString();
		
		String uid=request.getParameter("uid");
		
		response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
		
		if(id.equals("admin"))
		{
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
				
				PreparedStatement p=con.prepareStatement("select qno,status,start_time,end_time from answer where uid=? and status is not null");
				p.setInt(1, Integer.parseInt(uid));
				
				ResultSet rs=p.executeQuery();
				
				JSONObject main=new JSONObject();
				
				JSONArray ja=new JSONArray();
				
				while(rs.next())
				{
					JSONObject jo=new JSONObject();
					
					jo.put("qno", rs.getInt("qno"));
					jo.put("status", rs.getString("status"));
					jo.put("start_time", rs.getTime("start_time"));
					jo.put("end_time", rs.getTime("end_time"));
					
					ja.put(jo);
				}
				
				p=con.prepareStatement("select name from candidate where id=?");
				p.setInt(1, Integer.parseInt(uid));
				
				rs=p.executeQuery();
				
				rs.next();
				
				main.put("name", rs.getString("name"));
				main.put("id", uid);
				main.put("results", ja);
				
				response.getWriter().print(main);
				
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
	}

}
