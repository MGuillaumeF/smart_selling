package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the utilisateur database table.
 * 
 */
@Entity
@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_utilisateur")
	private Integer idUtilisateur;

	private Integer actif;

	private String gds1;

	private String gds2;

	private String identifiant;

	@Column(name="mot_de_passe")
	private String motDePasse;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="id_role")
	private Role role;

	public Utilisateur() {
	}

	public Integer getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public Integer getActif() {
		return this.actif;
	}

	public void setActif(Integer actif) {
		this.actif = actif;
	}

	public String getGds1() {
		return this.gds1;
	}

	public void setGds1(String gds1) {
		this.gds1 = gds1;
	}

	public String getGds2() {
		return this.gds2;
	}

	public void setGds2(String gds2) {
		this.gds2 = gds2;
	}

	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return this.motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}