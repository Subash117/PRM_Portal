package com.portal.servlets;

import java.io.BufferedReader;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;


public class SetAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        
        HttpSession session= request.getSession(false);
        String id=session.getAttribute("id").toString();
        String pid=session.getAttribute("pid").toString();
        
//        BufferedReader reader = request.getReader();
//		
//		Gson gson = new Gson();
//
//		Answer set = gson.fromJson(reader, Answer.class);
//		
//		System.out.println(set.getAnswer());
        
        Answer set=new Answer();
        set.setQnNo(Integer.parseInt(request.getParameter("qno")));
        set.setAnswer(request.getParameter("ans"));
        
        System.out.print(set.answer);
		
		JSONObject jo=new JSONObject();
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			
			int qnid=set.qnNo;
			
			PreparedStatement p=con.prepareStatement("insert into answer(pid,uid,ans,qno) values(?,?,?,?)");
			
			p.setInt(1, Integer.parseInt(pid));
			p.setInt(2, Integer.parseInt(id));
			p.setString(3, set.getAnswer());
			p.setInt(4, qnid);
			
			p.executeUpdate();
			
			System.out.println(qnid);
			
			p=con.prepareStatement("select status from answer where qno=?");
			
			p.setInt(1, qnid);
			
			ResultSet rs=p.executeQuery();
			
			rs.next();
			
			String status=rs.getString("status");
			
			while(status==null)
			{
				rs=p.executeQuery();
				rs.next();
				status=rs.getString("status");
			}
			
			System.out.print("Received Status "+status);
			
			if(status.equals("correct") || status.equals("partial"))
			{
				p=con.prepareStatement("select id from question where id>? and pid=?");
				
				p.setInt(1, qnid);
				p.setInt(2, Integer.parseInt(pid));
				
				rs=p.executeQuery();
				
				if(rs.next())
				{
					qnid=rs.getInt("id");
					p=con.prepareStatement("update candidate set currentqn=?");
					
					p.setInt(1, qnid);
					
					System.out.println("Moved to next Question");
					p.executeUpdate();
					response.sendRedirect("http://localhost:4200/dashboard");
				}
				else
				{
					response.getWriter().print("You successfully completed the exam!..");
				}	
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.getWriter().print(jo);
        
	}

}
