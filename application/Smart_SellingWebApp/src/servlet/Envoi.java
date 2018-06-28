package servlet;

import java.io.IOException;
import java.io.PrintWriter;

//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonArrayBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import bean.CommercialSessionBean;
import bean.StockSessionBean;

/**
 * Servlet implementation class Envoi
 */
@WebServlet("/Envoi")
public class Envoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private HttpServletResponse response;
    private HttpServletRequest request;
    private RequestDispatcher view;
    private HttpSession session;
    private StockSessionBean stockSessionBean = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Envoi() {
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
    	if (strCurrentConnexion != null && strCurrentConnexion.equals("responsable des stocks")) {
    		this.view = this.request.getRequestDispatcher("html/envoi_commande.html");
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
        
        if (stockSessionBean == null) {
        	stockSessionBean = new StockSessionBean();
        	stockSessionBean.init();
        }
        
        String rule = null;
        if (request.getParameter("rule") != null && !request.getParameter("rule").trim().equals("")) {
        	rule = request.getParameter("rule").trim();
        
	        if (rule.equals("getAllCommandes")) {
	        	String listCommandes = stockSessionBean.getAllCommands();
	            //commercialSessionBean.close();
	            //commercialSessionBean = null;
	        	out.println(listCommandes);
	        } else if (rule.equals("getProduits")) {
	        	String listProduits = stockSessionBean.getAllProducts();
	            //commercialSessionBean.close();
	            //commercialSessionBean = null;
	        	out.println(listProduits);
	        } else if (rule.equals("setEnvoi")) {
        		String[] listeStringLigneLivraison = null;
        		String paramStr = null;
        		Integer[] listeIntegerLigneLivraison = null;
        		String ligneLivraisonValide = null;
        		Integer numLigneLivraison = null;
        		paramStr = request.getParameter("listLigneLivraison");
        		System.out.println(paramStr);
        		if (paramStr != null) {
        			paramStr = paramStr.trim();
        			System.out.println(paramStr);
        			if (!paramStr.equals("")){
        				System.out.println(paramStr);
        				listeStringLigneLivraison = paramStr.split(",");
        				if (listeStringLigneLivraison != null) {
        					listeIntegerLigneLivraison = new Integer[listeStringLigneLivraison.length];
        	            	int i = 0;
        	            	for (i = 0; i < listeStringLigneLivraison.length; i += 1) {
        	            		numLigneLivraison = Integer.parseInt(listeStringLigneLivraison[i]);
        	            		listeIntegerLigneLivraison[i] = numLigneLivraison;
        	            	}
        	            	ligneLivraisonValide = stockSessionBean.setLigneLivraisonValide(listeIntegerLigneLivraison);
        				}
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
	        	out.println(ligneLivraisonValide);
        	} else if (rule.equals("deconnect")) {
            	session.removeAttribute("currentConnexion");
            	session.removeAttribute("currentClient");
            	out.println("Connect");
            }
        }
	}

}
