package bean;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import manager.EntitiesManagerGestion;
import model.Commande;
import model.LigneCommande;
import model.LigneLivraison;
import model.Livraison;
import model.Produit;

/**
 * Session Bean implementation class ComptableSessionBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ComptableSessionBean {
	@PersistenceContext(name="ComptableEJB")
	private EntityManagerFactory entityManagerFactory = null;
	private EntityManager entityManager = null;

    /**
     * Default constructor. 
     */
    public ComptableSessionBean() {
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
    public String getAllCommands () {
    	JsonArrayBuilder jsonArrayBuilder = null;
    	JsonArray jsonArray = null;
    	List<Commande> result = null;
    	JsonArrayBuilder listLigneCommandeArray = null;
    	JsonObject livraisonObj = null;
    	Livraison livraison = null;
    	List<LigneCommande> listLigneCommande = null;
    	LigneLivraison ligneLivraison = null;
    	Integer idCommande = null;
    	Date dateEnvoi = null;
    	String strDateEnvoi = null;
    	try {
	    	TypedQuery<Commande> query = (TypedQuery<Commande>) entityManager.createQuery("FROM Commande", Commande.class);
	    	
	    	result = query.getResultList();
	    	jsonArrayBuilder = Json.createArrayBuilder();
	    	for( Commande u : result ){
	    		idCommande = u.getIdCommande();
	    		livraison = getLivraisonByCommande(idCommande);
	    		livraisonObj = Json.createObjectBuilder()
	    				.add("adresse", livraison.getAdresse().toString())
	    				.add("codePostal", livraison.getCodePostal().toString())
	    				.add("ville", livraison.getVille().toString())
	    				.add("pays", livraison.getPays().toString())
	    				.add("nom", livraison.getNom().toString())
	    				.add("prenom", livraison.getPrenom().toString())
	    				.build();
	    		listLigneCommandeArray = Json.createArrayBuilder();
	    		listLigneCommande = u.getLigneCommandes();
	    		for (LigneCommande lc : listLigneCommande) {
	    			ligneLivraison = getLigneLivraisonByLigneCommande(lc.getIdLigneCommande());
	    			dateEnvoi = ligneLivraison.getDateEnvoi();
	    			if (dateEnvoi == null) {
	    				strDateEnvoi = "vide";
	    			} else {
	    				strDateEnvoi = dateEnvoi.toString();
	    			}
	    			listLigneCommandeArray.add(Json.createObjectBuilder()
	    					.add("idLigneLivraison", ligneLivraison.getIdLigneLivraison())
	    					.add("idLigneCommande", lc.getIdLigneCommande())
	    					.add("quantite", lc.getQuantite())
	    					.add("idProduit", lc.getProduit().getIdProduit())
	    					.add("dateEnvoi", strDateEnvoi.toString())
	    					);
	    		}
	    		jsonArrayBuilder.add(Json.createObjectBuilder()
	        					.add("idCommande", u.getIdCommande())
	        					.add("livraison", livraisonObj.toString())
	        					.add("telephoneFixe", u.getClient().getTelephoneFixe().toString())
	        					.add("telephoneMobile", u.getClient().getTelephoneMobile().toString())
	        					.add("email", u.getClient().getEmail().toString())
	        					.add("dateEnregistrement", u.getDateEnregistrement().toString())
	        					.add("ligneCommandes", listLigneCommandeArray.build().toString())
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
    private LigneLivraison getLigneLivraisonByLigneCommande(Integer idLigneCommande) {
    	LigneLivraison ligneLivraison = null;
    	try {
	    	TypedQuery<LigneLivraison> query = (TypedQuery<LigneLivraison>) entityManager.createQuery("FROM LigneLivraison WHERE idLigneCommande=:idLigneCommande", LigneLivraison.class);
	    	query.setParameter("idLigneCommande", idLigneCommande);
	    	List<LigneLivraison> result = query.getResultList();
	    	for( LigneLivraison u : result ){
	    		ligneLivraison = u;	   			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return ligneLivraison;
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
   /* public String getAllProducts () {
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
    }*/
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
    	return commande;
    }
    public LigneCommande getLigneCommande (Integer idLigneCommande) {
    	LigneCommande ligneCommande = null;
    	try {
	    	TypedQuery<LigneCommande> query = (TypedQuery<LigneCommande>) entityManager.createQuery("FROM LigneCommande WHERE idLigneCommande=:idLigneCommande", LigneCommande.class);
	    	query.setParameter("idLigneCommande", idLigneCommande);
	    	List<LigneCommande> result = query.getResultList();
	    	for( LigneCommande u : result ){
	    		ligneCommande = u;	   			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return ligneCommande;
    }
    public LigneLivraison getLigneLivraison (Integer idLigneLivraison) {
    	LigneLivraison ligneLivraison = null;
    	try {
	    	TypedQuery<LigneLivraison> query = (TypedQuery<LigneLivraison>) entityManager.createQuery("FROM LigneLivraison WHERE idLigneLivraison=:idLigneLivraison", LigneLivraison.class);
	    	query.setParameter("idLigneLivraison", idLigneLivraison);
	    	List<LigneLivraison> result = query.getResultList();
	    	for( LigneLivraison u : result ){
	    		ligneLivraison = u;	   			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return ligneLivraison;
    }
    public Livraison getLivraisonByCommande (Integer idCommande) {
    	Livraison livraison = null;
    	try {
	    	TypedQuery<Livraison> query = (TypedQuery<Livraison>) entityManager.createQuery("FROM Livraison WHERE idCommande=:idCommande", Livraison.class);
	    	query.setParameter("idCommande", idCommande);
	    	List<Livraison> result = query.getResultList();
	    	for( Livraison u : result ){
	    		livraison = u;	   			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return livraison;
    }
	public String setLigneLivraisonValide(Integer[] listeLigneLivraisonId) {
		InitialContext context = null;
		LigneLivraison[] listeLigneLivraison = null;
		int i = 0;
		Date date = new Date();
		String retour = null;
		try {
			listeLigneLivraison = new LigneLivraison[listeLigneLivraisonId.length];
			context = new InitialContext();
			
	        UserTransaction userTransaction = (UserTransaction)context.lookup("java:comp/UserTransaction");
	        userTransaction.begin();
			for (i = 0; i < listeLigneLivraisonId.length; i += 1) {
				listeLigneLivraison[i] = getLigneLivraison(listeLigneLivraisonId[i]);
				listeLigneLivraison[i].setDateEnvoi(date);
				entityManager.persist(listeLigneLivraison[i]);
			}
	    	userTransaction.commit();
	    	retour = date.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retour;
	}
}
