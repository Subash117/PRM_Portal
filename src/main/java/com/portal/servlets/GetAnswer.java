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

import org.json.JSONArray;
import org.json.JSONObject;


public class GetAnswer extends HttpServlet {
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
		
		String qid=request.getParameter("qnid");
		
		if(id.equals("admin"))
		{
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@");
				
				PreparedStatement p=con.prepareStatement("select id,ans from answer where id=?");
				
				p.setInt(1, Integer.parseInt(qid));
				
				ResultSet rs=p.executeQuery();
				
				JSONObject mainObj=new JSONObject();
				
				JSONArray ja=new JSONArray();
				while(rs.next())
				{
					JSONObject jo=new JSONObject();
					jo.put("id", rs.getInt("id"));
					jo.put("ans", rs.getString("ans"));
					
					ja.put(jo);
				}
				System.out.print("Gets Answer Admin");
				mainObj.put("answers", ja);
				
				response.getWriter().print(mainObj);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}

}
