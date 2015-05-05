package com.caretaker.rest;



import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.ResultSet;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.api.view.Viewable;
//http://localhost:8080/CaretakersForYou/rest/viewcaretakerlist/viewList

@Path("/viewcaretakerlist") 
public class ListDisplay {

  
	@GET
	@Path("/viewList")
	//@Produces(MediaType.TEXT_PLAIN)
	@Produces("text/html")
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response viewList(String data, @Context HttpServletRequest request,
		      @Context HttpServletResponse response)throws Exception{
		
		System.out.println("enterd the method");
        String details = ""; 
		Connection conn = null;
		
		
			try {
				
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/geo", "root", "");
				if(!conn.isClosed()){
					System.out.println("Connection Successful");
					
				}else{
					System.out.println("Connection Error!!");
				}
				 
			      System.out.println("Connection to server sucessfully");
			 char acc='C';
			String query = "select firstname,lastname,service,fromdate,todate,city from user where account='"+acc+"'";
			System.out.println(query);
			PreparedStatement stmt2 = conn.prepareStatement(query);
			ResultSet rs = stmt2.executeQuery(query);
            
			
		
			
			details = "<html><body>";
            details = details + "<table border='2px' cellpadding='10' id='listview' align='center' width='600px'>";
            details = details + "<tr><th><h3><b>Name</b></h3></th>" +
                    "<th><h3><b>Services Provided</b></h3></th>"+
                     "<th><h3><b>Availability</b></h3></th>"+
                     "<th><h3><b>Location</b></h3></th>"+
                       "<th><h3><b>Charges</b></h3></th>"+
                        "<th><h3><b>Rating</b></h3></th>"+"</tr>";
                    
            while (rs.next()) 
            {
            	
            	
                details = details + "<tr><td><img src='img/page1_img1.jpg' alt='dp' style='height:25px;width:25px;'>&nbsp;&nbsp;" + rs.getString("firstname")+" "+rs.getString("lastname")+ "</td>" +
                                        "<td>" + rs.getString("service") + "</td>"+"<td>" + rs.getString("fromdate")+" - "+rs.getString("todate") + "</td>"+"<td>" + rs.getString("city")+",CA"+ "</td>"+
                                        "<td>" + "$14/hr" + "</td>"+"<td>" + "1" + "</td>"+"</tr>";
            }
            details += "</table></body></html>"; 
           // return details;
        } 
        catch (Exception e) 
        {
        	
            System.out.println(e.getMessage());
        } 
			
			Viewable out=new Viewable("/OrderDetails.jsp");
			return Response.status(200).entity(out).build();
			//return new Viewable("/ListView.html",details); 
			//response.setContentType("text/html");
			
			
			
			//HttpSession session=request.getSession();
			//session.setAttribute("details", details);
			//return details;
			
			//Viewable output= new Viewable("/ListView.html");
			//return Response.status(200).entity(output).build();
			
		}
		
		
		

}
	

