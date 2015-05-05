package com.caretaker.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import com.sun.jersey.api.view.Viewable;

@Path("/hello")
public class HelloWorld {

	@GET
	@Path("/helloworld")
	public String sayHello(String data, @Context HttpServletRequest request,
		      @Context HttpServletResponse response)throws Exception{
		System.out.println("Hi there!!");
		
		String output="hello world";
		//Viewable out=new Viewable("/ListView.html",output);
		//return Response.status(200).entity(out).build();
		response.setContentType("text/javascript");
		return output;
	}
	
}
