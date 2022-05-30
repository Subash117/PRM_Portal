package com.portal.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String address=request.getParameter("add");
		String mob=request.getParameter("mob");
		String pid=request.getParameter("pid");
		String clg=request.getParameter("clg");
		String year=request.getParameter("year");
		String pass=request.getParameter("pass");
		try
		{ 
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			PreparedStatement p=con.prepareStatement("insert into candidate(name,email,pass,mobile,address,college,clgyear,pid) values(?,?,?,?,?,?,?,?)");
			
			p.setString(1, name);
			p.setString(2, email);
			p.setString(3, pass);
			p.setString(4, mob);
			p.setString(5, address);
			p.setString(6, clg);
			p.setInt(7, Integer.parseInt(year));
			p.setInt(8, Integer.parseInt(pid));
			
			p.executeUpdate();
			
			response.sendRedirect("http://localhost:4200/login");
			 
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		}
	}

