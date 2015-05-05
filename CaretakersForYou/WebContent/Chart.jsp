<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="org.json.JSONObject" %>

<%
    Connection con= null;
 try{
  Class.forName("com.mysql.jdbc.Driver").newInstance();
  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/geo","root","");

        ResultSet rs = null;
        List<JSONObject> details = new ArrayList();
        
        
        String query = "select city,count(*) as count from user where account='C' group by city";
        System.out.println(query);
          PreparedStatement pstm= con.prepareStatement(query);

           rs = pstm.executeQuery();
           JSONObject JObj = null;

        while (rs.next()) {
        	String city=rs.getString("city");
        	String count=rs.getString("count");
        	
            
            JObj = new JSONObject();
            JObj.put("city", city);
            JObj.put("count", count);
            
            details.add(JObj);
        }
        
        JSONObject responseObj = new JSONObject();
        responseObj.put("details", details);
        String json = responseObj.toString();
        System.out.println(json);

    	out.print(json);
    }
    catch(Exception e){
        e.printStackTrace();
    }finally{
        if(con!= null){
            try{
            con.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
 %>