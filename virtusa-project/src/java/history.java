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
public class history extends HttpServlet {

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
        String uname="";
        if(ses.getAttribute("uname")==null){
            response.sendRedirect("/virtusa-project");
        }
        else{
            uname=ses.getAttribute("uname").toString();
        }
        PrintWriter out=response.getWriter();boolean flag=false;
        RequestDispatcher view = request.getRequestDispatcher("/history.html");
        view.include(request, response);
        out.print("<div class=\"container-fluid\"><table class=\"table table-striped\">\n" +
                "              <thead class=\"thead-dark\">\n" +
                "              <tr>\n" +
                "                <th scope=\"col\">#</th>\n" +
                "                <th scope=\"col\">From</th>\n" +
                "                <th scope=\"col\">To</th>\n" +
                "                <th scope=\"col\">Date</th>\n" +
                "                <th scope=\"col\">Unique Id</th>\n" +
                "              </tr>\n" +
                "            </thead>\n" +          

                "  <tbody>\n" );
        try{
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
                  String q="select * from valid where uname=\""+uname+"\" order by trav_date desc";
                  ResultSet rs=st.executeQuery(q);
                  int i=0;
                  while(rs.next()){
                      i++;
                      flag=true;
                      String from=rs.getString("src");
                      String to=rs.getString("dest");
                      String date=rs.getString("trav_date");
                      String id=rs.getString("id");
                      out.print(
                        "    <tr>\n" +
                        "      <th scope=\"row\">"+i+"</th>\n" +
                        "      <td>"+from+"</td>\n" +
                        "      <td>"+to+"</td>\n" +
                        "      <td>"+date+"</td>\n" +
                        "      <td>"+id+"</td>\n" +        
                        "    </tr>\n" +
                        "    <tr>\n" );
                  }
		  conn.close();
            }
                catch(Exception e){
                    out.print(e);
                }
        }
        catch(Exception e){
            out.print(e);
        }
        if(!flag){
                      out.print("<div>"
                              +"<h6>No travel history to display.</h6>"
                              + "</div>");
                  }
        out.print(
                "  </tbody>\n" +
                "</table></div>");
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
