<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="org.json.JSONObject" %>

<%
    Connection con= null;
 try{
  Class.forName("com.mysql.jdbc.Driver").newInstance();
  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/geo","root","");
  //System.out.println("hello");
        ResultSet rs = null;
        List<JSONObject> details = new ArrayList();
       

          String query = "SELECT  firstname , service , coordinates  from user  where account='c' ;";
          PreparedStatement pstm= con.prepareStatement(query);

           rs = pstm.executeQuery();
           JSONObject JObj = null;

        while (rs.next()) {
            String firstname = rs.getString("firstname");
            String service = rs.getString("service");
            String cord = rs.getString("coordinates");
            JObj = new JSONObject();
            JObj.put("firstname", firstname);
            JObj.put("service", service);
            JObj.put("cord", cord);
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