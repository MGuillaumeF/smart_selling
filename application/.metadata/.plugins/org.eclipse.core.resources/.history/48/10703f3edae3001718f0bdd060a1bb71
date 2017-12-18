package servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Properties;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import bean.ConnexionSessionBean;

/**
 * Servlet implementation class Connect
 */
@WebServlet("/Connect")
public class Connect extends HttpServlet {
	//private ConnexionLocal proxy;
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private HttpServletResponse response;
    private HttpServletRequest request;
    private RequestDispatcher view;
    private HttpSession session;
    private ConnexionSessionBean connexion = null;
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connect() {
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
		
		this.response.setContentType("text/html;charset=UTF-8");
        this.response.setCharacterEncoding("UTF-8");
        this.request.setCharacterEncoding("UTF-8");
        
		this.out = this.response.getWriter();//.append("Served at: ").append(request.getContextPath());
		this.view = this.request.getRequestDispatcher("html/connect.html");
		this.view.include(this.request, this.response);
		session = request.getSession();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//response.sendRedirect("Enregistrement");
		//this.view = this.request.getRequestDispatcher("/Enregistrement");
		//this.view.forward(this.request, this.response);
		this.request = request;
		this.response = response;
		this.session = request.getSession(true);
		
		this.response.setContentType("text/html;charset=UTF-8");
        this.response.setCharacterEncoding("UTF-8");
        this.request.setCharacterEncoding("UTF-8");
        
        this.out = this.response.getWriter();
        
        
		response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (connexion == null) {
    	    connexion = new ConnexionSessionBean();
    	    connexion.init();
        }
        String rule = request.getParameter("rule").trim();

        String identifiant = request.getParameter("i").trim();
        if (rule.equals("getSalts")) {
        	String salt2 = createRandomStr(256);
        	String salt1 = connexion.getSalt1(identifiant);
        	if (salt1 == null) {
        		salt1 = createRandomStr(256);
        	} else {
        		connexion.setSalt2(identifiant, salt2);
        	}
        	JsonObject json = Json.createObjectBuilder()
    				.add("s1", salt1)
    			    .add("s2", salt2)
    			    .build();
        	out.println(json.toString());
        } else if (rule.equals("connect")) {
        	String motDePasse = request.getParameter("p").trim();
        	//response.setContentType("text/html");
    		/*try {
    			InitialContext context = new InitialContext();
    			ConnexionLocal libraryBean;
    			libraryBean = (ConnexionLocal)context.lookup("ConnexionLocal/remote");
    			libraryBean.printAllManufacturers();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println(e);
    			e.printStackTrace();
    		}*/
    		/*try {
    			InitialContext context = new InitialContext();
    			session.setAttribute("context", context);
    			String jndiProxy = "java:global/Container1-EAR/ConnexionEJB/Connexion!bean.ConnexionLocal";
    			proxy = (ConnexionLocal) context.lookup(jndiProxy);
    			session.setAttribute("proxy",proxy);
    			session.setAttribute("etat", "alive");
    			proxy.init();
    			String test = proxy.printAllManufacturers(identifiant, motDePasse);
    			System.out.println(test);
    		} catch (NamingException e) {
    			e.printStackTrace();
    		}*/
        	
        	if (!identifiant.equals("") && !motDePasse.equals("")) {
        		
            	String mdp = connexion.getMotDePasse(identifiant);
            	if (motDePasse.equals(sha512(connexion.getSalt2(identifiant) + mdp))) {
            		String role = connexion.getRole(identifiant, mdp);
            		session.setAttribute("currentConnexion", role);
                	if (role == null) {
                		out.println("ko");
                	} else if (role.equals("commercial")) {
                		connexion.close();
                    	connexion = null;
                		out.println("EnregistrementClient");
                	} else if (role.equals("comptable")) {
                		connexion.close();
                    	connexion = null;
                		out.println("Facturation");
                	} else if (role.equals("responsable des stocks")) {
                		connexion.close();
                    	connexion = null;
                		out.println("Envoi");
                	}
            	} else {
            		out.println("ko");
            	}
            	
        	} else {
        		out.println("ko");
        	}
        }
	}
	private String createRandomStr (int size) {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[size];
		random.nextBytes(bytes);
		return bytes.toString();
	}
	private String sha512(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-512");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}

}
