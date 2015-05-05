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
        System.out.println("hello");
        char acc='C';
        String query = "select firstname,lastname,service,fromdate,todate,city from user where account='"+acc+"'";
          PreparedStatement pstm= con.prepareStatement(query);

           rs = pstm.executeQuery();
           JSONObject JObj = null;

        while (rs.next()) {
        	String name=rs.getString("firstname")+" "+rs.getString("lastname");
        	String service=rs.getString("service");
        	String avail=rs.getString("fromdate")+"  to  "+rs.getString("todate");
        	String city = rs.getString("city")+",CA";
            String charge = "$14/hr";
            //String star = "<img src='img/star.png' alt='stars' style='height:20px;width:20px;'>";
            String star = "img/star.png";
            
            JObj = new JSONObject();
            JObj.put("name", name);
            JObj.put("service", service);
            JObj.put("avail", avail);
            JObj.put("city", city);
            JObj.put("charge", charge);
            JObj.put("rating", star);
            
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