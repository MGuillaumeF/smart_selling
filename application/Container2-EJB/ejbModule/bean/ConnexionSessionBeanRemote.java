package bean;

import javax.ejb.Remote;

@Remote
public interface ConnexionSessionBeanRemote {
	void init();
	void close();
	String getRole(String email, String motDePasse);
	String getSalt1(String email);
	String getSalt2(String email);
	String setSalt2(String email, String salt2);
	String getMotDePasse(String email);
}
