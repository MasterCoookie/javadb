package pl.pols.lab.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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

@WebServlet(name = "TabServlet", urlPatterns = {"/tab"})
public class TabServlet extends HttpServlet {

    private Tab tab;
    private Connection con;
    
    @Override
    public void init() {
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
       
        
        
        
        String _username = request.getParameter("username");
        String _contact = request.getParameter("contact");
        String _inserted = request.getParameter("inserted");
        String sellingToday = "";
        this.tab = new Tab();
        
        
        
        if(_username == null || _contact == null || _username.length() == 0 || _contact.length() == 0){
            response.sendError(response.SC_BAD_REQUEST, "Wymagane są dwa parametry!!!");
        } else {
            
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies) {
                    if(cookie.getName().equals("sellingToday")) {
                        sellingToday = cookie.getValue();
                        break;
                    }
                }
            }
           
            PrintWriter out = response.getWriter();
            
//            HttpSession session = request.getSession();
//            if(session.getAttribute("tabObject") == null) {
//                this.tab = new Tab();
//                this.tab.setUsername(_username);
//                session.setAttribute("tabObject", this.tab);
//            } else {
//                this.tab = (Tab)session.getAttribute("tabObject");
//            }
            
             try {
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app");
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM Listings WHERE tabid=1");
                while (rs.next()) {
                    String title = rs.getString("title");
                    float price = rs.getFloat("price");
                    String author = rs.getString("authorUname");
                    var l = new Listing(title, price, "", false, author, "");
                    this.tab.addListing(l, false);
                }
                rs.close();
            }
                catch(SQLException e){
                   out.println(e.getMessage());
            }
            
            try{   
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>TAB</title>"); 
                out.println("<link rel=\"stylesheet\" href=\"css/main.css\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Hello " + this.tab.getUsername() + ". Welcome to TAB</h2>");
                out.println("<div class=\"table-wrapper\"><table class=\"fl-table\">");
                out.println("<tr><td>Title</td><td>Price</td><td>Author</td></tr>");
                for(int i = 0; i < this.tab.getListings().size(); ++i) {
                    var listing = this.tab.getListings().get(i);
                    out.print("<tr><td><a href=\"listing?index=" + i + "\">");
                    out.print(listing.getTitle() +"</a></td>");
                    out.print("<td>"+ Float.toString(listing.getPrice()) +"</td>");
                    out.print("<td>"+ listing.getAuthorUname() +"</td></tr>");
                };
                
                out.println("</table></div>");
                boolean inserted = _inserted != null && _inserted.length() > 0;
                
                    if(sellingToday.length() > 0) {
                        out.println("<h3>You have created listings for " + sellingToday + ", create more!</h3>");
                        if(inserted) {
                            Cookie cookie = new Cookie("sellingToday", sellingToday + ", " +_inserted);
                            response.addCookie(cookie);
                        }
                    } else if(inserted) {
                        out.println("<h3>You have created listings for " + _inserted + ", create more!</h3>");
                    }
                
                out.println("<a href=\"create?username=" + _username + "\"><button>Create</button></a>");
                out.println("</body>");
                out.println("</html>");
            } catch(Exception ex) {
                response.sendError(response.SC_BAD_REQUEST, ex.getMessage());
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
