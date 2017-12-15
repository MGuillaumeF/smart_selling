package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CommercialSessionBean;
import bean.ConnexionSessionBean;

/**
 * Servlet implementation class EnregistrementCommande
 */
@WebServlet("/EnregistrementCommande")
public class EnregistrementCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private HttpServletResponse response;
    private HttpServletRequest request;
    private RequestDispatcher view;
    private HttpSession session;
    private CommercialSessionBean commercialSessionBean = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnregistrementCommande() {
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
		
		this.response.setContentType("text/html;charset=UTF-8");
        this.response.setCharacterEncoding("UTF-8");
        this.request.setCharacterEncoding("UTF-8");
        
        this.out = this.response.getWriter();//.append("Served at: ").append(request.getContextPath());
		this.out.println("");
		
        Object currentConnexion = null;
		String strCurrentConnexion = null;
		currentConnexion = session.getAttribute("currentConnexion");
		if (currentConnexion != null) {
			strCurrentConnexion = currentConnexion.toString().trim();
		}
    	if (strCurrentConnexion != null && strCurrentConnexion.equals("commercial")) {
    	
			Object currentClient = null;
			String strCurrentClient = null;
			currentClient = session.getAttribute("currentClient");
			if (currentClient != null) {
				strCurrentClient = currentClient.toString().trim();
			}
	    	if (strCurrentClient != null && !strCurrentClient.equals("")) {
	    		this.view = this.request.getRequestDispatcher("html/enregistrement_commande.html");
	    		this.view.include(this.request, this.response);
	    	} else {
	    		out.println("pas de client sélectionné");
	    	}
    	} else {
    		out.println("pas de connexion");
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		this.request = request;
		this.response = response;
		this.session = request.getSession(true);
		
		this.response.setContentType("text/html;charset=UTF-8");
        this.response.setCharacterEncoding("UTF-8");
        this.request.setCharacterEncoding("UTF-8");
		
        this.out = this.response.getWriter();
        
        if (commercialSessionBean == null) {
            commercialSessionBean = new CommercialSessionBean();
            commercialSessionBean.init();
        }
        
        String rule = null;
        if (request.getParameter("rule") != null && !request.getParameter("rule").trim().equals("")) {
        	rule = request.getParameter("rule").trim();
        
	        if (rule.equals("getProduits")) {
	        	String listProduits = commercialSessionBean.getAllProducts();
	            //commercialSessionBean.close();
	            //commercialSessionBean = null;
	        	out.println(listProduits);
	        } else if (rule.equals("setCommande")) {
	        	String linesCommandes = request.getParameter("produit").trim();
	        	String prixTTC = request.getParameter("prixTTC").trim();
	        	String result = null;
	        	if (linesCommandes != null && linesCommandes.length() != 0){
	        		String[] part = null;
	            	part = linesCommandes.split(",");
	            	String[] row = null;
	            	JsonArrayBuilder jsonAB = Json.createArrayBuilder();
	            	for (int i = 0; i < part.length; i += 1) {
	            		row = part[i].split(":");
	            		jsonAB.add(Json.createObjectBuilder()
	            				.add("id", row[0])
	            				.add("val", row[1]));
	            	}
	            	JsonArray jsonArray = jsonAB.build();
	            	String currentClient = session.getAttribute("currentClient").toString().trim();
	            	if (currentClient != null && !currentClient.equals("")) {
	            		result = commercialSessionBean.setCommande(jsonArray, (Integer)Integer.parseInt(currentClient), (Float)Float.parseFloat(prixTTC));
	            	} else {
	            		result = Json.createObjectBuilder()
	            				.add("status", "ko")
	            			    .add("n", "2")
	            			    .add("errMessage", "client not found")
	            			    .build().toString();
	            	}
	            	//
	            	System.out.println(jsonArray);
	        	} else {
	        		result = Json.createObjectBuilder()
	        				.add("status", "ko")
	        			    .add("n", "4")
	        			    .add("errMessage", "commande vide")
	        			    .build().toString();
	        	}
	        	
	        	
	        	
	        	/*json = Json.createObjectBuilder()
						.add("status", "ko")
						.add("n", "1")
						.add("errMessage", "Micro servo de 9 grammes Tower Pro SG90")
						.build();*/
	
	            commercialSessionBean.close();
	            commercialSessionBean = null;
	            session.removeAttribute("currentClient");
	        	out.println(result);
	        } else if (rule.equals("deconnect")) {
	        	session.removeAttribute("currentConnexion");
	        	session.removeAttribute("currentClient");
	        	out.println("Connect");
	        }
        }
	}

}
