/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author A Chandu
 */
public class feedback extends HttpServlet {

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
        String uname=request.getParameter("name");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String msg=request.getParameter("message");
        Date d1=new Date();
        int date=d1.getDate();
        int month=(d1.getMonth()+1);
        int year=(d1.getYear()+1900);
        String date1=String.valueOf(year)+"-";
        if(month<10){
            date1+="0"+String.valueOf(month)+"-";
        }
        else{
            date1+=String.valueOf(month)+"-";
        }
        if(date<10){
            date1+="0"+String.valueOf(date);
        }
        else{
            date1+=String.valueOf(date);
        }
        if(uname==null || email == null ){
            response.sendRedirect("/virtusa-project");
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
                  java.sql.PreparedStatement ps = conn.prepareStatement("insert into feedback values(?,?,?,?,?)");
                  ps.setString(1,uname);
                  ps.setString(2,email);
                  ps.setString(3,phone);
                  ps.setString(4,msg);
                  ps.setString(5,date1);
                  ps.executeUpdate();
                  response.sendRedirect("home");
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
