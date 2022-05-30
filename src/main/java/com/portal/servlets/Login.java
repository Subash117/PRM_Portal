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


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id;
		String email=request.getParameter("email");
		String pass=request.getParameter("pass");
		String c=request.getParameter("class");
		
		
		if(c.equals("admin"))
		{
			if(email.equals("admin") && pass.equals("admin"))
			{
				HttpSession session=request.getSession();
				
				System.out.println("Logged In: admin");
				session.setAttribute("id","admin");
				response.sendRedirect("http://localhost:4200/admin-dashboard");
			}
		}
		
		else
		{
		PrintWriter out=response.getWriter();
		
		try
		{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");  
			
			
			PreparedStatement p=con.prepareStatement("select id,pid from candidate where email=? and pass=?");
			
			p.setString(1,email);  
			p.setString(2,pass);
			
			ResultSet rs=p.executeQuery(); 
			
			if(!rs.next())
			{
				out.print("Login Failed Email/Password Incorrect");
			}
			else
			{
				id=rs.getInt("id");
				String pid=rs.getString("pid");
				p=con.prepareStatement("update candidate set sesid=? where id=?;");

				HttpSession session=request.getSession();

				session.setAttribute("id",id);
				session.setAttribute("pid", pid);
				String sesid=session.getId();
				
				p.setString(1, sesid);
				p.setInt(2, id);
				
				p.executeUpdate();
				
				System.out.println("Logged In : "+id);
				
				response.sendRedirect("http://localhost:4200/dashboard");
			}
			con.close();
		}
			catch(Exception e){ 
				System.out.println(e);
			}
		}
	}

}
