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
        
        BufferedReader reader = request.getReader();
		
		Gson gson = new Gson();

		Answer set = gson.fromJson(reader, Answer.class);
		
		System.out.println(set.getAnswer());
		
		JSONObject jo=new JSONObject();
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			
			PreparedStatement p=con.prepareStatement("select id from question where pid=? sort by id");
			
			p.setInt(1,Integer.parseInt(pid));
			
			int currentqn=set.getQnNo();
			
			ResultSet rs=p.executeQuery();
			
			int temp=1;
			rs.next();
			while(temp!=currentqn)
			{
				rs.next();
				temp++;
			}
			
			int qnid=rs.getInt("id");
			
			p=con.prepareStatement("insert into answer(pid,uid,ans,qno) values(?,?,?,?)");
			
			p.setInt(1, Integer.parseInt(pid));
			p.setInt(2, Integer.parseInt(id));
			p.setString(3, set.getAnswer());
			p.setInt(4, qnid);
			
			p.executeUpdate();
			
			
			p=con.prepareStatement("select status from answer where qno=?");
			
			p.setInt(1, qnid);
			
			rs=p.executeQuery();
			
			rs.next();
			
			String status=rs.getString("status");
			
			while(status==null)
			{
				rs=p.executeQuery();
				rs.next();
				status=rs.getString("status");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.getWriter().print(jo);
        
	}

}
