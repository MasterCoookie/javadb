package pl.pols.lab.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pl.polsl.lab.model.Listing;
import pl.polsl.lab.model.Tab;

@WebServlet(name = "InsertServlet", urlPatterns = {"/insert"})
public class InsertServlet extends HttpServlet {

    private Tab tab;
    private Connection con;
    
    @Override
    public void init() {
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String _title = request.getParameter("title");
        String _price = request.getParameter("price");
        String _desc = request.getParameter("desc");
        String _negotiable = request.getParameter("negotiable");
        
        String sql = "";
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        if(session.getAttribute("tabObject") == null) {
            response.sendRedirect(request.getContextPath() + "/create");
        } else {
            this.tab = (Tab)session.getAttribute("tabObject");
        }
        
        if(_title == null || _title.length() == 0 || _price == null || _price.length() == 0 || _desc == null || _desc.length() == 0){
            response.sendRedirect(request.getContextPath() + "/create?username=" + this.tab.getUsername() + "&msg=Missing%20tab%20parameters");
        } else {
            try {
                float price = Float.parseFloat(_price);
                //TODO
                var l = new Listing(_title, price, _desc, "on".equals(_negotiable), "JK", "123");
                try {
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app");
                Statement statement = con.createStatement();
                sql = "INSERT INTO Listings(tabid, title, price, descryption, negotiable, authoruname, authorcontact) VALUES(1, '"
                        + _title + "'," + _price + ",'" + _desc + "'," + "on".equals(_negotiable) + ",'JK', '123'" +")";
                statement.executeUpdate(sql);
                out.println(sql);
            }
                catch(SQLException e){
                   
                   out.println(sql);
                   out.println(e.getMessage());
            }
            } catch(Exception ex) {
//                response.sendRedirect(request.getContextPath() + "/create?username=" + this.tab.getUsername() + "&msg=Invalid%20tab%20parameters");
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
