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

public class GetProcessState extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);
		
		String id=session.getAttribute("id").toString();
		String pid=session.getAttribute("pid").toString();
		
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
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			PreparedStatement p=con.prepareStatement("select started from process where id=?");
			
			p.setInt(1, Integer.parseInt(pid));
			
			ResultSet rs=p.executeQuery();
			
			JSONObject mainObj=new JSONObject();
			
			rs.next();
		
			int started=rs.getInt("started");
				
			if(started==0)
			{
				mainObj.put("started", false);
			}
			else
			{
				mainObj.put("started",true);
				
				p=con.prepareStatement("select currentqn from candidate where id=?");
				
				p.setInt(1, Integer.parseInt(id));
				
				rs=p.executeQuery();
				
				rs.next();
				
				int currentqn=rs.getInt("currentqn");
				
				while(currentqn==0)
				{
					rs=p.executeQuery();
					
					rs.next();
					
					currentqn=rs.getInt("currentqn");
				}
				
				p=con.prepareStatement("select id,qndesc from question where id=?");
				
				p.setInt(1, currentqn);
				
				System.out.println("Current Qn:"+currentqn);
				
				rs=p.executeQuery();
				
				rs.next();
				
				mainObj.put("qid",rs.getInt("id"));
				mainObj.put("question", rs.getString("qndesc"));

			}
			out.print(mainObj);
				
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}

}
