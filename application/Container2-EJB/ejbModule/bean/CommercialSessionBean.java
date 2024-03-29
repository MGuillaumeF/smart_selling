package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import javax.annotation.Resource;
//import javax.ejb.EJBContext;
//import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.InitialContext;
//import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
//import javax.transaction.HeuristicMixedException;
//import javax.transaction.HeuristicRollbackException;
//import javax.transaction.NotSupportedException;
//import javax.transaction.RollbackException;
//import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import manager.EntitiesManagerGestion;

//import javax.inject.Inject; 



import model.Client;
import model.Commande;
import model.LigneCommande;
import model.LigneLivraison;
import model.Livraison;
import model.Paiement;
import model.Produit;

/**
 * Session Bean implementation class CommercialSessionBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CommercialSessionBean {
	@PersistenceContext(name="Container2-EJB")
	private EntityManagerFactory entityManagerFactory = null;
	private EntityManager entityManager = null;
    /**
     * Default constructor. 
     */
    public CommercialSessionBean() {
        // TODO Auto-generated constructor stub
    }
    public void init() {
    	//entityManagerFactory = Persistence.createEntityManagerFactory("smart_selling_connect");
    	//entityManager = entityManagerFactory.createEntityManager();
    	if (entityManagerFactory == null) {
        	entityManagerFactory = EntitiesManagerGestion.saveEntityManagerFactory;
    	}
    	if (entityManager == null) {
        	entityManager = entityManagerFactory.createEntityManager();
    	}
    }
    public void close() {
    	if (entityManager != null) {
        	entityManager.close();
        	entityManager = null;
    	}
    	/*if (entityManagerFactory != null) {
        	entityManagerFactory.close();
        	entityManagerFactory = null;
    	}*/
    }
    public String printAllManufacturers(String email, String motDePasse) {  
    	/*EntityManager entityManager = null;
    	System.out.println("In SessionBean Connexion.java");
    	Role role = null;
    	try {
    		entityManager = entityManagerFactory.createEntityManager();
	    	TypedQuery<Utilisateur> query = (TypedQuery<Utilisateur>) entityManager.createQuery("FROM Utilisateur WHERE identifiant=:email AND mot_de_passe=:motDePasse AND actif=1", Utilisateur.class);
	    	query.setParameter( "email", email );
	    	query.setParameter( "motDePasse", motDePasse );
	    	
	    	
	    	List<Utilisateur> result = query.getResultList(); 
	    	for( Utilisateur u : result ){
				role = u.getRole();    			
		     }
	        System.out.println(result.toString());
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
  	      if (entityManager != null) {
  	    	entityManager.close();
 	      }
 	   	}
    	if (role != null) {
    		return role.getNom();
    	} else {
    		return null;
    	}*/
    	return null;
    }
    public String getAllProducts () {
    	JsonArrayBuilder jsonArrayBuilder = null;
    	JsonArray jsonArray = null;
    	try {
	    	TypedQuery<Produit> query = (TypedQuery<Produit>) entityManager.createQuery("FROM Produit WHERE actif=1", Produit.class);
	    	
	    	List<Produit> result = query.getResultList();
	    	jsonArrayBuilder = Json.createArrayBuilder();
	    	for( Produit u : result ){
	    		jsonArrayBuilder.add(Json.createObjectBuilder()
	        					.add("id", u.getIdProduit())
	        					.add("reference", u.getReference())
	        					.add("designation", u.getDesignation())
	        					.add("description", u.getDescription())
	        					.add("stock", u.getStock())
	        					.add("prix", u.getPrix())
	        					);
	        			   			
		     }
	    	jsonArray = jsonArrayBuilder.build();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	if (jsonArray != null) {
    		return jsonArray.toString();
    	} else {
    		return null;
    	}
    }
    public String getAllClients () {
    	JsonArrayBuilder jsonArrayBuilder = null;
    	JsonArray jsonArray = null;
    	try {
	    	TypedQuery<Client> query = (TypedQuery<Client>) entityManager.createQuery("FROM Client WHERE actif=1", Client.class);
	    	
	    	List<Client> result = query.getResultList();
	    	jsonArrayBuilder = Json.createArrayBuilder();
	    	for( Client u : result ){
	    		jsonArrayBuilder.add(Json.createObjectBuilder()
	        					.add("id", u.getIdClient())
	        					.add("nom", u.getNom())
	        					.add("prenom", u.getPrenom())
	        					.add("societe", u.getSociete())
	        					.add("code_postal", u.getCodePostal())
	        					.add("ville", u.getVille())
	        					.add("pays", u.getPays())
	        					.add("adresse", u.getAdresse())
	        					);
	        			   			
		     }
	    	jsonArray = jsonArrayBuilder.build();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	if (jsonArray != null) {
    		return jsonArray.toString();
    	} else {
    		return null;
    	}
    }
    public JsonObject setClient (String nom, String prenom, String societe, String adresse, String codePostal, String ville, String pays, String email, String telephoneFixe, String telephoneMobile, Integer actif) {
    	JsonObject result = null;
    	try {
        	
        	Client client = new Client();
        	
        	client.setNom(nom);
        	client.setPrenom(prenom);
        	client.setSociete(societe);
        	
        	client.setAdresse(adresse);
        	client.setCodePostal(codePostal);
        	client.setVille(ville);
        	client.setPays(pays);
        	
        	client.setEmail(email);
        	client.setTelephoneFixe(telephoneFixe);
        	client.setTelephoneMobile(telephoneMobile);
        	
        	client.setActif(actif);
        	
        	InitialContext context = null;
			context = new InitialContext();
			
            UserTransaction userTransaction = (UserTransaction)context.lookup("java:comp/UserTransaction");
        	userTransaction.begin();
			
        	entityManager.persist(client);
        	
  			userTransaction.commit();
	    	
        	result = Json.createObjectBuilder()
    				.add("status", "ok")
    				.add("n", client.getIdClient().toString()).build();
    	} catch (Exception e) {
    		System.out.println(e);
    		result = Json.createObjectBuilder()
    				.add("status", "ko")
    			    .add("n", "")
    			    .add("errMessage", e.toString())
    			    .build();
    	}
    	
    	return result;
    }
    public Client getClient (Integer idClient) {
    	Client client = null;
    	try {
	    	TypedQuery<Client> query = (TypedQuery<Client>) entityManager.createQuery("FROM Client WHERE idClient=:idClient AND actif=1", Client.class);
	    	query.setParameter("idClient", idClient);
	    	List<Client> result = query.getResultList();
	    	
	    	for( Client u : result ){
	    		client = u;
	    		
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	if (client != null) {
    		return client;
    	} else {
    		return null;
    	}
    }
    public Produit getProduit (Integer idProduit) {
    	Produit produit = null;
    	try {
	    	TypedQuery<Produit> query = (TypedQuery<Produit>) entityManager.createQuery("FROM Produit WHERE idProduit=:idProduit AND actif=1", Produit.class);
	    	query.setParameter("idProduit", idProduit);
	    	List<Produit> result = query.getResultList();
	    	
	    	for( Produit u : result ){
	    		produit = u;
	    		
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	if (produit != null) {
    		return produit;
    	} else {
    		return null;
    	}
    }
    public Commande getCommande (Integer idCommande) {
    	Commande commande = null;
    	try {
	    	TypedQuery<Commande> query = (TypedQuery<Commande>) entityManager.createQuery("FROM Commande WHERE idCommande=:idCommande", Commande.class);
	    	query.setParameter("idCommande", idCommande);
	    	List<Commande> result = query.getResultList();
	    	
	    	for( Commande u : result ){
	    		commande = u;
	    		
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	if (commande != null) {
    		return commande;
    	} else {
    		return null;
    	}
    }
    public void upateStock(Integer idProduit, Integer decrementStock) {
    	Integer stock = null;
    	try {
	    	TypedQuery<Produit> query = (TypedQuery<Produit>) entityManager.createQuery("FROM Produit WHERE idProduit=:idProduit AND actif=1", Produit.class);
	    	query.setParameter("idProduit", idProduit);
	    	List<Produit> result = query.getResultList();
	    	
	    	InitialContext context = null;
			context = new InitialContext();
			
	    	UserTransaction userTransaction = (UserTransaction)context.lookup("java:comp/UserTransaction");
        	userTransaction.begin();
        	
	    	for( Produit u : result ){
	    		stock = u.getStock();
	    		stock -= decrementStock;
	    		u.setStock(stock);
	    		entityManager.persist(u);
		     }
	    	userTransaction.commit();
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    public String setCommande(JsonArray jsonArray, Integer idClient, Float prixTTC) {
    	JsonObject result = null;
    	try {
    		InitialContext context = null;
			context = new InitialContext();
			
        	Commande commande = new Commande();
        	Client client = null;
        	client = getClient(idClient);
        	int i;
        	JsonObject jsonObject = null;
        	if (client != null) {
        		
	        	//List<LigneLivraison> listLigneLivraisons = null;
	        	//listLigneLivraisons = new ArrayList<LigneLivraison>();
	        	List<LigneCommande> listLigneCommandes = null;
	        	listLigneCommandes = new ArrayList<LigneCommande>();
	        	
	        	LigneLivraison ligneLivraison = null;
	        	LigneCommande ligneCommande = null;
	        	
	        	
	        	
	        	commande.setDateEnregistrement(new Date());
	        	commande.setClient(client);
	        	
	        	Livraison livraison = null;
        		livraison = new Livraison();
	        	livraison.setAdresse(client.getAdresse());//adresse);
	        	livraison.setCodePostal(client.getCodePostal());//codePostal);
	        	livraison.setVille(client.getVille());//ville);
	        	livraison.setPays(client.getPays());//pays);

	        	livraison.setNom(client.getNom());//nom);
	        	livraison.setPrenom(client.getPrenom());//prenom);
	        	
	        	
	        	Paiement paiement = null;
	        	paiement = new Paiement();
	        	paiement.setDate(null);
	        	paiement.setPrixTTC(prixTTC);
	        	
	        	
	        	
	        	
	        	
	        	
	        	//entityManager.persist(paiement);
	        	//entityManager.persist(livraison);
	        	
	        	
	        	//
	        	for (i = 0; i < jsonArray.size(); i += 1) {
	        		jsonObject = jsonArray.getJsonObject(i);
		        	//ligneLivraison = new LigneLivraison();
		        	ligneCommande = new LigneCommande();
		        	
		        	
		        	ligneCommande.setCommande(commande);
		        	//ligneCommande.setLigneLivraison(ligneLivraison);
		        	ligneCommande.setProduit(getProduit((Integer)Integer.parseInt(jsonObject.getString("id"))));//);
		        	ligneCommande.setQuantite((Integer)Integer.parseInt(jsonObject.getString("val")));//);
		        	upateStock(
		        			(Integer)Integer.parseInt(jsonObject.getString("id")), 
		        			(Integer)Integer.parseInt(jsonObject.getString("val"))
		        	);
		        	listLigneCommandes.add(ligneCommande);
		        	//ligneLivraison.setDateEnvoi(null);
		        	//ligneLivraison.setidLigneCommande(ligneCommande);
		        	//ligneLivraison.setLivraison(livraison);
		        	//listLigneLivraisons.add(ligneLivraison);
	        	}
	        	
	        	//commande.setPaiement(paiement);
	        	//
	        	//
	        	//commande.setLigneCommandes(listLigneCommandes);
	        	//livraison.setLigneLivraisons(listLigneLivraisons);
	        	
	        	/*entityManager.persist(commande);
	        	entityManager.flush();
	        	userTransaction.commit();*/
	        	
	        	UserTransaction userTransaction = (UserTransaction)context.lookup("java:comp/UserTransaction");
	        	userTransaction.begin();
	        	entityManager.persist(commande);
	        	livraison.setIdCommande(commande.getIdCommande());
	        	entityManager.persist(livraison);
	        	paiement.setIdCommande(commande.getIdCommande());
	        	entityManager.persist(paiement);
	        	commande.setLivraison(livraison);
	        	commande.setPaiement(paiement);
	        	entityManager.persist(commande);
	        	for( LigneCommande u : listLigneCommandes ){
	        		//u..setIdCommande(commande.getIdCommande());
	        		entityManager.persist(u);
	        		ligneLivraison = new LigneLivraison();
	        		ligneLivraison.setDateEnvoi(null);
	        		//ligneLivraison.setLivraison(livraison);
	        		ligneLivraison.setLivraison(livraison);
	        		ligneLivraison.setIdLigneCommande(u.getIdLigneCommande());
	        		entityManager.persist(ligneLivraison);
	        	}
	        	userTransaction.commit();
	        	result = Json.createObjectBuilder()
	    				.add("status", "ok")
	    			    .add("n", "1")
	    			    .build();
        	} else {
        		result = Json.createObjectBuilder()
        				.add("status", "ko")
        			    .add("n", "2")
        			    .add("errMessage", "client not found")
        			    .build();
        	}
        	
    	} catch (Exception e) {
    		System.out.println(e);
    		result = Json.createObjectBuilder()
    				.add("status", "ko")
    			    .add("n", "2")
    			    .add("errMessage", e.toString())
    			    .build();
    	}
    	return result.toString();
    }
}
