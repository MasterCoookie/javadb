package pl.pols.lab.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import pl.polsl.lab.model.Listing;
import pl.polsl.lab.model.Tab;

@WebServlet(name = "ListingServlet", urlPatterns = {"/listing"})
public class ListingServlet extends HttpServlet {

    private Tab tab;
    private Listing listing;
    
    @Override
    public void init() {
//        tab = new Tab();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        if(session.getAttribute("tabObject") == null) {
            response.sendRedirect(request.getContextPath() + "/create");
        } else {
            this.tab = (Tab)session.getAttribute("tabObject");
        }
        String _index = request.getParameter("index");
        
        if(_index == null || _index.length() == 0){
            response.sendError(response.SC_BAD_REQUEST, "Wymagane są dwa parametry!!!");
        } else {
            try{
                int index = Integer.parseInt(_index);
                this.listing = this.tab.getListings().get(index);
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>TAB</title>"); 
                out.println("<link rel=\"stylesheet\" href=\"css/main.css\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>" + this.listing.getTitle() + "</h1>");
                out.println("<p>" + this.listing.getDesc() + "</p>");
                
                out.println("<p>Listed for:" + this.listing.getPrice() + "$" + (this.listing.isNegotiable() ? " Negotiable": "") + "</p>");

                out.println("</body>");
                out.println("</html>");
            } catch(Exception ex) {
                response.sendError(response.SC_BAD_REQUEST, "Invalid arguments!");
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
