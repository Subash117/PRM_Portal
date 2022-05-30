package com.portal.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);
		String id=session.getAttribute("id").toString();
		
		
		
		if(id.equals("admin"))
		{
			session.invalidate();
		}
		else
		{
			try
			{ 
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");  
				
				
				PreparedStatement p=con.prepareStatement("update candidate set sesid=? where id=?");
				
				p.setString(1,null);
				p.setInt(2,Integer.parseInt(id));
				
				p.executeUpdate();
				session.invalidate();
				
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		System.out.println("Logged Out: "+id);
		response.sendRedirect("http://localhost:4200");
	}

}
