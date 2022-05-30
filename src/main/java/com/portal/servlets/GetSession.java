package com.portal.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class GetSession extends HttpServlet {
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
		HttpSession session=request.getSession(false);
		try {
			if(session==null)
			{
				mainObj.put("session", false);
			}
			else
			{
				String id=session.getAttribute("id").toString();
				mainObj.put("session", true);
				
				if(id.equals("admin"))
				{
					mainObj.put("pid", "admin");
				}
				else
				{
					String pid=session.getAttribute("pid").toString();
					mainObj.put("pid", pid);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		response.getWriter().print(mainObj);
	}

}
