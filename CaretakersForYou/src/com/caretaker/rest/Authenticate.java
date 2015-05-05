package com.caretaker.rest;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/authenticate")
public class Authenticate {

	@GET
	public Response authenticate(@QueryParam("email") String email,
			@QueryParam("pass") String pass) {
		Connection conn = null;
		try {
			String query = "SELECT COUNT(*) FROM user WHERE email = '" + email
					+ "' AND password = '" + getHash(pass) + "'";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/geo", "root", "");
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			rs.first();
			int cnt = rs.getInt(1);
			if (cnt > 0) {
				return Response.status(200).build();
			} else {
				return Response.status(401).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(401).build();
		}
	}

	public String getHash(String str) throws Exception {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		String hex = sb.toString();
		return hex;

	}
}