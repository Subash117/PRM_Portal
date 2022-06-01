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

public class SetStartTime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);
		
		int id=Integer.parseInt(session.getAttribute("id").toString());
		int pid=Integer.parseInt(session.getAttribute("pid").toString());
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			PreparedStatement p=con.prepareStatement("select currentqn from candidate where id=?");
			p.setInt(1, id);
			
			ResultSet rs=p.executeQuery();
			
			rs.next();
			
			int currentqn=rs.getInt("currentqn");
			
			p=con.prepareStatement("select * from answer where uid=? and qno=?");
			
			p.setInt(1, id);
			p.setInt(2, currentqn);
			
			rs=p.executeQuery();
			
			if(!rs.next())
			{
			p=con.prepareStatement("insert into answer(uid,pid,qno,start_time) values(?,?,?,current_time())");
			
			p.setInt(1,id);
			p.setInt(2, pid);
			p.setInt(3, currentqn);
			
			p.executeUpdate();
			}
			
			response.sendRedirect("http://localhost:4200/dashboard");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	
	}

}
