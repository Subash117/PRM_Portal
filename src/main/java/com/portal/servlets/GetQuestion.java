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

import org.json.JSONObject;

public class GetQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        
        HttpSession session=request.getSession(false);
        String id=session.getAttribute("id").toString();
        String pid=session.getAttribute("pid").toString();
        
        try
        {
        	Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
			
			PreparedStatement p=con.prepareStatement("select currentqn from candidate where id=?");
			
			p.setInt(1, Integer.parseInt(id));
			
			ResultSet rs=p.executeQuery();
			
			rs.next();
			
			int questionno=rs.getInt("currentqn");
			
			p=con.prepareStatement("select qndesc from question where pid=? order by id");
			
			p.setInt(1,Integer.parseInt(pid));
			
			rs=p.executeQuery();
			
			int temp=1;
					
			while(rs.next() && temp<questionno)
			{
				temp++;
			}
			JSONObject mainObj=new JSONObject();
			
			mainObj.put("question", rs.getString("qndesc"));
			mainObj.put("no", questionno);
			
			response.getWriter().print(mainObj);
			
        }
        catch(Exception e)
        {
        	System.out.println(e);
        }
        
        
	}

}
