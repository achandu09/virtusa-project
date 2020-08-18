/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
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
public class getpasses extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void print(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String date=request.getParameter("date");
        out.print("<!doctype html>\n" +
            "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <!-- Required meta tags -->\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
            "\n" +
            "    <!-- Bootstrap CSS -->\n" +
            "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n" +
            "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
            "    <title>Admin Home</title>\n" +
            "    <link rel=\"icon\" href=\"../../images/icon17.svg\">\n" +
            "  </head>\n" +
            "  <body class=\"bg-secondary\">\n" +
            "    <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n" +
            "        <a class=\"navbar-brand\" href=\"#\"><i class=\"fa fa-quote-left\"></i> ePass-Covid <i class=\"fa fa-quote-right\"></i></a>\n" +
            "        <ul class=\"font-weight-normal navbar-nav mr-auto\">\n" +
            "          <li class=\"nav-item active\">\n" +
            "            <a class=\"nav-link\" href=\"home\"><i class=\"fa fa fa-address-card-o\"></i> Admin UI<span class=\"sr-only\">(current)</span></a>\n" +
            "          </li>\n" +
            "        </ul>  \n" +
            "        <form action=\"/virtusa-project/api/logout\" method=\"get\" class=\"form-inline my-2 my-lg-0\">\n" +
            "            <button class=\"btn btn-danger my-2 my-sm-0\" type=\"submit\"><i class=\"fa fa-sign-out\" ></i>Logout</button>\n" +
            "        </form>\n" +
            "    </nav>\n" +
            "    <!--code goes here-->\n" +
            "    <div class=\"container p-4\">\n" +
            "          <form method=\"post\" action=\"getpasses\">\n" +
            "          <div class=\"row justify-content-center\">\n" +
            "              <div class=\"col-sm-4\">\n" +
            "                  <label><h5>Select Date: </h5></label>\n" +
            "                    <div class=\"form-group\">\n" +
            "                        <input type=\"date\" class=\"form-control\" id=\"date\" name=\"date\" value=\""+date+"\" required>\n" +
            "                    </div>    \n" +
            "              </div>              \n" +
            "          </div>\n" +
            "              <div class=\"row\"></div>\n" +
            "              <div class=\"row justify-content-center\">\n" +
            "                  <div class=\"col-sm-3\">\n" +
            "                      <br />\n" +
            "                      <input class=\"btn btn-danger form-control\" type=\"submit\" value=\"Get Passes!\">\n" +
            "                  </div>\n" +
            "              </div>\n" +
            "          </form>\n" +
            "      </div>\n" +
            "    \n" +
            "    <hr class=\"featurette-divider\">\n" +
            "        <!-- Optional JavaScript -->\n" +
            "    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\n" +
            "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" +
            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>\n" +
            "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>\n" +
            "  </body>\n" +
            "</html>");
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession ses=request.getSession();
        if(ses.getAttribute("uname")==null){
            response.sendRedirect("/virtusa-project");
        }
        else if((ses.getAttribute("isAdmin").toString()).equals("false")){
            response.sendRedirect("/virtusa-project/api/home");
        }
        else{
            print(request,response);
            try{
                boolean flag=false;
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
                  String q="select * from valid where trav_date=\""+request.getParameter("date")+"\"";
                  java.sql.ResultSet rs=st.executeQuery(q);
                  int i=0;
                  out.print("<div class=\" bg-light\"><table class=\"table table-striped\">\n" +
                "              <thead class=\"thead-dark\">\n" +
                "              <tr>\n" +
                "                <th scope=\"col\">#</th>\n" +
                "                <th scope=\"col\">From</th>\n" +
                "                <th scope=\"col\">To</th>\n" +
                "                <th scope=\"col\">Date</th>\n" +
                "                <th scope=\"col\">Unique Id</th>\n" +
                "                <th scope=\"col\">Details</th>\n" +          
                "              </tr>\n" +
                "            </thead>\n" +          

                "  <tbody>\n" );
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
                        "      <td><form action=\"details\" method=\"post\"><button class=\"form-control btn btn-danger\" name=\"id\" value=\""+id+"\" type=\"submit\">Get Details</button></form>"+        
                        "    </tr>\n" +
                        "    <tr>\n" );
                  }
		  conn.close();
                  if(!flag){
                      out.print("<div class=\"bg-secondary row justify-content-center\">\n" +
                                "  <h4>No passes allotted to display,Try different date.</h4>\n" +
                                "</div>\n"
                      );
                  }
            }
                catch(Exception e){
                    out.print(e);
                }
            }
            catch(Exception e){
                
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
