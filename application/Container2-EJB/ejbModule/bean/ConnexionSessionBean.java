package bean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import manager.EntitiesManagerGestion;
import model.Role;
import model.Utilisateur;


/**
 * Session Bean implementation class Connect
 */
@Stateless
@LocalBean
public class ConnexionSessionBean implements ConnexionSessionBeanRemote{
	public interface ConnexionLocal {

	}
	@PersistenceContext(name="Container2-EJB")
	private EntityManagerFactory entityManagerFactory = null;
	private EntityManager entityManager = null;
    /**
     * Default constructor. 
     */
    public ConnexionSessionBean() {
        // TODO Auto-generated constructor stub
    }
    public void init() {
    	if (EntitiesManagerGestion.saveEntityManagerFactory == null) {
    		entityManagerFactory = Persistence.createEntityManagerFactory("Container2-EJB");
    		EntitiesManagerGestion.saveEntityManagerFactory = entityManagerFactory;
    	} else {
    		entityManagerFactory = EntitiesManagerGestion.saveEntityManagerFactory;
    	}
        entityManager = entityManagerFactory.createEntityManager();
    	
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
    public String getRole(String email, String motDePasse) {
    	Role role = null;
    	try {
	    	TypedQuery<Utilisateur> query = (TypedQuery<Utilisateur>) entityManager.createQuery("FROM Utilisateur WHERE identifiant=:email AND mot_de_passe=:motDePasse AND actif=1", Utilisateur.class);
	    	query.setParameter( "email", email );
	    	query.setParameter( "motDePasse", motDePasse );
	    	
	    	
	    	List<Utilisateur> result = query.getResultList(); 
	    	for( Utilisateur u : result ){
				role = u.getRole();    			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	if (role != null) {
    		return role.getNom();
    	} else {
    		return null;
    	}
    }
    public String getSalt1(String email) {
    	String gds = null;
    	try {
	    	TypedQuery<Utilisateur> query = (TypedQuery<Utilisateur>) entityManager.createQuery("FROM Utilisateur WHERE identifiant=:email AND actif=1", Utilisateur.class);
	    	query.setParameter( "email", email );
	    	
	    	List<Utilisateur> result = query.getResultList(); 
	    	for( Utilisateur u : result ){
	    		gds = u.getGds1();    			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return gds;
    }
    public String getSalt2(String email) {
    	String gds = null;
    	try {
	    	TypedQuery<Utilisateur> query = (TypedQuery<Utilisateur>) entityManager.createQuery("FROM Utilisateur WHERE identifiant=:email AND actif=1", Utilisateur.class);
	    	query.setParameter( "email", email );
	    	
	    	List<Utilisateur> result = query.getResultList(); 
	    	for( Utilisateur u : result ){
	    		gds = u.getGds2();    			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return gds;
    }
    public String setSalt2(String email, String salt2) {
    	String gds = null;
    	try {
	    	TypedQuery<Utilisateur> query = (TypedQuery<Utilisateur>) entityManager.createQuery("FROM Utilisateur WHERE identifiant=:email AND actif=1", Utilisateur.class);
	    	query.setParameter( "email", email );
	    	
	    	List<Utilisateur> result = query.getResultList(); 
	    	InitialContext context = null;
			context = new InitialContext();
			
            UserTransaction userTransaction = (UserTransaction)context.lookup("java:comp/UserTransaction");
        	userTransaction.begin();
	    	for( Utilisateur u : result ){
	    		u.setGds2(salt2);  
	    		entityManager.persist(u);
		     }
	    	userTransaction.commit();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return gds;
    }
    public String getMotDePasse(String email) {
    	String motDePasse = null;
    	try {
	    	TypedQuery<Utilisateur> query = (TypedQuery<Utilisateur>) entityManager.createQuery("FROM Utilisateur WHERE identifiant=:email AND actif=1", Utilisateur.class);
	    	query.setParameter( "email", email );
	    	
	    	List<Utilisateur> result = query.getResultList(); 
	    	for( Utilisateur u : result ){
	    		motDePasse = u.getMotDePasse();    			
		     }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return motDePasse;
    }
}
