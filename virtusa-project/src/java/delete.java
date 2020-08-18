/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
public class delete extends HttpServlet {

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
        if(ses.getAttribute("uname")==null){
            response.sendRedirect("/virtusa-project");
        }
        else if((ses.getAttribute("isAdmin").toString()).equals("false")){
            response.sendRedirect("/virtusa-project/api/home");
        }
        else{
            String uidai=request.getParameter("uidai");
            PrintWriter out=response.getWriter();
            Connection conn = null;
		  String url = "jdbc:mysql://localhost:3306/epass";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "ac43"; 
		  String password = "chandu456";
                  try {
		  Class.forName(driver);
		  conn = java.sql.DriverManager.getConnection(url,userName,password);
		  //out.print("Connected to the database");
                  java.sql.PreparedStatement ps = conn.prepareStatement("delete from details where uidai_no=?");
                  ps.setString(1,uidai);
                  int rc = ps.executeUpdate();
                  if(rc==0){
                      out.print("<h3>Record not deleted,Retry</h3>");
                  }
                  else{
                      conn.close();
                      //out.print("inserted");
                      response.sendRedirect("users");
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
