/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author A Chandu
 */
public class profile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession ses=request.getSession();
        PrintWriter out=response.getWriter();
        if(ses.getAttribute("uname")==null){
            response.sendRedirect("/virtusa-project");
        }
        else{
            RequestDispatcher view = request.getRequestDispatcher("/profile.html");
            view.include(request, response);
            try{
                Connection conn = null;
		  String url = "jdbc:mysql://localhost:3306/epass";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "ac43"; 
		  String password = "chandu456";
                  String uname=ses.getAttribute("uname").toString();
		  Class.forName(driver);
		  conn = java.sql.DriverManager.getConnection(url,userName,password);
                  Statement st = conn.createStatement();
                  String q="select * from details where username=\""+uname+"\";";
                  ResultSet rs=st.executeQuery(q);
                  if(rs.next()){
                    String fname=rs.getString("fname");
                    String lname=rs.getString("lname");
                    String usern=rs.getString("username");
                    String email=rs.getString("email");
                    String phone=rs.getString("phone");
                    String uidai=rs.getString("uidai_no");
                    out.print("<div class=\"container\">\n" +
"          <div class=\"row justify-content-center\">\n" +
"              <div class=\"col-sm-4 border-left border-top text-danger\">\n" +
"                  <h4>Name:</h4>\n" +
"              </div>\n" +
"              <div class=\"col-sm-4 border-right border-top\">\n" +
"                  <h4>"+fname+" "+lname+"</h4>\n" +
"              </div>\n" +
"          </div>\n" +
"          <div class=\"row justify-content-center\">\n" +
"              <div class=\"col-sm-4 border-left text-danger\">\n" +
"                  <h4>Username:</h4>\n" +
"              </div>\n" +
"              <div class=\"col-sm-4 border-right \">\n" +
"                  <h4>"+usern+"</h4>\n" +
"              </div>\n" +
"          </div>\n" +
"          <div class=\"row justify-content-center\">\n" +
"              <div class=\"col-sm-4 border-left text-danger\">\n" +
"                  <h4>Email:</h4>\n" +
"              </div>\n" +
"              <div class=\"col-sm-4 border-right \">\n" +
"                  <h4>"+email+"</h4>\n" +
"              </div>\n" +
"          </div>\n" +  
"          <div class=\"row justify-content-center\">\n" +
"              <div class=\"col-sm-4 border-left text-danger\">\n" +
"                  <h4>Phone No:</h4>\n" +
"              </div>\n" +
"              <div class=\"col-sm-4 border-right \">\n" +
"                  <h4>"+phone+"</h4>\n" +
"              </div>\n" +
"          </div>\n" +
"          <div class=\"row justify-content-center\">\n" +
"              <div class=\"col-sm-4 border-left border-bottom text-danger\">\n" +
"                  <h4>Aadhaar No:</h4>\n" +
"              </div>\n" +
"              <div class=\"col-sm-4 border-right border-bottom \">\n" +
"                  <h4>"+uidai+"</h4>\n" +
"              </div>\n" +
"          </div>\n" +
"          <div class=\"row justify-content-center\">\n" +
"              <div class=\"col-sm-4\">\n" +
"                  <button class=\"btn btn-danger\">Edit</button>\n" +
"              </div>\n" +
"              <div class=\"col-sm-4 border-right \">\n" +
"                  <button class=\"btn btn-danger\">Delete</button>\n" +
"              </div>\n" +
"          </div>\n" +
"      </div>");
                  }
                  conn.close();
            }
            catch(Exception e){
                out.print(e);
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
