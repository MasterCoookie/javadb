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
import pl.polsl.lab.model.Tab;

@WebServlet(name = "CreateServlet", urlPatterns = {"/create"})
public class CreateServlet extends HttpServlet {

    private Tab tab;
    
    @Override
    public void init() {
//        tab = new Tab();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        
        String _username = request.getParameter("username");
        String _msg = request.getParameter("msg");
        
        PrintWriter out = response.getWriter();

        
        if(_username == null || _username.length() == 0){
            response.sendError(response.SC_BAD_REQUEST, "Invalid argument");
        } else {
            HttpSession session = request.getSession();
            if(session.getAttribute("tabObject") == null) {
                this.tab = new Tab();
                this.tab.setUsername(_username);
                session.setAttribute("tabObject", this.tab);
            } else {
                this.tab = (Tab)session.getAttribute("tabObject");
//                out.println("d");
            }
            try{
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>TAB</title>"); 
                out.println("<link rel=\"stylesheet\" href=\"css/main.css\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>New TAB listing creation</h1>");
                out.println("<form action=\"insert\" method=\"POST\">");
                if(_msg != null && _msg.length() != 0) {
                    out.println("<h3 style=\"color: red;\">" + _msg + "</h3>");
                }
                out.println("<input type=\"text\" name=\"title\" placeholder=\"Title\"></input></br>");
                out.println("<input type=\"text\" name=\"price\" placeholder=\"Price\"></input></br>");
                out.println("<input type=\"checkbox\" name=\"negotiable\"></input>Negotiable?</br>");
                out.println("<textarea name=\"desc\" placeholder=\"Descryption\"></textarea></br>");
                out.println("<input type=\"submit\" value=\"Create\"></input>");
                out.println("</form>");
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
