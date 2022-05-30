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


public class StartProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        
		JSONObject mainObj=new JSONObject();
		
			String id=request.getParameter("pid");
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver");  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/prmportal","root","CAPSlock007@"); 
					
					PreparedStatement p=con.prepareStatement("select started from process where id=?");
					p.setInt(1, Integer.parseInt(id));
					
					ResultSet rs=p.executeQuery();
					
					rs.next();
					
					if(rs.getInt("started")==0)
					{
					
					p=con.prepareStatement("update process set started=? where id=?");
					
					p.setInt(1, 1);
					p.setInt(2,Integer.parseInt(id));
					
					p.executeUpdate();
					
					p=con.prepareStatement("select id from question where pid=?");
					
					p.setInt(1, Integer.parseInt(id));
					
					rs=p.executeQuery();
					
					rs.next();
					
					int currentqn=rs.getInt("id");
					
					p=con.prepareStatement("update candidate set currentqn=? where pid=?");
					
					p.setInt(1, currentqn);
					p.setInt(2, Integer.parseInt(id));
					
					p.executeUpdate();
				}
					p=con.prepareStatement("select id,name,sesid from candidate where pid=?");
					
					p.setInt(1,Integer.parseInt(id));
					
					rs=p.executeQuery();
					
					JSONArray ja=new JSONArray();
					while(rs.next())
					{
						JSONObject jo=new JSONObject();
						jo.put("id", rs.getInt("id"));
						jo.put("name",rs.getString("name"));
						
						String sesid=rs.getString("sesid");
						if(sesid==null)
						{
							jo.put("sesid", false);
						}
						else
						{
							jo.put("sesid", true);
						}
						
						ja.put(jo);
					}
					mainObj.put("ques", ja);
					
					response.getWriter().print(mainObj);
					
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
	}
