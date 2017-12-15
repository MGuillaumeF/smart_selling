package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ComptableSessionBean;


/**
 * Servlet implementation class Facturation
 */
@WebServlet("/Facturation")
public class Facturation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private HttpServletResponse response;
    private HttpServletRequest request;
    private RequestDispatcher view;
    private HttpSession session;
    private ComptableSessionBean comptableSessionBean = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Facturation() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.request = request;
		this.response = response;
		this.session = request.getSession(true);
		
		response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
		this.out = this.response.getWriter();//.append("Served at: ").append(request.getContextPath());
		this.out.println("");
		
		Object currentConnexion = null;
		String strCurrentConnexion = null;
		currentConnexion = session.getAttribute("currentConnexion");
		if (currentConnexion != null) {
			strCurrentConnexion = currentConnexion.toString().trim();
		}
    	if (strCurrentConnexion != null && strCurrentConnexion.equals("comptable")) {
    		this.view = this.request.getRequestDispatcher("html/facturation_commande.html");
    		this.view.include(this.request, this.response);
    	} else {
    		out.println("pas de connexion");
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.request = request;
		this.response = response;
		this.session = request.getSession(true);
        
        
		response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        this.out = this.response.getWriter();
        
        if (comptableSessionBean == null) {
        	comptableSessionBean = new ComptableSessionBean();
        	comptableSessionBean.init();
        }
        
        String rule = null;
        if (request.getParameter("rule") != null && !request.getParameter("rule").trim().equals("")) {
        	rule = request.getParameter("rule").trim();
        
	        if (rule.equals("getAllCommandes")) {
	        	String listCommandes = comptableSessionBean.getAllCommands();
	            //commercialSessionBean.close();
	            //commercialSessionBean = null;
	        	out.println(listCommandes);
	        } else if (rule.equals("getProduits")) {
	        	String listProduits = comptableSessionBean.getAllProducts();
	            //commercialSessionBean.close();
	            //commercialSessionBean = null;
	        	out.println(listProduits);
	        } else if (rule.equals("setEncaissement")) {
        		String paramStr = null;
        		String retour = null;
        		Integer numCommande = null;
        		paramStr = request.getParameter("id");
        		System.out.println(paramStr);
        		if (paramStr != null) {
        			paramStr = paramStr.trim();
        			System.out.println(paramStr);
        			if (!paramStr.equals("")){
        				System.out.println(paramStr);
    	            	numCommande = Integer.parseInt(paramStr);
    	            	retour = comptableSessionBean.setEncaissementCommande(numCommande);
        			} else {
        				System.out.println("*********************************************************************************************** equals VIDE 120");
        			}
        		} else {
    				System.out.println("*********************************************************************************************** NULL 123");
    			}
        		
            	
	        	//String 
	        	//String listProduits = stockSessionBean.getAllProducts();
	            //commercialSessionBean.close();
	            //commercialSessionBean = null;
	        	out.println(retour);
        	} else if (rule.equals("deconnect")) {
            	session.removeAttribute("currentConnexion");
            	session.removeAttribute("currentClient");
            	out.println("Connect");
            }
        }
	}

}
