package com.shan.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Validation extends HttpServlet {
		String url=null;
		String un=null;;
		String pwd=null;
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet res1 =null;
		
	
	@Override
	public void init(ServletConfig sc) throws ServletException {
		
		ServletContext sCon = sc.getServletContext();
		url = sCon.getInitParameter("url");
		un = sCon.getInitParameter("un");
		pwd = sCon.getInitParameter("pwd");
		
		
		try {		
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection(url, un,pwd );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		try {
			String query="select*from shanstudent where un=? and pwd=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
		    res1 = pstmt.executeQuery();
			
			if(res1.next()==true) {
				writer.println("<h3><Welcome to shanAcademy</h3>");
				req.getRequestDispatcher("/drive").include(req, resp);;
				
			}else {
				RequestDispatcher  rd = req.getRequestDispatcher("/invalidlogin.html");
				rd.include(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void destroy() {
		try {
			con.close();
			pstmt.close();
			res1.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
