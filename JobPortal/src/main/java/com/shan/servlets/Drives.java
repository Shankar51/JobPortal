package com.shan.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Drives extends HttpServlet {
	
	String url=null;
	String un=null;
	String pwd=null;
	Connection con =null;
	Statement stmt=null;
	ResultSet res2 =null;
	
	
	@Override
	public void init(ServletConfig sc) throws ServletException {
		
		ServletContext sCon = sc.getServletContext();
		url = sCon.getInitParameter("url");
		un = sCon.getInitParameter("un");
		pwd = sCon.getInitParameter("pwd");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, un, pwd);
			
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		
		try {
			String query="select*from Drives";
			stmt = con.createStatement();
			res2 = stmt.executeQuery(query);

				writer.println("<table border=1>\r\n"
						+ "	<tr>\r\n"
						+ "		<th>Id</th>\r\n"
						+ "		<th>name</th>\r\n"
						+ "		<th>10th</th>\r\n"
						+ "		<th>12th</th>\r\n"
						+ "		<th>grad</th>\r\n"
						+ "		<th>profile</th>\r\n"
						+ "		<th>packag</th>\r\n"
						+ "		<th>skills</th>\r\n"
						+ "	</tr>");
			
			
			while(res2.next()==true) {
				int id = res2.getInt(1);
				String name = res2.getString(2);
				int ten = res2.getInt(3);
				int twe = res2.getInt(4);
				int grad = res2.getInt(5);
				String profile = res2.getString(6);
				float packag = res2.getFloat(7);
				String skills = res2.getString(8);
				
				writer.println("<tr>\r\n"
						+ "		<td>"+id+"</td>\r\n"
						+ "		<td>"+name+"</td>\r\n"
						+ "		<td>"+ten+"</td>\r\n"
						+ "		<td>"+twe+"</td>\r\n"
						+ "		<td>"+grad+"</td>\r\n"
						+ "		<td>"+profile+"</td>\r\n"
						+ "		<td>"+packag+"</td>\r\n"
						+ "		<td>"+skills+"</td>\r\n"
						+ "	</tr>");
			}
			writer.println("</table>");
			
			req.getRequestDispatcher("/eligible").include(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
