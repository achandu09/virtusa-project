/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 *
 * @author A Chandu
 */
public class epass_auth extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static String getUniqueId(int n) 
    { 
  
        // length is bounded by 256 Character 
        byte[] array = new byte[256]; 
        new Random().nextBytes(array); 
  
        String randomString 
            = new String(array, Charset.forName("UTF-8")); 
  
        // Create a StringBuffer to store the result 
        StringBuffer r = new StringBuffer(); 
  
        // remove all spacial char 
        String  AlphaNumericString 
            = randomString 
                  .replaceAll("[^0-9]",""); 
  
        // Append first 20 alphanumeric characters 
        // from the generated random String into the result 
        for (int k = 0; k < AlphaNumericString.length(); k++) { 
  
            if (Character.isLetter(AlphaNumericString.charAt(k)) 
                    && (n > 0) 
                || Character.isDigit(AlphaNumericString.charAt(k)) 
                       && (n > 0)) { 
  
                r.append(AlphaNumericString.charAt(k)); 
                n--; 
            } 
        } 
  
        // return the resultant string 
        if(r.toString().length() == 10){
            return r.toString();
        }
        return getUniqueId(10); 
    }
    public void failed(HttpServletRequest request, HttpServletResponse response,String msg)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <!-- Required meta tags -->\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                "\n" +
                "    <!-- Bootstrap CSS -->\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\" integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "    <title>Error 404!</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "<div class=\"row justify-content-center\"> "+
                "    <h3>"+msg+"</h3>\n" +
                "</div> "+
                "<div class=\"row justify-content-center\">"+
                "     <form method=\"get\" action=\"/virtusa-project/api/epass\">"+
                "     <input type=\"submit\" class=\"btn btn-danger\" value=\"Go Back!\"> "+
                "</div>"+
                "\n" +
                "    <!-- Optional JavaScript -->\n" +
                "    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" +
                "    <script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\" integrity=\"sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI\" crossorigin=\"anonymous\"></script>\n" +
                "  </body>\n" +
                "</html>");
    }
    protected void pass(HttpServletRequest request, HttpServletResponse response ,String id,String name ,
            String email,String phone,String uidai,String from , String to , String date)
            throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        out.print("<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>ePass</title>\n" +
            "    <link rel=\"icon\" href=\"../images/icon17.svg\">"+
            "    <script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\"\n" +
            "        integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\">\n" +
            "    </script>\n" +
            "    <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\"\n" +
            "        integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\">\n" +
            "    </script>\n" +
            "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\"\n" +
            "        integrity=\"sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI\" crossorigin=\"anonymous\">\n" +
            "    </script>\n" +
            "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\"\n" +
            "        integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">\n" +
            "</head>\n" +
            "\n" +
            "<body class=\"bg-secondary\"> \n" +
            "    <div class=\"container-fluid border p-3\">\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "           <h6 id=\"head\">e-Pass(COVID-19)</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "           <h6 id=\"uni_id\">Unique ID: "+id+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "           <h6 id=\"name\">Name: "+name+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "            <h6 id=\"email\">Email: "+email+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "            <h6 id=\"phone\">Phone: "+phone+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "            <h6 id=\"uidai\">Aadhaar Number: "+uidai+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "            <h6 id=\"from\">Heading from: "+from+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "            <h6 id=\"to\">Destination: "+to+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "            <h6 id=\"valid\">Valid for Date: "+date+"</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">\n" +
            "            <h6 id=\"warn\">You must carry your aadhaar card from identification check</h6>\n" +
            "        </div>\n" +
            "        <div class=\"row justify-content-center\">    \n" +
            "           <button type=\"button\" class=\"btn btn-danger\" onclick=\"fill()\">Download Pass!</button>\n" +
            "           <div class=\"col-sm-1\"></div>\n" +
            "           <form action=\"/virtusa-project/api/home\" method=\"get\">\n" +
            "              <input class=\"btn btn-danger\" type=\"submit\" value=\"Go Home!\" />\n" +
            "           </form>" +
            "        </div>\n" +        
            "    </div>\n" +
            "    <canvas id=\"myCanvas\" width=\"1000\" height=\"1000\" style=\"border:1px solid #ca1e1e;display:none ;\">Your browser does not support the\n" +
            "        canvas element.</canvas>\n" +
            "</body>\n" +
            "<script>\n" +
            "    function fill() {\n" +
            "        var canvas = document.getElementById(\"myCanvas\");\n" +
            "        var head = document.getElementById(\"head\").innerHTML;\n" +
            "        var id = document.getElementById(\"uni_id\").innerHTML;\n" +
            "        var name = document.getElementById(\"name\").innerHTML;\n" +
            "        var email = document.getElementById(\"email\").innerHTML;\n" +
            "        var phone = document.getElementById(\"phone\").innerHTML;\n" +
            "        var uidai = document.getElementById(\"uidai\").innerHTML;\n" +
            "        var from = document.getElementById(\"from\").innerHTML;\n" +
            "        var to = document.getElementById(\"to\").innerHTML;\n" +
            "        var valid = document.getElementById(\"valid\").innerHTML;\n" +
            "        var warn = document.getElementById(\"warn\").innerHTML;\n" +
            "        var ctx = canvas.getContext(\"2d\");\n" +
            "        ctx.moveTo(5,5);\n" +
            "        ctx.lineTo(5,600);\n" +
            "        ctx.stroke();\n" +
            "        ctx.moveTo(5,5);\n" +
            "        ctx.lineTo(995,5);\n" +
            "        ctx.stroke();\n" +
            "        ctx.moveTo(5,600);\n" +
            "        ctx.lineTo(995,600);\n" +
            "        ctx.stroke();\n" +
            "        ctx.moveTo(995,5);\n" +
            "        ctx.lineTo(995,600);\n" +
            "        ctx.stroke();\n" +
            "        ctx.font = \"30px Arial\";\n" +
            "        ctx.fillText(head,380,50);\n" +
            "        ctx.fillText(id, 20,150);\n" +
            "        ctx.fillText(name,20,200);\n" +
            "        ctx.fillText(email,20,250);\n" +
            "        ctx.fillText(phone,20,300);\n" +
            "        ctx.fillText(uidai,20,350);\n" +
            "        ctx.fillText(from,20,400);\n" +
            "        ctx.fillText(to,20,450);\n" +
            "        ctx.fillText(valid,20,500);\n" +
            "        ctx.fillText(warn,20,550);\n" +
            "        //canvas.style.display='block';\n" +
            "        const aurl=canvas.toDataURL();\n" +
            "        //console.log(aurl);\n" +
            "        const a=document.createElement(\"a\");\n" +
            "        document.body.appendChild(a);\n" +
            "        a.href= canvas.toDataURL();\n" +
            "        a.download=\"e-pass.png\";\n" +
            "        a.click();\n" +
            "        document.body.removeChild(a);\n" +
            "    }\n" +
            "</script>\n" +
            "\n" +
            "</html>");
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession ses = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            String from=request.getParameter("from");
            String to=request.getParameter("to");
            String date=request.getParameter("date");
            if(from.equals(to)){
                failed(request,response,"Source and Destination cannot be same");
            }
            else{
                Connection conn = null;
		  String url = "jdbc:mysql://localhost:3306/epass";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "ac43"; 
		  String password = "chandu456";
		  try {
		  Class.forName(driver);
		  conn = java.sql.DriverManager.getConnection(url,userName,password);
		  //out.print("Connected to the database");
                  Statement st1 = conn.createStatement();
                  Statement st2 = conn.createStatement();
                  Statement st3 = conn.createStatement();
                  Statement st4 = conn.createStatement();
                  String q1="select * from covid_count where area = \""+from+"\"";
                  String q2="select * from covid_count where area = \""+to+"\"";
                  String q3="select * from covid_count where area = \"Telangana\"";
                  ResultSet rs1=st1.executeQuery(q1);
                  ResultSet rs2=st2.executeQuery(q2);
                  ResultSet rs3=st3.executeQuery(q3);
                  if(rs1.next()&&rs2.next()&&rs3.next()){
                       int tcount=rs3.getInt("total");
                       int rcount=rs3.getInt("recovery");
                       int dcount=rs3.getInt("deaths");
                       int tcount_f=rs1.getInt("total");
                       int tcount_t=rs2.getInt("total");
                       int rcount_f=rs1.getInt("recovery");
                       int rcount_t=rs2.getInt("recovery");
                       int dcount_f=rs1.getInt("deaths");
                       int dcount_t=rs2.getInt("deaths");
                       int x,y,a,b,m,n;
                       if(tcount_f==0){
                           x=0;a=100;m=0;
                       }
                       else{ 
                           x=((tcount-rcount-dcount)/(tcount_f - rcount_f - dcount_f))*100;
                           if(rcount_f==0){
                              a=0;
                           }
                           else{
                              a=((rcount_f)/(tcount_f))*100;
                           }
                           if(dcount_f==0){
                              m=0;
                           }
                           else{
                              m=((dcount_f)/(tcount_f))*100;   
                           }
                       }
                       if(tcount_t==0){
                           y=0;b=100;n=0;
                       }
                       else{
                            y=((tcount-rcount-dcount)/(tcount_t - rcount_t - dcount_t))*100;
                            if(rcount_t==0){
                              b=0;
                            }
                            else{
                               b=((rcount_t)/(tcount_t))*100;
                            }
                            if(dcount_t==0){
                               n=0;
                            }
                            else{
                               n=((dcount_t)/(tcount_t))*100;   
                            }
                       }
                       if((x>=40) || (a<=25) || (m>=20) || rs1.getBoolean("containment")){
                           failed(request,response,"Passes cannot be allocated right now for the people from this area. ("+(from)+")");
                       }
                       else if((y>=40) || (b<=25) || (n>=20) || rs2.getBoolean("containment")){
                           failed(request,response,"Passes cannot be allocated right now for the people reaching this area. ("+(to)+")");
                       }
                       else{
                           String uname = ses.getAttribute("uname").toString();
                           String q4="select * from details where username=\""+uname+"\"";
                           
                           ResultSet rs4=st4.executeQuery(q4);
                           if(rs4.next()){
                                String id=getUniqueId(10);
                                java.sql.PreparedStatement ps = conn.prepareStatement("insert into valid values(?,?,?,?,?,?,?,?,?)");
                                ps.setString(1,ses.getAttribute("uname").toString());
                                ps.setString(2, rs4.getString("fname")+" "+rs4.getString("lname"));
                                ps.setString(3,rs4.getString("email"));
                                ps.setString(4,rs4.getString("phone"));
                                ps.setString(5,rs4.getString("uidai_no"));
                                ps.setString(6,from);
                                ps.setString(7,to);
                                ps.setString(8,id);
                                ps.setString(9,date);
                                ps.executeUpdate();
                                pass(request,response,id,rs4.getString("fname")+" "+rs4.getString("lname"),
                                        rs4.getString("email"),rs4.getString("phone"),rs4.getString("uidai_no"),
                                        from,to,date);
                           }
                       }
                  }   
                  else{
                      failed(request,response,"Unexpected error occurred , try again later.");
                  }
		  conn.close();
		  //out.print("Disconnected from database");
		  }
                  catch(Exception e){
                      out.print(e);
                  }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
