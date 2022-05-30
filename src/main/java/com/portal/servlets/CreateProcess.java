package com.portal.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class CreateProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        
		BufferedReader reader = request.getReader();
		
		Gson gson = new Gson();

		QuestionSet set = gson.fromJson(reader, QuestionSet.class);
		
		Random rand=new Random();
		int pid=rand.nextInt();
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			PreparedStatement p=con.prepareStatement("select id from process where id=?");
			
			p.setInt(1, pid);
			
			ResultSet rs=p.executeQuery();
			
			while(rs.next())
			{
				pid=rand.nextInt();
				p.setInt(1, pid);
				rs=p.executeQuery();
			}
			
			p=con.prepareStatement("insert into process(id,name,started) values(?,?,?)");
			
			p.setInt(1, pid);
			p.setString(2, set.getName());
			p.setInt(3, 0);
			
			p.executeUpdate();
			
			p=con.prepareStatement("insert into question(qndesc,pid) values(?,?)");
			
			p.setInt(2, pid);
			String[] temp=set.getQuestions();
			
			for(String s:temp)
			{
				p.setString(1, s);
				p.executeUpdate();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
        
		JSONObject mainObj=new JSONObject();
		try {
			mainObj.put("Done", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.getWriter().print(mainObj);
	}
}
