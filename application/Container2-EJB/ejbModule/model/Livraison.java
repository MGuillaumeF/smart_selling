package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the livraison database table.
 * 
 */
@Entity
@NamedQuery(name="Livraison.findAll", query="SELECT l FROM Livraison l")
public class Livraison implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_livraison")
	private Integer idLivraison;

	private String adresse;

	@Column(name="code_postal")
	private String codePostal;

	@Column(name="id_commande")
	private Integer idCommande;

	private String nom;

	private String pays;

	private String prenom;

	private String ville;

	//bi-directional one-to-one association to Commande
	@OneToOne(mappedBy="livraison")
	private Commande commande;

	//bi-directional many-to-one association to LigneLivraison
	@OneToMany(mappedBy="livraison")
	private List<LigneLivraison> ligneLivraisons;

	public Livraison() {
	}

	public Integer getIdLivraison() {
		return this.idLivraison;
	}

	public void setIdLivraison(Integer idLivraison) {
		this.idLivraison = idLivraison;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public Integer getIdCommande() {
		return this.idCommande;
	}

	public void setIdCommande(Integer idCommande) {
		this.idCommande = idCommande;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Commande getCommande() {
		return this.commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public List<LigneLivraison> getLigneLivraisons() {
		return this.ligneLivraisons;
	}

	public void setLigneLivraisons(List<LigneLivraison> ligneLivraisons) {
		this.ligneLivraisons = ligneLivraisons;
	}

	public LigneLivraison addLigneLivraison(LigneLivraison ligneLivraison) {
		getLigneLivraisons().add(ligneLivraison);
		ligneLivraison.setLivraison(this);

		return ligneLivraison;
	}

	public LigneLivraison removeLigneLivraison(LigneLivraison ligneLivraison) {
		getLigneLivraisons().remove(ligneLivraison);
		ligneLivraison.setLivraison(null);

		return ligneLivraison;
	}

}