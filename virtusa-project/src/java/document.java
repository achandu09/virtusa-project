/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mysql.cj.jdbc.Blob;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author A Chandu
 */
public class document extends HttpServlet {

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
       // response.setContentType("text/html;charset=UTF-8");
        HttpSession ses=request.getSession();
        if(ses.getAttribute("uname")==null){
            response.sendRedirect("/virtusa-project");
        }
        else if((ses.getAttribute("isAdmin").toString()).equals("false")){
            response.sendRedirect("/virtusa-project/api/home");
        }
        else{
            java.sql.Blob image = null;
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            ServletOutputStream out = response.getOutputStream();
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
                  String q="select * from valid where id=\""+request.getParameter("id")+"\"";
                  rs = st.executeQuery("select uidai_doc from details where uidai_no=\""+request.getParameter("uidai")+"\"");
                  if (rs.next()) {
                    image = rs.getBlob(1);
                  } 
                  else {
                    response.setContentType("text/html");
                    out.println("<html><head><title>Display Blob Example</title></head>");
                    out.println("<body><h4><font color='red'>image not found for given id</font></h4></body></html>");
                    return;
                  }
                  response.setContentType("image/gif");
                  InputStream in = image.getBinaryStream();
                  int length = (int) image.length();
                  int bufferSize = 1024;
                  byte[] buffer = new byte[bufferSize];
                  while ((length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                  }
                  in.close();
                  out.flush();
		  conn.close();
            }
                catch(Exception e){
                    response.setContentType("text/html");
                    out.println("<html><head><title>Unable To Display image</title></head>");
                    out.println("<body><h4><font color='red'>Image Display Error=" + e.getMessage() +
                     "</font></h4></body></html>");
                }
            finally {
            try {
            rs.close();
            stmt.close();
            con.close();
            } catch (java.sql.SQLException e) {
            e.printStackTrace();
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

    private Object getToolkit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
