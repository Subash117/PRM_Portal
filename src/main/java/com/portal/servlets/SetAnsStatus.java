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

public class SetAnsStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);
		
		response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
		
		String id=session.getAttribute("id").toString();
		
		String ansid=request.getParameter("ansid");
		
		String status =request.getParameter("status");
		
		int pid=404,uid,qid;
		
		if(id.equals("admin"))
		{
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
				
				PreparedStatement p=con.prepareStatement("update answer set status=? where id=?");
				
				p.setString(1, status);
				p.setInt(2, Integer.parseInt(ansid));
				
				p.executeUpdate();
				
				p=con.prepareStatement("select uid,pid,qno from answer where id=?");
				p.setInt(1, Integer.parseInt(ansid));
				
				ResultSet rs=p.executeQuery();
				rs.next();
				
				int qnid=rs.getInt("qno");
				pid=rs.getInt("pid");
				uid=rs.getInt("uid");
				
				if(status.equals("correct") || status.equals("partial"))
				{	
					
					p=con.prepareStatement("select id from question where id>? and pid=?");
					
					p.setInt(1, qnid);
					p.setInt(2, pid);
					
					rs=p.executeQuery();
					
					if(rs.next())
					{
						qnid=rs.getInt("id");
						p=con.prepareStatement("update candidate set currentqn=? where id=?");
						
						p.setInt(1, qnid);
						p.setInt(2, uid);
						
						System.out.println("Moved to next Question");
						p.executeUpdate();
					}
					else
					{
						p=con.prepareStatement("update candidate set finished=1 where id=?");
						p.setInt(1, uid);
						
						p.executeUpdate();
					}
				}
				response.sendRedirect("http://localhost:4200/process/"+pid);
			}
			
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}

}
