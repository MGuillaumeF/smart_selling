package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonArray;
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
 * Servlet implementation class Enregistrement
 */
@WebServlet("/EnregistrementClient")
public class EnregistrementClient extends HttpServlet {
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
    public EnregistrementClient() {
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
    		this.view = this.request.getRequestDispatcher("html/enregistrement_client.html");
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
		// doGet(request, response);
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
        
        String rule = request.getParameter("rule").trim();
        
        
        if (rule.equals("addClient")) {
            String nom = request.getParameter("nom").trim();
        	String prenom = request.getParameter("prenom").trim();
        	String societe = request.getParameter("societe").trim();
        	
        	String adresse = request.getParameter("adresse").trim();
        	String codePostal = request.getParameter("code_postal").trim();
        	String ville = request.getParameter("ville").trim();
        	String pays = request.getParameter("pays").trim();
        	
        	String email = request.getParameter("email").trim();
        	String telephoneFixe = request.getParameter("tel_fixe").trim();
        	String telephoneMobile = request.getParameter("tel_mob").trim();
        	
        	JsonObject json;
        	
        	if ((!nom.equals("") || !prenom.equals("") || !societe.equals("")) && 
        			(!telephoneFixe.equals("") || !telephoneMobile.equals("")) &&
        			!adresse.equals("") &&
        			!codePostal.equals("") &&
        			!ville.equals("") &&
        			!pays.equals("") &&
        			!email.equals("")
        			) {
        		JsonObject jsonResult = commercialSessionBean.setClient (nom, prenom, societe, adresse, codePostal, ville, pays, email, telephoneFixe, telephoneMobile, (Integer)1);
        		
				//if (jsonResult.get("status").equals("ok")) {
					session.setAttribute("currentClient", jsonResult.getString("n"));
				//}
					System.out.print(jsonResult.get("n").toString());
	        	commercialSessionBean.close();
	        	commercialSessionBean = null;
        		out.println(jsonResult.toString());
        	} else {
        		json = Json.createObjectBuilder()
        				.add("status", "ko")
        			    .add("n", "1")
        			    .add("errMessage", "un input mal remplit")
        			    .build();
        		out.println(json.toString());
        	}
        } else if (rule.equals("getClients")) {
        	String listClients = commercialSessionBean.getAllClients();
        	out.println(listClients);
        } else if (rule.equals("setClient")) {
        	String id = request.getParameter("id").trim();
        	session.setAttribute("currentClient", id);
        	JsonObject json = Json.createObjectBuilder()
    				.add("status", "ok").build();
        	commercialSessionBean.close();
        	commercialSessionBean = null;
        	out.println(json.toString());
        } else if (rule.equals("deconnect")) {
        	session.removeAttribute("currentConnexion");
        	session.removeAttribute("currentClient");
        	out.println("Connect");
        }
	}

}
