package com.caretaker.rest;


import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.api.view.Viewable;

 
@Path("/insertFormpage") 
public class insertForm {
	
	
	@POST
	@Path("/insertReg")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertReg(String data, @Context HttpServletRequest request,
		      @Context HttpServletResponse response)throws Exception{
		System.out.println(data);
		
	
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
			      
			     
			}
				catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(data);
			 
			JSONObject jsonObject = (JSONObject) obj;
	 
			String fname = (String) jsonObject.get("first");
			System.out.println(fname);
			String lname = (String) jsonObject.get("last");
			System.out.println(lname);
			String email = (String) jsonObject.get("email");
			System.out.println(email);
			String password = (String) jsonObject.get("pass");
			System.out.println(password);
			String addressline1 = (String) jsonObject.get("address1");
			System.out.println(addressline1);
			String city = (String) jsonObject.get("cities");
			System.out.println(city);
			String pincode = (String) jsonObject.get("pin");
			System.out.println(pincode);
			String proof = (String) jsonObject.get("prooftype");
			System.out.println(proof);
			String account = (String) jsonObject.get("account");
			System.out.println(account);
			String service = (String) jsonObject.get("service");
			System.out.println(service);
			String date1 = (String) jsonObject.get("fromdate");
			System.out.println(date1);
			String date2 = (String) jsonObject.get("todate");
			System.out.println(date2);
			String coordinates = (String) jsonObject.get("coord");
			System.out.println(date2);
			
	 
			String query = "INSERT into user(firstname,lastname,email,password,address1,city,pincode,prooftype,account,service,fromdate,todate,coordinates) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			System.out.println(query);
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, email);
			stmt.setString(4, getHash(password));
			stmt.setString(5, addressline1);
			stmt.setString(6, city);
			stmt.setString(7, pincode);
			stmt.setString(8, proof);
			stmt.setString(9, account);
			stmt.setString(10, service);
			stmt.setString(11, date1);
			stmt.setString(12, date2);
			stmt.setString(13, coordinates);
			int rowcount = stmt.executeUpdate();
			System.out.println(rowcount);
			String result="";
		if(rowcount>0)
		{
			
			System.out.println("Registration added successfully!");
			result="Registration added successfully!";
			
			Viewable output= new Viewable("/ListView.html", result);
			return Response.status(200).entity(output).build();
		}
		else
		{
			result="Registration unsuccessful!";
			Viewable output= new Viewable("/RegistrationPage.html", result);
			return Response.status(401).entity(output).build();
		}
		

}
	
	
	public String getHash(String str) throws Exception{
		
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        String hex = sb.toString();
        System.out.println(hex);
        return hex; 
 
	}
}