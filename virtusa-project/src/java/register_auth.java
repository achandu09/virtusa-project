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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author A Chandu
 */
@MultipartConfig
public class register_auth extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String usern=request.getParameter("uname");
        String uidaiNo=request.getParameter("uidai");
        if(usern==null || uidaiNo == null ){
            out.print("reached here"+usern+" "+uidaiNo+" "+request.getParameter("lname"));
            //response.sendRedirect("/virtusa-project");
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
                  Statement st = conn.createStatement();
                  String q="select pass from details where username = "+"\""+usern+"\"";
                  ResultSet rs= st.executeQuery(q);
                  if(rs.next()){
                      response.sendRedirect("/virtusa-project/api/register-failed");
                  }
                  else{
                      Statement st1 = conn.createStatement();
                      String q1="select pass from details where uidai_no = "+"\""+uidaiNo+"\"";
                      ResultSet rs1= st1.executeQuery(q1);
                      if(rs1.next()){
                          response.sendRedirect("/virtusa-project/api/register-failed");
                      }
                      else{
                          RequestDispatcher rd = request.getRequestDispatcher("insert");
                          rd.forward(request, response);
                      }
                  }
		  conn.close();
		  //out.print("Disconnected from database");
		  } catch (Exception e) {
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
